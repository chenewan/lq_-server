package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.WeatherHistory;
import com.byd.emg.service.WeatherHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/weatherHistory")
public class WeatherHistoryController {

    @Autowired
    private WeatherHistoryService weatherHistoryService;

    //查询当前的最新的实时天气
    @RequestMapping("/weather_current")
    @ResponseBody
    public ServerResponse weather_current() {
        WeatherHistory weatherHistory=null;
        weatherHistory=weatherHistoryService.selectCurrentWeather();
        if(weatherHistory!=null){
            return ServerResponse.createBySuccess("查询成功", weatherHistory);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

}
