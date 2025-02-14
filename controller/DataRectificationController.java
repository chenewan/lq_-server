package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.exportutil.DataRectificationDbToExcelUtil;
import com.byd.emg.exportutil.Sampling_period_5minDbToExcelUtil;
import com.byd.emg.resultentity.DataRectificationParameter;
import com.byd.emg.service.HistoryRecordService;
import com.byd.emg.util.DateTimeUtil;
import com.byd.emg.util.ExcelWriteOutUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exportDataRectification")
public class DataRectificationController {

    @Autowired
    private HistoryRecordService historyRecordService;

    //导出数据
    @RequestMapping("/historyRecordsDbToExcel")
    public ServerResponse historyRecordsDbToExcel(@RequestParam(value = "cycle") String cycle, //周期
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
            excelFileName="电表";
            tabelName="menu_table_power";
        }else if(changing.equals("水")){
            excelFileName="水表";
            tabelName="menu_table_water";
        }else if(changing.equals("压缩空气")){
            excelFileName="压缩空气表";
            tabelName="menu_table_CompressdeAir";
        }else if(changing.equals("天然气")){
            excelFileName="天然气表";
            tabelName="menu_table_NaturalGas";
        }
        DataRectificationParameter dataParameter=new DataRectificationParameter(cycle,startTime,endTime,startDate,endDate,type, cabinet_number, showValue,changing);
        Map<String,Object> resultMap=historyRecordService.exportExcelData(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing,tabelName);
        HSSFWorkbook wb= DataRectificationDbToExcelUtil.dataRectificationDbToExcel(resultMap,cabinet_number,dataParameter,excelFileName);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=excelFileName+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }



    //导入纠正的数据

}
