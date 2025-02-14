package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.exportutil.DflzmDbToExcelUtil;
import com.byd.emg.exportutil.Sampling_period_5minDbToExcelUtil;
import com.byd.emg.exportutil.TzDataMonitoringDbToExcelUtil;
import com.byd.emg.pojo.*;
import com.byd.emg.service.HistoryRecordService;
import com.byd.emg.service.MenuTableService;
import com.byd.emg.service.TzDataMonitoringService;
import com.byd.emg.util.DateTimeUtil;
import com.byd.emg.util.ExcelWriteOutUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;
import java.util.Map;

//导出excel表格的控制类
@RestController
@RequestMapping("/exportExcel")
public class ExportExcelController {

    @Autowired
    private HistoryRecordService historyRecordService;

    @Autowired
    private TzDataMonitoringService TzDataMonitoringService;

    @Autowired
    private MenuTableService menuTableService;

    /**
     * @param changing:介质(水、电、汽、油)变换，默认为”电“
     * @return
     */
    //导出Sampling_period_5min数据
    @RequestMapping("/sampling_period_5minDbToExcel")
    public ServerResponse sampling_period_5minDbToExcel(@RequestParam(value = "cycle") String cycle, //周期
                                                        @RequestParam(value = "startTime") String startTime,
                                                        @RequestParam(value = "endTime") String endTime,
                                                        @RequestParam(value = "startDate") String startDate,
                                                        @RequestParam(value = "endDate") String endDate,
                                                        @RequestParam(value = "type") String type, //类型
                                                        @RequestParam(value = "cabinet_number") List<String> cabinet_number,
                                                        @RequestParam(value = "showValue")String showValue,
                                                        @RequestParam(value = "changing",defaultValue = "电") String changing){
        String excelFileName="";
        String tabelName="menu_table_power";
        if(changing.equals("电")){
            excelFileName="查询分析_电表";
            tabelName="menu_table_power";
        }else if(changing.equals("水")){
            excelFileName="查询分析_水表";
            tabelName="menu_table_water";
        }else if(changing.equals("压缩空气")){
            excelFileName="查询分析_压缩空气表";
            tabelName="menu_table_CompressdeAir";
        }else if(changing.equals("天然气")){
            excelFileName="查询分析_天然气表";
            tabelName="menu_table_NaturalGas";
        }else if(changing.equals("油")){
            excelFileName="查询分析_柴汽油";
            tabelName="menu_table_oil";
        }
        Map<String,Object>  resultMap=historyRecordService.exportExcelData(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing,tabelName);
        HSSFWorkbook wb= Sampling_period_5minDbToExcelUtil.sampling_period_5minDbToExcel(resultMap,cabinet_number,excelFileName);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=excelFileName+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }

    //导出区治能耗数据
    @RequestMapping("/dflzmDbToExcel")
    public ServerResponse dflzmDbToExcel(String startDate,
                                         String endDate,
                                         String tags,
                                         String cycle,
                                         String changing)  {
        String excelFileName=menuTableService.selectByValue(tags);
        List<String[]> historyRecordsList = historyRecordService.getDflzmEnergy(startDate,endDate,tags,cycle,changing);
        HSSFWorkbook wb= DflzmDbToExcelUtil.dflzmDbToExcel(historyRecordsList,excelFileName);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=excelFileName+"_"+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }

    //导出tz_data_monitoring数据(电)
    @RequestMapping("/dataMonitoringDbToExcel")
    public ServerResponse dataMonitoringDbToExcel(@RequestParam(value = "cabinet_number") List<String> cabinet_number,
                                                  @Param(value="changing") String changing)  {
        String table_name="tz_data_monitoring";
        String sheetNmae="数据监控_涂装车间_电表";
        if(changing.contains("涂装")){
            table_name="tz_data_monitoring";
            sheetNmae="数据监控_涂装车间_电表";
        }else if(changing.contains("冲焊")){
            table_name="ch_data_monitoring";
            sheetNmae="数据监控_冲焊车间_电表";
        }else if(changing.contains("车架")){
            table_name="cj_data_monitoring";
            sheetNmae="数据监控_车架车间_电表";
        }else if(changing.contains("总装")){
            table_name="zz_data_monitoring";
            sheetNmae="数据监控_总装车间_电表";
        }else if(changing.contains("变电站")){
            table_name="BDZ_data_monitoring";
            sheetNmae="数据监控_变电站_电表";
        }
        List<TzDataMonitoring> tzDataMonitoringList=TzDataMonitoringService.AllDataByTzDataMonitoring(cabinet_number,table_name);
        //定义一个数组，来存储列标题
        String[] headers={"序号","柜体名称","A相电压(KV)","B相电压(KV)","C相电压(KV)","A相电流(A)","B相电流(A)","C相电流(A)",
                "有功功率(KW)","无功功率(KW)","功率因数(PF)","总有功表码值(KW/H)","总无功表码值(KW/H)"};
        HSSFWorkbook wb= TzDataMonitoringDbToExcelUtil.tzDataMonitoringDbToExcel(headers,tzDataMonitoringList,sheetNmae);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=sheetNmae+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }

    //导出tz_data_monitoring_water数据(涂装水)
    @RequestMapping("/dataMonitoringDbToExcelWater")
    public ServerResponse dataMonitoringDbToExcelWater(@RequestParam(value = "cabinet_number") List<String> cabinet_number,
                                                       @Param(value="changing") String changing)  {
        String table_name="tz_data_monitoring_water";
        String sheetName="数据监控_涂装车间_水表";
        if(changing.contains("涂装")){
            table_name="tz_data_monitoring_water";
            sheetName="数据监控_涂装车间_水表";
        }else if(changing.contains("冲焊")){
            table_name="ch_data_monitoring_water";
            sheetName="数据监控_冲焊车间_水表";
        }else if(changing.contains("车架")){
            table_name="cj_data_monitoring_water";
            sheetName="数据监控_车架车间_水表";
        }else if(changing.contains("总装")){
            table_name="zz_data_monitoring_water";
            sheetName="数据监控_总装车间_水表";
        }
        List<TzDataMonitoring_water> tzDataMonitoringList=TzDataMonitoringService.AllDataByTzDataMonitoring_water(cabinet_number,table_name);
        //定义一个数组，来存储列标题
        String[] headers={"序号","柜体名称","瞬时流量(m³/h)","累计流量(m³/h)","压力(Mpa)"};
        HSSFWorkbook wb= TzDataMonitoringDbToExcelUtil.tzDataMonitoringDbToExcel(headers,tzDataMonitoringList,sheetName);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=sheetName+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }

    //导出tz_data_monitoring_NaturalGas数据(涂装天然气)
    @RequestMapping("/dataMonitoringDbToExcelNaturalGas")
    public ServerResponse dataMonitoringDbToExcelNaturalGas(@RequestParam(value = "cabinet_number") List<String> cabinet_number,
                                                       @Param(value="changing") String changing)  {
        String table_name="tz_data_monitoring_NaturalGas";
        String sheetName="数据监控_涂装车间_天然气表";
        if(changing.contains("涂装")){
            table_name="tz_data_monitoring_NaturalGas";
            sheetName="数据监控_涂装车间_天然气表";
        }else if(changing.contains("冲焊")){
            table_name="ch_data_monitoring_NaturalGas";
            sheetName="数据监控_冲焊车间_天然气表";
        }else if(changing.contains("车架")){
            table_name="cj_data_monitoring_NaturalGas";
            sheetName="数据监控_车架车间_天然气表";
        }else if(changing.contains("总装")){
            table_name="zz_data_monitoring_NaturalGas";
            sheetName="数据监控_总装车间_天然气表";
        }
        List<TzDataMonitoring_NaturalGas> tzDataMonitoringList=TzDataMonitoringService.AllDataByTzDataMonitoring_NaturalGas(cabinet_number,table_name);
        //定义一个数组，来存储列标题
        String[] headers={"序号","柜体名称","流速(m³/h)","流量(m³/h)","累计流量(m³/h)","反向累计流量(m³/h)","流体温度(℃)","环境温度(℃)","管道压力(MPa)"};
        HSSFWorkbook wb= TzDataMonitoringDbToExcelUtil.tzDataMonitoringDbToExcel(headers,tzDataMonitoringList,sheetName);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=sheetName+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }

    //导出tz_data_monitoring_CompressdeAir数据(压缩空气)
    @RequestMapping("/dataMonitoringDbToExcelCompressdeAir")
    public ServerResponse dataMonitoringDbToExcelCompressdeAir(@RequestParam(value = "cabinet_number") List<String> cabinet_number,
                                                            @Param(value="changing") String changing)  {
        String table_name="tz_data_monitoring_CompressdeAir";
        String sheetName="数据监控_涂装车间_压缩空气表";
        if(changing.contains("涂装")){
            table_name="tz_data_monitoring_CompressdeAir";
            sheetName="数据监控_涂装车间_压缩空气表";
        }else if(changing.contains("冲焊")){
            table_name="ch_data_monitoring_CompressdeAir";
            sheetName="数据监控_冲焊车间_压缩空气表";
        }else if(changing.contains("车架")){
            table_name="cj_data_monitoring_CompressdeAir";
            sheetName="数据监控_车架车间_压缩空气表";
        }else if(changing.contains("总装")){
            table_name="zz_data_monitoring_CompressdeAir";
            sheetName="数据监控_总装车间_压缩空气表";
        }
        List<TzDataMonitoring_CompressdeAir> tzDataMonitoringList=TzDataMonitoringService.AllDataByTzDataMonitoring_CompressedAir(cabinet_number,table_name);
        //定义一个数组，来存储列标题
        String[] headers={"序号","柜体名称","瞬时流量(m³)","累计流量(m³)","压力(MPa)"};
        HSSFWorkbook wb= TzDataMonitoringDbToExcelUtil.tzDataMonitoringDbToExcel(headers,tzDataMonitoringList,sheetName);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=sheetName+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }


}
