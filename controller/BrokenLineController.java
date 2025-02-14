package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;

import com.byd.emg.pojo.Sampling_period_5min;
import com.byd.emg.resultentity.ContrastData;
import com.byd.emg.service.HistoryRecordService;
import com.byd.emg.service.RealTimeValueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

//获取折线图数据的控制类
@Controller
//
@RequestMapping("/brokenLine")
public class BrokenLineController {

    @Autowired
    private HistoryRecordService historyRecordService;

    @Autowired
    private RealTimeValueService realTimeValueService;

    /**
     * @param changing:介质(水、电、汽)变换，默认为”电“
     * @return
     */
    @RequestMapping("/brokeLineRealValue")
    @ResponseBody
    public ServerResponse<List<String[]>> brokeLineRealValue(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "type") String type, //类型
            @RequestParam(value = "cabinet_number") List<String> cabinet_number,
            @RequestParam(value = "showValue") String showValue,
            //@RequestParam(value = "showType") String showType,
            @RequestParam(value = "changing",defaultValue = "电") String changing){
        List<String[]> historyRecordsList = historyRecordService.getHistoryRecordByTagname(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing);
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("折线图数据获取成功", historyRecordsList);
        }
        return ServerResponse.createBySuccessMessage("折线图数据获取失败");
    }

    /**
     *  区治能耗查询
     * @param cycle        周期(一天)
     * @param startDate    开始日期("yyyy-MM-dd")
     * @param endDate      结束日期("yyyy-MM-dd")
     * @param tags         变量集合
     * @param  changing     介质(电、气)
     */
    @RequestMapping("/dflzmEnergy")
    @ResponseBody
    public ServerResponse<List<String[]>> dflzmEnergy(String startDate,
                                                      String endDate,
                                                      String tags,
                                                      String cycle,
                                                      String changing){
        List<String[]> historyRecordsList = historyRecordService.getDflzmEnergy(startDate,endDate,tags,cycle,changing);
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("查询成功", historyRecordsList);
        }
        return ServerResponse.createBySuccessMessage("无数据");
    }



    /**同比分析的方法
     * @param id          节点id
     * @param cycle                    周期(日、月、年)
     * @param active_date       对比的起始时间和结束时间
     * @param passive_date         被对比的起始时间和结束时间
     * @param changing                介质(水、电、汽)变换，默认为”电“
     * @return
     */
    @RequestMapping("/contrastMethod")
    @ResponseBody
    public ServerResponse contrastMethod(String id,
                                         String cycle,
                                         @RequestParam(value = "active_date")  List<String> active_date,
                                         @RequestParam(value = "passive_date")  List<String> passive_date,
                                         @RequestParam(value = "changing",defaultValue = "电") String changing){
        Map<String,Object> resultMap=historyRecordService.contrastMethod(id,cycle,active_date,passive_date,changing.trim());
        List<ContrastData> contrastDataList=(List<ContrastData>)resultMap.get("resultList");
        String errorMessage="";
        if(resultMap.get("errorMessage")!=null) errorMessage=resultMap.get("errorMessage").toString();
        if(contrastDataList.size()>0&& StringUtils.isEmpty(errorMessage)){
            return ServerResponse.createBySuccess("同比分析成功", contrastDataList);
        }
        if(!StringUtils.isEmpty(errorMessage)) {
            return ServerResponse.createBySuccessMessage(errorMessage);
        }
        return ServerResponse.createBySuccessMessage("同比分析失败");
    }

    /**
     * @param changing:介质(水、电、汽)变换，默认为”电“
     * @return
     */
    @RequestMapping("/getminbymin")
    @ResponseBody
    public ServerResponse<List<List<Sampling_period_5min>>> getminbymin(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "type") String type, //类型
            @RequestParam(value = "cabinet_number") List<String> cabinet_number,
            @RequestParam(value = "showValue")String showValue,
            @RequestParam(value = "changing",defaultValue = "电") String changing){
        List<List<Sampling_period_5min>>DateList =historyRecordService.getminbymin(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing);
        if (DateList.size() > 0) {
            return ServerResponse.createBySuccess("获取分钟最小值成功", DateList);
        }
        return ServerResponse.createBySuccessMessage("获取分钟最小值失败");
    }

    /**
     * @param changing:介质(水、电、汽)变换，默认为”电“
     * @return
     */
    @RequestMapping("/getmaxbymin")
    @ResponseBody
    public ServerResponse<List<List<Sampling_period_5min> >> getmaxbymin(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startTime") String startTime,//开始时间
            @RequestParam(value = "endTime") String endTime, //结束时间
            @RequestParam(value = "startDate") String startDate,//开始日期
            @RequestParam(value = "endDate") String endDate,//结束日期
            @RequestParam(value = "type") String type, //类型
            @RequestParam(value = "cabinet_number") List<String> cabinet_number,
            @RequestParam(value = "showValue")String showValue,
            @RequestParam(value = "changing",defaultValue = "电") String changing){

        List<List<Sampling_period_5min>> DateList =historyRecordService.getmaxbymin(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing);

        if (DateList.size() > 0) {

            return ServerResponse.createBySuccess("获取分钟最大值成功", DateList);
        }

        return ServerResponse.createBySuccessMessage("获取分钟最大值失败");
    }

}




