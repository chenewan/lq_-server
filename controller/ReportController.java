package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.resultentity.Report;
import com.byd.emg.service.ReportService;
import com.byd.emg.service.ReportToPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


//生成报表的控制类
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportToPointService reportToPointService;

    @RequestMapping("/createExcel")
    public ServerResponse createExcel(@RequestParam(value = "paths",defaultValue ="") String paths,
                                      @RequestParam(value = "date",defaultValue ="") String date,
                                      @RequestParam(value = "startTime",defaultValue ="") String startTime,
                                      @RequestParam(value = "endTime",defaultValue ="") String endTime) {
        ServerResponse ss=null;
        //功能编码，"1"表示班次报表，8点到8点
        String funCode=paths.split("_")[5];
        if(funCode.equals("1")){
            ss=reportToPointService.createExcelToPoint(paths,date,startTime,endTime);
        }else{
            ss=reportService.createExcel(paths,date);
        }
        return ss;
    }

    @RequestMapping("/getHistoryReport")
    public ServerResponse getHistoryReport(String paths) {
        List<Report> reportList = reportService.getHistoryReport(paths);
        return ServerResponse.createBySuccess(reportList);
    }

    @RequestMapping("/deleteReport")
    public ServerResponse deleteReport(String path) {
        ServerResponse ss = reportService.deleteReport(path);
        return ss;
    }

    //更新报表的日结算日期
    @RequestMapping("/updateReportTime")
    public void updateReportTime(String label,String startTime,String endTime) {
        reportToPointService.updateReportTime(label,startTime,endTime);
    }








}
