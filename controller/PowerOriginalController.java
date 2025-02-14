package com.byd.emg.controller;


import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.ResultPageData;
import com.byd.emg.service.PowerOriginalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/energyPowerPage")
public class PowerOriginalController {

    @Autowired
    private PowerOriginalService powerOriginalService;

    @RequestMapping("/energyPowerPageList")
    @ResponseBody
    public ServerResponse<PageInfo<ResultPageData>> getPowerPageData(@RequestParam(value = "pageNum") int pageNum,
                                                                     @RequestParam(value = "pageSize") int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<ResultPageData> po= powerOriginalService.getPower_Page_1();
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>(po);
        if(po.size()>0){


            return  ServerResponse.createBySuccess("电力分部图分页成功",pageResult);
        }else{

            return ServerResponse.createByErrorMessage("电力分部图分页失败");
        }
    }
}
