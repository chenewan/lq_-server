package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.ResultPageData;
import com.byd.emg.service.PowerDistributionSearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/energyPowerTable")
public class PowerDistributionSearchController {
    @Autowired
    private PowerDistributionSearchService powerDistributionSearchService;


    @RequestMapping("/getAllTable")
    @ResponseBody

    public ServerResponse<PageInfo<ResultPageData>> getPowerPageData(@RequestParam(value = "pageNum") int pageNum,
                                                                     @RequestParam(value = "pageSize") int pageSize,
                                                                     @RequestParam(value = "area") String area,
                                                                     @RequestParam(value = "plant") String plant,
                                                                     @RequestParam(value = "cabinet_name") String cabinet_name){

        PageHelper.startPage(pageNum,pageSize);
        List<ResultPageData> pt= powerDistributionSearchService.power_table(area,plant,cabinet_name);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>(pt);
        if(pt.size()>0){


            return  ServerResponse.createBySuccess("电力分部检索分页成功",pageResult);
        }else{

            return ServerResponse.createByErrorMessage("电力分部检索分页失败");
        }
    }

}
