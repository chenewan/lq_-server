package com.byd.emg.controller;


import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.PowerAlarm;
import com.byd.emg.service.HomePageService;
import com.byd.emg.service.IEnergyOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//首页相关的控制类
@RestController
@RequestMapping("/homePage")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private IEnergyOverview iEnergyOverview;

    @RequestMapping("/calculateHomepageData")
    public ServerResponse calculateHomepageData() {
        //homePageService.calculateHomepageData();
        iEnergyOverview.calculateEnergyOverview();
        //iEnergyOverview.calculateEnergyOverviewPower();
        return null;
    }



    /**首页同比环比模块    start*/
    @RequestMapping("/lqEletricityCompare")
    @ResponseBody
    public ServerResponse lqNaturalGasCompare() {

        Map<String,Object> resultMap=homePageService.lqEletricityCompare();
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //商用车总用电
    @RequestMapping("/caEletricityCompare")
    @ResponseBody
    public ServerResponse caEletricityCompare() {

        Map<String,Object> resultMap=homePageService.caEletricityCompare();
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //商用车总用气
    @RequestMapping("/caNaturalGasCompare")
    @ResponseBody
    public ServerResponse caNaturalGasCompare() {

        Map<String,Object> resultMap=homePageService.caNaturalGasCompare();
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //商用车总柴油
    @RequestMapping("/caOilCompare")
    @ResponseBody
    public ServerResponse caOilCompare() {

        Map<String,Object> resultMap=homePageService.caOilCompare();
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }
    /**首页同比环比模块    end*/

    //电-功率模块
    @RequestMapping("/fuHePowerCure")
    @ResponseBody
    public ServerResponse fuHePowerCure() {
        Map<String,List<String[]>> resultMap=homePageService.fuHePowerCure();
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //能耗曲线——天然气(天、时、刻)
    @RequestMapping("/nengHaoNaturalCure")
    @ResponseBody
    public ServerResponse nengHaoNaturalCure(String type) {
        List<String[]> resultList=homePageService.nengHaoNaturalCure(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //电——耗值(天、时、刻)
    @RequestMapping("/nengHaoPowerCure")
    @ResponseBody
    public ServerResponse nengHaoPowerCure(String type) {
        Map<String, List<String[]>> resultMap=homePageService.nengHaoPowerCure(type);
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //柴油——耗值(天、时、刻)
    @RequestMapping("/nengHaoOilCure")
    @ResponseBody
    public ServerResponse nengHaoOilCure(String type) {
        List<String[]> resultList=homePageService.nengHaoOilCure(type);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //能耗标图 开始
    @RequestMapping("/nengHaoBar")
    @ResponseBody
    public ServerResponse nengHaoBar(String type) {
        Map<String, List<String[]>> resultMap=homePageService.nengHaoBar(type);
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //能源结构(扇形图)
    @RequestMapping("/nengHaoSector")
    @ResponseBody
    public ServerResponse nengHaoSector (String type) {
        Map<String, Object> resultMap=homePageService.nengHaoSector(type);
        if(resultMap!=null){
            return ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //系统故障信息栏
    @RequestMapping("/faultInformation")
    @ResponseBody
    public ServerResponse faultInformation () {
        List<PowerAlarm> resultList=homePageService.faultInformation();
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }





}
