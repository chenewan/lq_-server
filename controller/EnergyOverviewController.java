package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.exportutil.TzDataMonitoringDbToExcelUtil;
import com.byd.emg.pojo.TzDataMonitoring;
import com.byd.emg.resultentity.OverviewSectorData;
import com.byd.emg.service.IEnergyOverview;
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

//能耗总览的控制类
@RestController
@RequestMapping("/energyOverview")
public class EnergyOverviewController {

    @Autowired
    private IEnergyOverview iEnergyOverview;

    //变电站柱状图
    @RequestMapping("/getBdzBar")
    public ServerResponse getBdzBar(String type)  {
        List<String[]> resultList=iEnergyOverview.getBdzBar(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //变电站扇形图
    @RequestMapping("/getBdzSector")
    public ServerResponse getBdzSector(String type)  {
        List<OverviewSectorData> resultList=iEnergyOverview.getBdzSector(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //变电站折线图
    @RequestMapping("/getBdzBrokenLine")
    public ServerResponse getBdzBrokenLine(String type,String workShop)  {
        List<String[]> resultList=iEnergyOverview.getBdzBrokenLine(type,workShop);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //车间柱状图
    @RequestMapping("/getWorkShopBar")
    public ServerResponse getWorkShopBar(String type)  {
        List<String[]> resultList=iEnergyOverview.getWorkShopBar(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //车间扇形图
    @RequestMapping("/getWorkShopSector")
    public ServerResponse getWorkShopSector(String type)  {
        List<OverviewSectorData>  resultList=iEnergyOverview.getWorkShopSector(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //车间下拉选(包含3PL车间)
    @RequestMapping("/workShopList")
    public ServerResponse workShopList()  {
        List<String>  resultList=iEnergyOverview.workShopList();
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //车间折线图
    @RequestMapping("/getWorkShopBrokenLine")
    public ServerResponse getWorkShopBrokenLine(String type,String workShop)  {
        List<String[]> resultList=iEnergyOverview.getWorkShopBrokenLine(type,workShop);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //变压器车间下拉选(不包含3PL车间)
    @RequestMapping("/transformerWorkShopList")
    public ServerResponse transformerWorkShopList()  {
        List<String>  resultList=iEnergyOverview.transformerWorkShopList();
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //变压器下拉选
    @RequestMapping("/transformerList")
    public ServerResponse transformerList(String workShop)  {
        List<String>  resultList=iEnergyOverview.transformerList(workShop);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //变压器柱状图
    @RequestMapping("/getTransformerBar")
    public ServerResponse getTransformerBar(String type,String workShop)  {
        List<String[]> resultList=iEnergyOverview.getTransformerBar(type,workShop);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //变压器扇形图
    @RequestMapping("/getTransformerSector")
    public ServerResponse getTransformerSector(String type,String workShop)  {
        List<OverviewSectorData> resultList=iEnergyOverview.getTransformerSector(type,workShop);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //变压器折线图
    @RequestMapping("/getTransformerBrokenLine")
    public ServerResponse getTransformerBrokenLine(String type,String workShop,String transformerName)  {
        List<String[]> resultList=iEnergyOverview.getTransformerBrokenLine(type,workShop,transformerName);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //柴油柱状图
    @RequestMapping("/getOilBar")
    public ServerResponse getOilBar(String type)  {
        List<String[]> resultList=iEnergyOverview.getOilBar(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //柴油扇形图
    @RequestMapping("/getOilSector")
    public ServerResponse getOilSector(String type)  {
        List<OverviewSectorData> resultList=iEnergyOverview.getOilSector(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //柴油车间下拉选
    @RequestMapping("/oilWorkShopList")
    public ServerResponse oilWorkShopList()  {
        List<String> resultList=iEnergyOverview.oilWorkShopList();
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }


    //柴油曲线(柱状)图
    @RequestMapping("/getOilLine")
    public ServerResponse getOilLine(String type,String workShop)  {
        List<String[]> resultList=iEnergyOverview.getOilLine(type,workShop);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //天然气柱状图
    @RequestMapping("/getNaturalGasBar")
    public ServerResponse getNaturalGasBar(String type)  {
        List<String[]> resultList=iEnergyOverview.getNaturalGasBar(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //天然气扇形图
    @RequestMapping("/getNaturalGasSector")
    public ServerResponse getNaturalGasSector(String type)  {
        List<OverviewSectorData> resultList=iEnergyOverview.getNaturalGasSector(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //天然气车间下拉选
    @RequestMapping("/naturalGasWorkShopList")
    public ServerResponse naturalGasWorkShopList()  {
        List<String> resultList=iEnergyOverview.naturalGasWorkShopList();
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //天然气曲线(柱状)图
    @RequestMapping("/getNaturalGasLine")
    public ServerResponse getNaturalGasLine(String type,String workShop)  {
        List<String[]> resultList=iEnergyOverview.getNaturalGasLine(type,workShop);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }





}
