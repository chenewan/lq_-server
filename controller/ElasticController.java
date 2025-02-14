package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.service.impl.BaseElasticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yancq
 * @date 2020/3/29
 */

@Slf4j
@RequestMapping("/es")
@RestController
public class ElasticController {

    @Autowired
    BaseElasticService baseElasticService;

    //
    @RequestMapping("/list")
    public ServerResponse listPower(@RequestParam(value = "ids") String ids, @RequestParam(value = "type") String type, @RequestParam(value = "cycle") String cycle,
                                    @RequestParam(value = "outType") String outType, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",baseElasticService.listPower(ids,type,cycle,
                startTime,endTime,outType));
    }

    @RequestMapping("/listXl")
    public ServerResponse listXl(@RequestParam(value = "ids") String ids, @RequestParam(value = "type") String type, @RequestParam(value = "cycle") String cycle,
                                    @RequestParam(value = "outType") String outType, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",baseElasticService.listXl(ids,type,cycle,
                startTime,endTime,outType));
    }


    @RequestMapping("/listLoad")
    public ServerResponse listLoad(@RequestParam(value = "ids") String ids,@RequestParam(value = "types") String types,@RequestParam(value = "cycle") String cycle,
                                   @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception{
        return ServerResponse.createBySuccess("查询成功",baseElasticService.listLoad(ids,types,cycle,startTime,endTime));
    }


    @RequestMapping("/listTime")
    public ServerResponse listTime(@RequestParam(value = "ids") String ids, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",baseElasticService.listTime(ids,
                startTime,endTime));
    }

}
