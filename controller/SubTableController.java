package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Sampling_period_5min;
import com.byd.emg.service.SubTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//分表测试方法
@RestController
@RequestMapping("/subTable")
public class SubTableController {

    @Autowired
    private SubTableService subTableService;

    @RequestMapping("/subTableMethod")
    public ServerResponse subTableMethod(){
        subTableService.subTableMethod();
       return null;
    }

    @RequestMapping("/dataCollation")
    public ServerResponse dataCollation(){
        subTableService.dataCollation();
        return null;
    }

}
