package com.byd.emg.common;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.byd.emg.pojo.TZmonitor;
import com.byd.emg.resultentity.MesParameter;
import com.byd.emg.resultentity.TimeJobParameter;
import com.byd.emg.service.*;
import com.byd.emg.util.LogUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.apache.cxf.endpoint.Client;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TimerJobs {

    private static Client client=null;

    private static final Logger log = LoggerFactory.getLogger(TimerJobs.class);

    //保存多少年的历史数据
    private static int YEAR_NUM=20;

    //webservice请求的url;
    //http://172.29.0.208:8092/energysystem/server?wsdl
    //private static final String PATH="http://192.168.0.189:8092/energysystem/server?wsdl";
    //private static final String PATH="http://192.168.0.201:8092/energysystem/server?wsdl";
    //private static final String PATH="http://172.15.16.66:9233/MES?wsdl";
    //private static final String PATH="http://172.29.0.51:9233/MES?wsdl";
    private static final String PATH="http://cvmes-service.dflzm.com:9217/MES?wsdl";
    @Autowired
    private TimeJobService timeJobService;

    @Autowired
    private TimeJobService_2 timeJobService_2;

    @Autowired
    private TzDataMonitoringService tzDataMonitoringService;

    @Autowired
    private MenuTableService menuTableService;

    @Autowired
    private ITZmonitor iTZmonitor;

    @Autowired
    private IAlarmPoint iAlarmPoint;

    @Autowired
    private IEnergyOverview iEnergyOverview;

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private StateEnergyService stateEnergyService;

//    @Autowired
//    private PqmService pqmService;

    @Autowired
    private EemService eemService;

    @Autowired
    private TppService tppService;

    @Autowired
    private IBdzDataMonitoring iBdzDataMonitoring;

    /*########变电站模拟实时表测试部分#########################*/
   /* @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobByTest1min() {
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        timeJobService.TimeJobByTest1min(date);
    }*/
    /*########测试部分#########################*/

    //每分钟请求CVMES的webservice方法
    //@Scheduled(cron="0 0/1 * * * ?")
    public void TimeJobBy1min_CVMES() {
        try {
            if(client==null){
                creatClient();
            }else{
                //判断此连接是否可以正常访问
                URL url = new URL(PATH);
                InputStream in = url.openStream();
            }
            if(client!=null) {
                Map<String,String> codeAndTagnameMap=iTZmonitor.codeAndTagnameMap();
                Object[] objects = new Object[0];
                List<String> dataList=iAlarmPoint.selectAllCode();
                MesParameter mesParameter=new MesParameter();
                mesParameter.setKey("PBSOU-TLQDF-CSYS-LDMES");
                mesParameter.setData(dataList);
                JSONObject json = JSONObject.fromObject(mesParameter);
                String _para=json.toString();
                objects = client.invoke("GetMonitored", _para);
                dataList.clear();
                JSONObject obj = JSONObject.fromObject(objects[0]);
                String update_time = obj.getString("update_time");
                String data = obj.getString("data");
                JSONArray arr = JSONArray.fromObject(data);
                List<TZmonitor> tZmonitorList = new ArrayList<TZmonitor>();
                for (int index = 0; index < arr.size(); index++) {
                    String successed = arr.getJSONObject(index).get("successed").toString();
                    String monitored_code = arr.getJSONObject(index).get("monitored_code").toString();
                    String monitored_parameter = arr.getJSONObject(index).get("monitored_parameter").toString();
                    TZmonitor tZmonitor = new TZmonitor();
                    tZmonitor.setUpdate_time(update_time);
                    if(!StringUtils.isEmpty(successed)){
                        if(successed.equals("true")){
                            successed="1";
                        }else{
                            successed="0";
                        }
                    }
                    tZmonitor.setSuccessed(successed);
                    tZmonitor.setMonitored_code(monitored_code);
                    if(!StringUtils.isEmpty(monitored_code)) tZmonitor.setTagname(codeAndTagnameMap.get(monitored_code.trim()));
                    tZmonitor.setMonitored_parameter(monitored_parameter);
                    tZmonitorList.add(tZmonitor);
                }
                if (tZmonitorList.size() > 0) {
                    iTZmonitor.clearData();
                    iTZmonitor.batchInsert(tZmonitorList);
                    tZmonitorList.clear();
                }
                System.out.println("每分钟请求CVMES的方法结束");

            }
        }catch (ConnectException e) {
            client = null;
            System.out.println("webservice拒绝连接，请检查连接是否有效");
        }catch (Exception e){
            client=null;
            System.out.println("webservice拒绝连接，请检查连接是否有效");
            e.printStackTrace();
        }finally {
            this.TimeJobBy1min();

        }
    }

    @Scheduled(cron="0 30 0/1 * * ?")
    public void statistics() {
        DateTime time = DateUtil.date();
        log.error("1小时计算-开关机:"+ time.toString());
        stateEnergyService.statistics(time);
//        pqmService.asyncLoad(time);
        log.error("1小时计算-能效平衡:"+ time.toString());
        eemService.async(time);
    }

    /**
     *  15分钟计算需量
     */
//    @Scheduled(cron="5 0/15 * * * ? ")
   // public void xl() {
//        pqmService.asyncXl(DateUtil.date());
//        pqmService.asyncDnzl(DateUtil.date());
    //}

    //已合并
    public void tppAsync() {
        tppService.async(null);
    }

    //状态能耗1分钟存储数据的定时方法
    //@Scheduled(cron="0 0/1 * * * ?")
    //@Async("taskExecutorMin")
    public void equipBy1min() {
        DateTime datime=DateUtil.date();
        String hisDate= datime.toString("yyyy-MM-dd");
        String hisTime= datime.toString("HH:mm:ss");
        timeJobService.equipBy1min(hisDate,hisTime);
        String diffTime=this.diffTime(hisDate+" "+hisTime,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("equipBy1min()=====>> "+Thread.currentThread().getName()+" "+hisDate+" "+hisTime+" 状态能耗每分钟存储成功，用时："+diffTime);
        log.info("equipBy1min()=====>> "+Thread.currentThread().getName()+" "+hisDate+" "+hisTime+" 状态能耗每分钟存储成功，用时："+diffTime);
        LogUtil.createLog("equipBy1min.log","equipBy1min()=====>> "+Thread.currentThread().getName()+" "+hisDate+" "+hisTime+" 状态能耗每分钟存储成功，用时："+diffTime);
    }

    //每周一清除日志
    //@Scheduled(cron="0 0 0 ? * MON")
    public void clearLog() {
        LogUtil.clearLog("D:\\liuqi\\scheduledLogs");
    }

    //存储每年的表码值
    @Scheduled(cron="0 0 0 1 1 ?")
    public void TimeJobByYear() {
        DateTime currDate=DateUtil.date();
        String datetime=currDate.toString("yyyy-MM-dd HH:mm:ss");
        timeJobService.TimeJobByYear(datetime);
    }

    //存储每5min的耗值
    @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobBy5minConsume() {
        DateTime datime=DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");

        String dataSourceTableName="";
        String historyTableNameConsume="";
        String cacheName="";
        String condition="" ;

        this.tppAsync();

        //变电站实时数据(行转列)
        iBdzDataMonitoring.insertTimeValue();
        //涂装电行转列
        tzDataMonitoringService.updatePowerMonitorValue("tz_real_time_power","tz_data_monitoring");
        //涂装水行转列
        tzDataMonitoringService.updateWaterMonitorValue("tz_real_time_water","tz_data_monitoring_water");
        //涂装天然气行转列
        tzDataMonitoringService.updateNaturalGasMonitorValue("tz_real_time_NaturalGas","tz_data_monitoring_NaturalGas");
        //涂装压缩空气行转列
        tzDataMonitoringService.updateCompressedAirMonitorValue("tz_real_time_CompressedAir","tz_data_monitoring_CompressdeAir");
        //冲焊电行转列
        tzDataMonitoringService.updatePowerMonitorValue("ch_real_time_power","ch_data_monitoring");
        //冲焊水行转列
        tzDataMonitoringService.updateWaterMonitorValue("ch_real_time_water","ch_data_monitoring_water");
        //冲焊压缩空气行转列
        tzDataMonitoringService.updateCompressedAirMonitorValue("ch_real_time_CompressedAir","ch_data_monitoring_CompressdeAir");
        //车架电行转列
        tzDataMonitoringService.updatePowerMonitorValue("cj_real_time_power","cj_data_monitoring");
        //车架水行转列
        tzDataMonitoringService.updateWaterMonitorValue("cj_real_time_water","cj_data_monitoring_water");
        //车架压缩空气行转列
        tzDataMonitoringService.updateCompressedAirMonitorValue("cj_real_time_CompressedAir","cj_data_monitoring_CompressdeAir");
        //车架天然气行转列
        tzDataMonitoringService.updateNaturalGasMonitorValue("cj_real_time_NaturalGas","cj_data_monitoring_NaturalGas");
        //总装电行转列
        tzDataMonitoringService.updatePowerMonitorValue("zz_real_time_power","zz_data_monitoring");
        //总装压缩空气行转列
        tzDataMonitoringService.updateCompressedAirMonitorValue("zz_real_time_CompressedAir","zz_data_monitoring_CompressdeAir");
        //3pl电行转列
        tzDataMonitoringService.updatePowerMonitorValue("pl_real_time_power","pl_data_monitoring");
        //消防电行转列
        tzDataMonitoringService.updatePowerMonitorValue("xf_real_time_power","xf_data_monitoring");
        //光伏电行转列
        tzDataMonitoringService.updateGfPowerMonitorValue("gf_real_time_power","gf_data_monitoring");


        /*//变电站
        dataSourceTableName="BDZ_real_power";
        historyTableNameConsume="BDZ_5min_consume_power";
        cacheName="BDZpower";
        condition="where RIGHT(tagname,CHARINDEX('_',REVERSE(tagname))-1)='EQ' or RIGHT(tagname,CHARINDEX('_',REVERSE(tagname))-1)='EP'" ;
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,cacheName,condition);
        */
        //油
        dataSourceTableName="tz_real_time_oil";
        historyTableNameConsume="oil_5min_consume_power";
        cacheName="tzOil";
        condition="" ;
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,cacheName,condition);

        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobBy5minConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每5min的耗值成功，用时："+diffTime);
        log.info("TimeJobBy5minConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每5min的耗值成功，用时："+diffTime);
        LogUtil.createLog("TimeJobBy5minConsume.log","TimeJobBy5minConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每5min的耗值成功，用时："+diffTime);
       // LogUtil.createLog("TimeJobBy5minConsumeTotal.log","TimeJobBy5min()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 历史记录总用时："+diffTime);
    }



    //首页曲线图定时计算
    @Scheduled(cron="0 0/10 * * * ?")
    //@Async("taskExecutor_cal")
    public void calculateHomepageData() {
        DateTime datime=DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        homePageService.calculateCurve();
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("calculateHomepageData()====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 每10min首页定时计算执行成功,用时："+diffTime);
        log.info("calculateHomepageData()====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 每10min首页定时计算执行成功,用时："+diffTime);
        LogUtil.createLog("calculateHomepageData.log","calculateHomepageData()====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 每10min首页定时计算执行成功,用时："+diffTime);
    }

    //能耗总览页定时计算
    @Scheduled(cron="0 2 0/1 * * ?")
    //@Async("taskExecutor_cal")
    public void calculateEnergyOverview() {
        DateTime datime=DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        //每小时计算电流、电压缺项报警
        homePageService.calcuWarning();
        homePageService.calculateHomepageData();
        iEnergyOverview.calculateEnergyOverview();
        iEnergyOverview.calculateEnergyOverviewPower();
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("calculateEnergyOverview()====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 每小时能耗总览页定时计算执行成功，用时："+diffTime);
        LogUtil.createLog("calculateEnergyOverview.log","calculateEnergyOverview()====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 每小时能耗总览页定时计算执行成功，用时："+diffTime);
    }



    //删除一年前、20年前的历史记录的定时任务(每月执行一次)
    @Scheduled(cron= "0 2 2 1 * ?")
    public void deleteHisRecord(){
        DateTime datime=DateUtil.date();
        String mm=datime.toString("MM");
        String yy=datime.toString("yyyy");
        int currYear=Integer.parseInt(yy);
        //一年前的年份
        int oneAgo=currYear-1;
        //20年前的年份
        int twentyAgo=currYear-YEAR_NUM;
        int tableNum=twentyAgo%YEAR_NUM+1;
        //删除5分钟的记录
        timeJobService.delRecordByTime("BDZ_power_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_power_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("zz_power_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("ch_power_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_power_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_CompressedAir_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_CompressedAir_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("ch_CompressedAir_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("zz_CompressedAir_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_NaturalGas_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_NaturalGas_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_water_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_water_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("ch_water_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("oil_power_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("pl_power_5min_"+mm,oneAgo+"-"+mm);
        timeJobService.delRecordByTime("BDZ_5min_consume_power",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("ch_5min_consume_CompressedAir",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("ch_5min_consume_power",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("ch_5min_consume_water",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_5min_consume_CompressedAir",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_5min_consume_NaturalGas",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_5min_consume_power",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("cj_5min_consume_water",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("oil_5min_consume_power",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("pl_5min_consume_power",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_5min_consume_CompressedAir",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_5min_consume_NaturalGas",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_5min_consume_power",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("tz_5min_consume_water",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("zz_5min_consume_CompressedAir",oneAgo+"-"+mm);
        timeJobService.delRecordByTime("zz_5min_consume_power",oneAgo+"-"+mm);

        //删除小时的记录
        timeJobService.delRecordByTime("BDZ_power_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_power_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("zz_power_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("ch_power_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_power_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_CompressedAir_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_CompressedAir_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("ch_CompressedAir_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("zz_CompressedAir_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_NaturalGas_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_NaturalGas_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_water_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_water_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("ch_water_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("oil_power_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("pl_power_hour_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("BDZ_hour_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_hour_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("ch_hour_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_hour_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("cj_hour_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("cj_hour_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("cj_hour_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("cj_hour_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("oil_hour_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("pl_hour_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_hour_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("tz_hour_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("tz_hour_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_hour_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("zz_hour_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("zz_hour_consume_power",twentyAgo+"");

        //删除天的记录
        timeJobService.delRecordByTime("BDZ_power_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_power_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("zz_power_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("ch_power_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_power_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_CompressedAir_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_CompressedAir_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("ch_CompressedAir_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("zz_CompressedAir_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_NaturalGas_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_NaturalGas_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("tz_water_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("cj_water_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("ch_water_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("oil_power_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("pl_power_day_"+tableNum,twentyAgo+"");
        timeJobService.delRecordByTime("BDZ_day_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_day_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("ch_day_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_day_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("cj_day_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("cj_day_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("cj_day_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("cj_day_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("oil_day_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("pl_day_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_day_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("tz_day_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("tz_day_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_day_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("zz_day_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("zz_day_consume_power",twentyAgo+"");

        //删除月的记录
        timeJobService.delRecordByTime("BDZ_power_month",twentyAgo+"");
        timeJobService.delRecordByTime("tz_power_month",twentyAgo+"");
        timeJobService.delRecordByTime("zz_power_month",twentyAgo+"");
        timeJobService.delRecordByTime("ch_power_month",twentyAgo+"");
        timeJobService.delRecordByTime("cj_power_month",twentyAgo+"");
        timeJobService.delRecordByTime("tz_CompressedAir_month",twentyAgo+"");
        timeJobService.delRecordByTime("cj_CompressedAir_month",twentyAgo+"");
        timeJobService.delRecordByTime("ch_CompressedAir_month",twentyAgo+"");
        timeJobService.delRecordByTime("zz_CompressedAir_month",twentyAgo+"");
        timeJobService.delRecordByTime("tz_NaturalGas_month",twentyAgo+"");
        timeJobService.delRecordByTime("cj_NaturalGas_month",twentyAgo+"");
        timeJobService.delRecordByTime("tz_water_month",twentyAgo+"");
        timeJobService.delRecordByTime("cj_water_month",twentyAgo+"");
        timeJobService.delRecordByTime("ch_water_month",twentyAgo+"");
        timeJobService.delRecordByTime("oil_power_month",twentyAgo+"");
        timeJobService.delRecordByTime("pl_power_month",twentyAgo+"");
        timeJobService.delRecordByTime("BDZ_month_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_month_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("ch_month_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_month_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("cj_month_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("cj_month_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("cj_month_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("cj_month_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("oil_month_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("pl_month_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_month_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("tz_month_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("tz_month_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_month_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("zz_month_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("zz_month_consume_power",twentyAgo+"");

        //删除年的记录
        timeJobService.delRecordByTime("BDZ_power_year",twentyAgo+"");
        timeJobService.delRecordByTime("tz_power_year",twentyAgo+"");
        timeJobService.delRecordByTime("zz_power_year",twentyAgo+"");
        timeJobService.delRecordByTime("ch_power_year",twentyAgo+"");
        timeJobService.delRecordByTime("cj_power_year",twentyAgo+"");
        timeJobService.delRecordByTime("tz_CompressedAir_year",twentyAgo+"");
        timeJobService.delRecordByTime("cj_CompressedAir_year",twentyAgo+"");
        timeJobService.delRecordByTime("ch_CompressedAir_year",twentyAgo+"");
        timeJobService.delRecordByTime("zz_CompressedAir_year",twentyAgo+"");
        timeJobService.delRecordByTime("tz_NaturalGas_year",twentyAgo+"");
        timeJobService.delRecordByTime("cj_NaturalGas_year",twentyAgo+"");
        timeJobService.delRecordByTime("tz_water_year",twentyAgo+"");
        timeJobService.delRecordByTime("cj_water_year",twentyAgo+"");
        timeJobService.delRecordByTime("ch_water_year",twentyAgo+"");
        timeJobService.delRecordByTime("oil_power_year",twentyAgo+"");
        timeJobService.delRecordByTime("pl_power_year",twentyAgo+"");
        timeJobService.delRecordByTime("BDZ_year_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_year_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("ch_year_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("ch_year_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("cj_year_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("cj_year_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("cj_year_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("cj_year_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("oil_year_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("pl_year_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_year_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("tz_year_consume_NaturalGas",twentyAgo+"");
        timeJobService.delRecordByTime("tz_year_consume_power",twentyAgo+"");
        timeJobService.delRecordByTime("tz_year_consume_water",twentyAgo+"");
        timeJobService.delRecordByTime("zz_year_consume_CompressedAir",twentyAgo+"");
        timeJobService.delRecordByTime("zz_year_consume_power",twentyAgo+"");

    }


    //已合并
    public void TimeJobBy1min() {
        //所有预警的cabinet_number的集合
        List<String> yujingList=new ArrayList<String>();
        //所有报警的cabinet_number的集合
        List<String> baojingList=new ArrayList<String>();
        Map<String,List<String>> tzDataMap=tzDataMonitoringService.selectAllAlarmData("tz_data_monitoring");
        Map<String,List<String>> zzDataMap=tzDataMonitoringService.selectAllAlarmData("zz_data_monitoring");
        Map<String,List<String>> cjDataMap=tzDataMonitoringService.selectAllAlarmData("cj_data_monitoring");
        Map<String,List<String>> chDataMap=tzDataMonitoringService.selectAllAlarmData("ch_data_monitoring");
        yujingList.addAll(tzDataMap.get("yujing"));
        yujingList.addAll(zzDataMap.get("yujing"));
        yujingList.addAll(cjDataMap.get("yujing"));
        yujingList.addAll(chDataMap.get("yujing"));
        baojingList.addAll(tzDataMap.get("baojing"));
        baojingList.addAll(zzDataMap.get("baojing"));
        baojingList.addAll(cjDataMap.get("baojing"));
        baojingList.addAll(chDataMap.get("baojing"));
        //先将数据报警的状态还原
        if(yujingList.size()>0||baojingList.size()>0) menuTableService.resetData_alarm();
        if(yujingList.size()>0) {
            menuTableService.batchUpdateData_alarm(yujingList,"0");
            yujingList.clear();
        }
        if(baojingList.size()>0) {
            menuTableService.batchUpdateData_alarm(baojingList,"1");
            baojingList.clear();
        }
        tzDataMap.clear();zzDataMap.clear();cjDataMap.clear();chDataMap.clear();
    }

    //存储每小时的耗值(将该小时内所有5min的耗值相加)
    //每小时零一分钟执行
    //@Scheduled(cron="0 1 0/1 * * ?")
    //已合并
    public void TimeJobByHourConsume(DateTime datime){
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        String queryHour=datime.toString("yyyy-MM-dd HH");
        Map<String, String> dataMap = timeJobService.selectCurrData(queryHour);
        //存储每小时的耗值(将该小时内所有5min的耗值相加)
        String dataSourceTableName="tz_5min_consume_power";
        String historyTableNameConsume="tz_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //涂装天然气
        dataSourceTableName="tz_5min_consume_NaturalGas";
        historyTableNameConsume="tz_hour_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //涂装水
        dataSourceTableName="tz_5min_consume_water";
        historyTableNameConsume="tz_hour_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //涂装压缩空气
        dataSourceTableName="tz_5min_consume_CompressedAir";
        historyTableNameConsume="tz_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //冲焊电
        dataSourceTableName="ch_5min_consume_power";
        historyTableNameConsume="ch_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //冲焊水
        dataSourceTableName="ch_5min_consume_water";
        historyTableNameConsume="ch_hour_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //冲焊压缩空气
        dataSourceTableName="ch_5min_consume_CompressedAir";
        historyTableNameConsume="ch_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //车架电
        dataSourceTableName="cj_5min_consume_power";
        historyTableNameConsume="cj_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //车架水
        dataSourceTableName="cj_5min_consume_water";
        historyTableNameConsume="cj_hour_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //车架压缩空气
        dataSourceTableName="cj_5min_consume_CompressedAir";
        historyTableNameConsume="cj_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //车架天然气
        dataSourceTableName="cj_5min_consume_NaturalGas";
        historyTableNameConsume="cj_hour_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //总装电
        dataSourceTableName="zz_5min_consume_power";
        historyTableNameConsume="zz_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //总装压缩空气
        dataSourceTableName="zz_5min_consume_CompressedAir";
        historyTableNameConsume="zz_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //变电站
        dataSourceTableName="BDZ_5min_consume_power";
        historyTableNameConsume="BDZ_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //油
        dataSourceTableName="oil_5min_consume_power";
        historyTableNameConsume="oil_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
        //3pl
        dataSourceTableName="pl_5min_consume_power";
        historyTableNameConsume="pl_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);

        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByHourConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每小时的耗值成功,用时："+diffTime);
        log.info("TimeJobByHourConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每小时的耗值成功,用时："+diffTime);
        LogUtil.createLog("TimeJobByHourConsume.log","TimeJobByHourConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每小时的耗值成功,用时："+diffTime);

    }

    //存每天的耗值(将每小时的耗值累加)
    //每天的00:00:00 00:01:30执行
    //@Scheduled(cron="30 1 0 * * ?" )
    //@Async("taskExecutor_cal")
    //已合并
    public void TimeJobByDayConsume(DateTime datime) {
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        //String queryDate=subtractDate(date,"天");
       Map<String, String> dataMap = timeJobService.selectCurrData(date);
        String dataSourceTableName="tz_hour_consume_power";
        String historyTableNameConsume="tz_day_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //涂装天然气
        dataSourceTableName="tz_hour_consume_NaturalGas";
        historyTableNameConsume="tz_day_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //涂装水
        dataSourceTableName="tz_hour_consume_water";
        historyTableNameConsume="tz_day_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //涂装压缩空气
        dataSourceTableName="tz_hour_consume_CompressedAir";
        historyTableNameConsume="tz_day_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //冲焊电
        dataSourceTableName="ch_hour_consume_power";
        historyTableNameConsume="ch_day_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //冲焊水
        dataSourceTableName="ch_hour_consume_water";
        historyTableNameConsume="ch_day_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //冲焊压缩空气
        dataSourceTableName="ch_hour_consume_CompressedAir";
        historyTableNameConsume="ch_day_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //车架电
        dataSourceTableName="cj_hour_consume_power";
        historyTableNameConsume="cj_day_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //车架水
        dataSourceTableName="cj_hour_consume_water";
        historyTableNameConsume="cj_day_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //车架压缩空气
        dataSourceTableName="cj_hour_consume_CompressedAir";
        historyTableNameConsume="cj_day_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //车架天然气
        dataSourceTableName="cj_hour_consume_NaturalGas";
        historyTableNameConsume="cj_day_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //总装电
        dataSourceTableName="zz_hour_consume_power";
        historyTableNameConsume="zz_day_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //总装压缩空气
        dataSourceTableName="zz_hour_consume_CompressedAir";
        historyTableNameConsume="zz_day_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //变电站
        dataSourceTableName="BDZ_hour_consume_power";
        historyTableNameConsume="BDZ_day_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //油
        dataSourceTableName="oil_hour_consume_power";
        historyTableNameConsume="oil_day_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
        //3pl
        dataSourceTableName="pl_hour_consume_power";
        historyTableNameConsume="pl_day_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);

        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByDayConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每天的耗值成功,用时："+diffTime);
        log.info("TimeJobByDayConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每天的耗值成功,用时："+diffTime);
        LogUtil.createLog("TimeJobByDayConsume.log","TimeJobByDayConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每天的耗值成功,用时："+diffTime);
    }

    //存每月的耗值(将每天的耗值累加)
    //月初凌晨零点过2分执行
    //@Scheduled(cron= "0 2 0 1 * ?")
    //@Async("taskExecutor_cal")
    //已合并
    public void TimeJobByMonthConsume(DateTime datime) {
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        String queryMonth=datime.toString("yyyy-MM");
        Map<String, String> dataMap = timeJobService.selectCurrData(queryMonth);
        //存每月的耗值(将每天的耗值累加)
        String dataSourceTableName="tz_day_consume_power";
        String historyTableNameConsume="tz_month_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //涂装天然气
        dataSourceTableName="tz_day_consume_NaturalGas";
        historyTableNameConsume="tz_month_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //涂装水
        dataSourceTableName="tz_day_consume_water";
        historyTableNameConsume="tz_month_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //涂装压缩空气
        dataSourceTableName="tz_day_consume_CompressedAir";
        historyTableNameConsume="tz_month_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //冲焊电
        dataSourceTableName="ch_day_consume_power";
        historyTableNameConsume="ch_month_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //冲焊水
        dataSourceTableName="ch_day_consume_water";
        historyTableNameConsume="ch_month_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //冲焊压缩空气
        dataSourceTableName="ch_day_consume_CompressedAir";
        historyTableNameConsume="ch_month_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //车架电
        dataSourceTableName="cj_day_consume_power";
        historyTableNameConsume="cj_month_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //车架水
        dataSourceTableName="cj_day_consume_water";
        historyTableNameConsume="cj_month_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //车架压缩空气
        dataSourceTableName="cj_day_consume_CompressedAir";
        historyTableNameConsume="cj_month_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //车架天然气
        dataSourceTableName="cj_day_consume_NaturalGas";
        historyTableNameConsume="cj_month_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //总装电
        dataSourceTableName="zz_day_consume_power";
        historyTableNameConsume="zz_month_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //总装压缩空气
        dataSourceTableName="zz_day_consume_CompressedAir";
        historyTableNameConsume="zz_month_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //变电站
        dataSourceTableName="BDZ_day_consume_power";
        historyTableNameConsume="BDZ_month_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //油
        dataSourceTableName="oil_day_consume_power";
        historyTableNameConsume="oil_month_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        //3pl
        dataSourceTableName="pl_day_consume_power";
        historyTableNameConsume="pl_month_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryMonth,date,time,dataMap);
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByMonthConsume()=====>>存储每月的耗值成功,用时："+diffTime);
        log.info("TimeJobByMonthConsume()=====>>存储每月的耗值成功,用时："+diffTime);
        LogUtil.createLog("TimeJobByMonthConsume.log","TimeJobByMonthConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每月的耗值成功,用时："+diffTime);
    }

    //存每年的耗值(将每月的耗值累加)
    //年初凌晨零点2分30秒
    //@Scheduled(cron="0 0 0 1 1 ?")
    //@Async("taskExecutor_cal")
    //已合并
    public void TimeJobByYearConsume(DateTime datime) {
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        String queryYear=datime.toString("yyyy");
        //queryYear=subtractDate(queryYear,"年");
        String[] tables={"tz_year_consume_power","tz_year_consume_NaturalGas","tz_year_consume_water","tz_year_consume_CompressedAir"
                ,"ch_year_consume_power","ch_year_consume_water","ch_year_consume_CompressedAir"
                ,"cj_year_consume_power","cj_year_consume_water","cj_year_consume_CompressedAir","cj_year_consume_NaturalGas"
                ,"zz_year_consume_power","zz_year_consume_CompressedAir","BDZ_year_consume_power","oil_year_consume_power"};
        Map<String, String> dataMap = timeJobService.selectCurrData(queryYear);
        String dataSourceTableName="tz_day_consume_power";
        String historyTableNameConsume="tz_year_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //涂装天然气
        dataSourceTableName="tz_month_consume_NaturalGas";
        historyTableNameConsume="tz_year_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //涂装水
        dataSourceTableName="tz_month_consume_water";
        historyTableNameConsume="tz_year_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //涂装压缩空气
        dataSourceTableName="tz_month_consume_CompressedAir";
        historyTableNameConsume="tz_year_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //冲焊电
        dataSourceTableName="ch_month_consume_power";
        historyTableNameConsume="ch_year_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //冲焊水
        dataSourceTableName="ch_month_consume_water";
        historyTableNameConsume="ch_year_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //冲焊压缩空气
        dataSourceTableName="ch_month_consume_CompressedAir";
        historyTableNameConsume="ch_year_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //车架电
        dataSourceTableName="cj_month_consume_power";
        historyTableNameConsume="cj_year_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //车架水
        dataSourceTableName="cj_month_consume_water";
        historyTableNameConsume="cj_year_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //车架压缩空气
        dataSourceTableName="cj_month_consume_CompressedAir";
        historyTableNameConsume="cj_year_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //车架天然气
        dataSourceTableName="cj_month_consume_NaturalGas";
        historyTableNameConsume="cj_year_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //总装电
        dataSourceTableName="zz_month_consume_power";
        historyTableNameConsume="zz_year_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //总装压缩空气
        dataSourceTableName="zz_month_consume_CompressedAir";
        historyTableNameConsume="zz_year_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //变电站
        dataSourceTableName="BDZ_month_consume_power";
        historyTableNameConsume="BDZ_year_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //油
        dataSourceTableName="oil_month_consume_power";
        historyTableNameConsume="oil_year_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        //3pl
        dataSourceTableName="pl_month_consume_power";
        historyTableNameConsume="pl_year_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryYear,date,time,dataMap);
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByYearConsume()=====>> "+date+" "+time+" 存储每年的耗值成功，用时："+diffTime);
        log.info("TimeJobByYearConsume()=====>> "+date+" "+time+" 存储每年的耗值成功，用时："+diffTime);
        LogUtil.createLog("TimeJobByYearConsume.log","TimeJobByYearConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每年的耗值成功，用时："+diffTime);

    }

    //判断当前日期是否是本月的最后一天
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    //创建动态客户端的方法
    private static void creatClient() {
        try {
            //判断此连接是否可以正常访问
            URL url = new URL(PATH);
            InputStream in = url.openStream();
            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            client = factory.createClient(PATH);
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(2000);  //连接超时
            httpClientPolicy.setAllowChunking(false);    //取消块编码
            httpClientPolicy.setReceiveTimeout(120000);     //响应超时
            conduit.setClient(httpClientPolicy);
        }catch (Exception e) {
            client=null;
            System.out.println("webservice拒绝连接，请检查连接是否有效");
        }
    }

    //根据传入的年月减去对应的周期
    public String subtractDate(String date,String cycle) {
        SimpleDateFormat df=null;
        int type=2;
        if(cycle.equals("时")){
            df = new SimpleDateFormat("yyyy-MM-dd HH");
            type=Calendar.HOUR_OF_DAY;
        }else if(cycle.equals("天")){
            df = new SimpleDateFormat("yyyy-MM-dd");
            type=Calendar.DAY_OF_MONTH;
        }else if(cycle.equals("月")) {
            df = new SimpleDateFormat("yyyy-MM");
            type=Calendar.MONTH;
        }else if(cycle.equals("年")){
            df = new SimpleDateFormat("yyyy");
            type=Calendar.YEAR;
        }
        String returnDate=date;
        try {
            Calendar rightNow = Calendar.getInstance();
            Date begin_d1=df.parse(date);
            rightNow.setTime(begin_d1);
            rightNow.add(type,-1);
            returnDate=df.format(rightNow.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    //计算时间差
    public String diffTime(String startTime,String endTime){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result="";
        try
        {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别
            result=diff/1000+"秒";
        }catch (Exception e){
        }
        return result;
    }

}

