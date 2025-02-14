package com.byd.emg.controller;

/**
 *  状态能耗
 * @author yancq
 * @date 2019/10/8
 */
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.StateSetting;
import com.byd.emg.service.EemService;
import com.byd.emg.service.StateEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/eem")
public class EemController {

    @Autowired
    private EemService eemService;

    @RequestMapping("/getPowerNav")
    public ServerResponse getPowerNav() throws ParseException {
        return ServerResponse.createBySuccess("查询成功",eemService.getPowerNav());
    }

    @RequestMapping("/listPower")
    public ServerResponse listPower(@RequestParam(value = "ids") String ids,@RequestParam(value = "cycle") String cycle,
                               @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",eemService.listPower(ids,cycle,startTime,endTime));
    }

    @RequestMapping("/getGasNav")
    public ServerResponse getGasNav() throws ParseException {
        return ServerResponse.createBySuccess("查询成功",eemService.getGasNav());
    }

    @RequestMapping("/listGas")
    public ServerResponse listGas(@RequestParam(value = "ids") String ids,@RequestParam(value = "cycle") String cycle,
                                    @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",eemService.listGas(ids,cycle,startTime,endTime));
    }


    @RequestMapping("/getOilNav")
    public ServerResponse getOilNav() throws ParseException {
        return ServerResponse.createBySuccess("查询成功",eemService.getOilNav());
    }

    @RequestMapping("/listOil")
    public ServerResponse listOil(@RequestParam(value = "ids") String ids,@RequestParam(value = "cycle") String cycle,
                                  @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",eemService.listOil(ids,cycle,startTime,endTime));
    }
    @RequestMapping("/getPower")
    public ServerResponse getPower(@RequestParam(value = "cycle") String cycle,@RequestParam(value = "pvs") String pvs) throws Exception {
        return ServerResponse.createBySuccess("查询成功",eemService.getPower(cycle,pvs));
    }

    @RequestMapping("/getGas")
    public ServerResponse getGas(@RequestParam(value = "cjs") String cjs,@RequestParam(value = "cycle") String cycle,
                                 @RequestParam(value = "tzs") String tzs,@RequestParam(value = "counts") String counts) throws Exception {
        return ServerResponse.createBySuccess("查询成功",eemService.getGas(cjs,tzs,counts,cycle));
    }

    @RequestMapping("/getOil")
    public ServerResponse getOil(@RequestParam(value = "ids") String ids,@RequestParam(value = "cycle") String cycle,
                                 @RequestParam(value = "counts") String counts) throws Exception {
        return ServerResponse.createBySuccess("查询成功",eemService.getOil(ids,counts,cycle));
    }


    @RequestMapping("/async")
    public ServerResponse async(@RequestParam String dateTime)  {
        eemService.async(DateUtil.parseDateTime(dateTime));
        return ServerResponse.createBySuccess("查询成功",true);
    }


}
