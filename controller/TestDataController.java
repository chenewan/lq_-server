package com.byd.emg.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.byd.emg.common.ServerResponse;

import com.byd.emg.mapper.TimeJobMapper;
import com.byd.emg.service.*;
import com.byd.emg.util.CacheUtil;
import com.byd.emg.util.DateTimeUtil;
import com.byd.emg.util.LogUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

//    /testData/dataCollation
@Log
@RestController
@RequestMapping("testData")
public class TestDataController {

    @Autowired
    private TestDataService testDataService;

    @Autowired
    private TzDataMonitoringService tzDataMonitoringService;

    @Autowired
    private TimeJobService timeJobService;



    @Autowired
    private IEnergyOverview iEnergyOverview;

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private IBdzDataMonitoring iBdzDataMonitoring;

    /**首页同比环比模块    start*/
    @RequestMapping("/dataCollation")
    @ResponseBody
    public ServerResponse dataCollation() {

        boolean result=testDataService.dataCollation();
        if(result){
            return ServerResponse.createBySuccess("查询成功");
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    @RequestMapping("/testHomepage")
    @ResponseBody
    public ServerResponse testHomepage() {
        //homePageService.calculateHomepageData();
        DateTime datime=DateUtil.date();
        String datetime= datime.toString("yyyy-MM-dd HH:mm:ss");
        timeJobService.TimeJobByYear(datetime);
        return ServerResponse.createBySuccessMessage("查询成功");
    }

    @RequestMapping("/test")
    @ResponseBody
    public ServerResponse test() {
        tzDataMonitoringService.updatePowerMonitorValue("ch_real_time_power","ch_data_monitoring");
        //homePageService.calculateCurve();
        return null;
    }

    @RequestMapping("/testHour")
    public void testHour(){
        Date nowDate = new Date();
        String date= DateTimeUtil.dateToStr(nowDate,"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(nowDate,"HH:mm:ss");
        String queryHour=DateTimeUtil.dateToStr(nowDate,"yyyy-MM-dd HH");
        //queryHour="2019-09-03 15";
        System.out.println(time);
        //存储每小时的耗值(将该小时内所有5min的耗值相加)
        /*String dataSourceTableName="tz_5min_consume_power";
        String historyTableNameConsume="tz_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //涂装天然气
        dataSourceTableName="tz_5min_consume_NaturalGas";
        historyTableNameConsume="tz_hour_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //涂装水
        dataSourceTableName="tz_5min_consume_water";
        historyTableNameConsume="tz_hour_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //涂装压缩空气
        dataSourceTableName="tz_5min_consume_CompressedAir";
        historyTableNameConsume="tz_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //冲焊电
        dataSourceTableName="ch_5min_consume_power";
        historyTableNameConsume="ch_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //冲焊水
        dataSourceTableName="ch_5min_consume_water";
        historyTableNameConsume="ch_hour_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //冲焊压缩空气
        dataSourceTableName="ch_5min_consume_CompressedAir";
        historyTableNameConsume="ch_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //车架电
        dataSourceTableName="cj_5min_consume_power";
        historyTableNameConsume="cj_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //车架水
        dataSourceTableName="cj_5min_consume_water";
        historyTableNameConsume="cj_hour_consume_water";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //车架压缩空气
        dataSourceTableName="cj_5min_consume_CompressedAir";
        historyTableNameConsume="cj_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //车架天然气
        dataSourceTableName="cj_5min_consume_NaturalGas";
        historyTableNameConsume="cj_hour_consume_NaturalGas";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //总装电
        dataSourceTableName="zz_5min_consume_power";
        historyTableNameConsume="zz_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //总装压缩空气
        dataSourceTableName="zz_5min_consume_CompressedAir";
        historyTableNameConsume="zz_hour_consume_CompressedAir";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
        //变电站
        dataSourceTableName="BDZ_5min_consume_power";
        historyTableNameConsume="BDZ_hour_consume_power";
        timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time);
*/
        //油
        String dataSourceTableName="tz_real_time_oil";
        String historyTableNameConsume="oil_5min_consume_poower";
        String lastTimeTableName="tz_last_real_oil";
        String condition="" ;
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,lastTimeTableName,condition);

    }

    public void TimeJobByHourConsume(){
        DateTime datime=DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        String queryHour=datime.toString("yyyy-MM-dd HH");
        String[] tables={"tz_hour_consume_power","tz_hour_consume_NaturalGas","tz_hour_consume_water","tz_hour_consume_CompressedAir"
                ,"ch_hour_consume_power","ch_hour_consume_water","ch_hour_consume_CompressedAir"
                ,"cj_hour_consume_power","cj_hour_consume_water","cj_hour_consume_CompressedAir","cj_hour_consume_NaturalGas"
                ,"zz_hour_consume_power","zz_hour_consume_CompressedAir","BDZ_hour_consume_power","oil_hour_consume_power"};
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
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByHourConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每小时的耗值成功,用时："+diffTime);
        //log.info("TimeJobByHourConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每小时的耗值成功,用时："+diffTime);
        LogUtil.createLog("TimeJobByHourConsume.log","TimeJobByHourConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每小时的耗值成功,用时："+diffTime);

    }

    public void TimeJobByDayConsume() {
        DateTime datime=DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        //String queryDate=subtractDate(date,"天");
        String[] tables={"tz_day_consume_power","tz_day_consume_NaturalGas","tz_day_consume_water","tz_day_consume_CompressedAir"
                ,"ch_day_consume_power","ch_day_consume_water","ch_day_consume_CompressedAir"
                ,"cj_day_consume_power","cj_day_consume_water","cj_day_consume_CompressedAir","cj_day_consume_NaturalGas"
                ,"zz_day_consume_power","zz_day_consume_CompressedAir","BDZ_day_consume_power","oil_day_consume_power"};
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
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByDayConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每天的耗值成功,用时："+diffTime);
        //log.info("TimeJobByDayConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每天的耗值成功,用时："+diffTime);
        LogUtil.createLog("TimeJobByDayConsume.log","TimeJobByDayConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每天的耗值成功,用时："+diffTime);
    }

    //存每月的耗值(将每天的耗值累加)
    //月初凌晨零点过2分执行
    //@Scheduled(cron= "0 2 0 1 * ?")
    //@Async("taskExecutor_cal")
    //已合并
    public void TimeJobByMonthConsume() {
        DateTime datime=DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("HH:mm:ss");
        String queryMonth=datime.toString("yyyy-MM");
        String[] tables={"tz_month_consume_power","tz_month_consume_NaturalGas","tz_month_consume_water","tz_month_consume_CompressedAir"
                ,"ch_month_consume_power","ch_month_consume_water","ch_month_consume_CompressedAir"
                ,"cj_month_consume_power","cj_month_consume_water","cj_month_consume_CompressedAir","cj_month_consume_NaturalGas"
                ,"zz_month_consume_power","zz_month_consume_CompressedAir","BDZ_month_consume_power","oil_month_consume_power"};
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
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByMonthConsume()=====>>存储每月的耗值成功,用时："+diffTime);
        //log.info("TimeJobByMonthConsume()=====>>存储每月的耗值成功,用时："+diffTime);
        LogUtil.createLog("TimeJobByMonthConsume.log","TimeJobByMonthConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每月的耗值成功,用时："+diffTime);
    }

    //存每年的耗值(将每月的耗值累加)
    //年初凌晨零点2分30秒
    //@Scheduled(cron="30 2 0 1 1 ?")
    //@Async("taskExecutor_cal")
    //已合并
    public void TimeJobByYearConsume() {
        DateTime datime=DateUtil.date();
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
        String diffTime=this.diffTime(date+" "+time,DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("TimeJobByYearConsume()=====>> "+date+" "+time+" 存储每年的耗值成功，用时："+diffTime);
        //log.info("TimeJobByYearConsume()=====>> "+date+" "+time+" 存储每年的耗值成功，用时："+diffTime);
        LogUtil.createLog("TimeJobByYearConsume.log","TimeJobByYearConsume()=====>> "+Thread.currentThread().getName()+" "+date+" "+time+" 存储每年的耗值成功，用时："+diffTime);

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
            long days = diff / (1000 * 60 * 60 * 24);

            /*long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);*/
            result=diff+"毫秒";
        }catch (Exception e){
        }
        return result;
    }


}
