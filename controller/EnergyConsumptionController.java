package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.exportutil.EnergyConsumptionDbToExcelUtil;
import com.byd.emg.exportutil.EnergyValueCompareDbToExcelUtil;
import com.byd.emg.exportutil.Sampling_period_5minDbToExcelUtil;
import com.byd.emg.service.EnergyConsumptionService;
import com.byd.emg.util.DateTimeUtil;
import com.byd.emg.util.ExcelWriteOutUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

//单元能耗控制类
@RestController
@RequestMapping("/energyConsumption")
public class EnergyConsumptionController {

    @Autowired
    private EnergyConsumptionService energyConsumptionService;

    /**实际单元能耗
     * @param cycle                 周期
     * @param startDate             yyyy-MM-dd
     * @param endDate               yyyy-MM-dd
     * @param element               元素
     * @param ids      节点id的集合
     * @return
     */
    @RequestMapping("/energyValue")
    @ResponseBody
    public ServerResponse<List<Object[]>> energyValue(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate",defaultValue ="") String endDate,
            @RequestParam(value = "element") String element, //类型
            @RequestParam(value = "ids") List<String> ids){
        List<Object[]> valueList = energyConsumptionService.getEnergyValue(cycle,startDate, endDate, element,ids);
        if (valueList.size() > 0) {
            return ServerResponse.createBySuccess("查询成功", valueList);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }


    /**
     * 能耗对比分析
     * @param cycle              周期
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param startDate_2      参考开始时间
     * @param endDate_2        参考结束时间
     * @param element          类型
     * @param ids              节点id的集合
     * @return
     */

    @RequestMapping("/energyValueCompare")
    @ResponseBody
    public ServerResponse<Map<String,List<Object[]>>> energyValueCompare(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "startDate_2") String startDate_2,
            @RequestParam(value = "endDate_2") String endDate_2,
            @RequestParam(value = "element",defaultValue ="实际") String element, //类型
            @RequestParam(value = "ids") List<String> ids){
        Map<String,List<Object[]>> valueMap= energyConsumptionService.getEnergyValueCompare(cycle,startDate, endDate,startDate_2,endDate_2, element,ids);
        if (valueMap!=null) {
            return ServerResponse.createBySuccess("查询成功",valueMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //单元能耗导出
    /**实际单元能耗
     * @param cycle                 周期
     * @param startDate             yyyy-MM-dd
     * @param endDate               yyyy-MM-dd
     * @param element               元素
     * @param ids      节点id的集合
     * @return
     */
    @RequestMapping("/exportEnergyValue")
    @ResponseBody
    public ServerResponse exportEnergyValue(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate",defaultValue ="") String endDate,
            @RequestParam(value = "element") String element, //类型
            @RequestParam(value = "ids") List<String> ids){
        List<Object[]> valueList = energyConsumptionService.getEnergyValue(cycle,startDate, endDate, element,ids);
        Map<String,String> equipmentMap=energyConsumptionService.getLablesById(ids);
        HSSFWorkbook wb= EnergyConsumptionDbToExcelUtil.energyConsumptionDbToExcel(valueList,equipmentMap);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile="单元能耗"+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }

    /**
     * 对比分析导出
     * @param cycle              周期
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param startDate_2      参考开始时间
     * @param endDate_2        参考结束时间
     * @param element          类型
     * @param ids              节点id的集合
     * @return
     */

    @RequestMapping("/exportEnergyValueCompare")
    @ResponseBody
    public ServerResponse exportEnergyValueCompare(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "startDate_2") String startDate_2,
            @RequestParam(value = "endDate_2") String endDate_2,
            @RequestParam(value = "element",defaultValue ="实际") String element, //类型
            @RequestParam(value = "ids") List<String> ids){
        Map<String,List<Object[]>> valueMap= energyConsumptionService.getEnergyValueCompare(cycle,startDate, endDate,startDate_2,endDate_2, element,ids);
        Map<String,String> equipmentMap=energyConsumptionService.getLablesById(ids);
        HSSFWorkbook wb= EnergyValueCompareDbToExcelUtil.energyValueCompareDbToExcel(valueMap,equipmentMap);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile="单元能耗对比分析"+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }









}
