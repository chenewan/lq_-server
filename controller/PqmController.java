package com.byd.emg.controller;

/**
 *  电能质量监控
 * @author yancq
 * @date 2019/10/8
 */
import cn.hutool.core.date.DateUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.service.EemService;
import com.byd.emg.service.PqmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/pqm")
public class PqmController {

    @Autowired
    private PqmService pqmService;

    @RequestMapping("/getNav")
    public ServerResponse getNav(@RequestParam String type) {
        return ServerResponse.createBySuccess("查询成功",pqmService.getNav(type));
    }


    @RequestMapping("/getReal")
    public ServerResponse getReal(@RequestParam(value = "ids") String ids,@RequestParam(value = "workshops") String workshops) {
        return ServerResponse.createBySuccess("查询成功",pqmService.getReal(ids,workshops));
    }



    @RequestMapping("/list")
    public ServerResponse listPower(@RequestParam(value = "ids") String ids,@RequestParam(value = "types") String types,@RequestParam(value = "cycle") String cycle,
                                    @RequestParam(value = "outType") String outType,@RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",pqmService.list(ids,types,cycle,
                startTime,endTime,outType));
    }


    @RequestMapping("/asyncDnzl")
    public ServerResponse asyncDnzl(@RequestParam String dateTime) {
        pqmService.asyncDnzl(DateUtil.parseDateTime(dateTime));
        return ServerResponse.createBySuccess("查询成功",true);
    }


    @RequestMapping("/getTransformer")
    public ServerResponse getTransformer() {
        return ServerResponse.createBySuccess("查询成功",pqmService.getTransformer());
    }


    @RequestMapping("/asyncLoad")
    public ServerResponse asyncLoad(@RequestParam String dateTime) {
        pqmService.asyncLoad(DateUtil.parseDateTime(dateTime));
        return ServerResponse.createBySuccess("查询成功",true);
    }

    @RequestMapping("/listXl")
    public ServerResponse listXl(@RequestParam(value = "ids") String ids,@RequestParam(value = "type") String type,@RequestParam(value = "cycle") String cycle,
                                 @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",pqmService.listXl(ids,type,cycle,startTime,endTime));
    }

    @RequestMapping("/asyncXl")
    public ServerResponse asyncXl(@RequestParam String dateTime) {
        pqmService.asyncXl(DateUtil.parseDateTime(dateTime));
        return ServerResponse.createBySuccess("查询成功",true);
    }

    @RequestMapping("/listLoad")
    public ServerResponse listLoad(@RequestParam(value = "ids") String ids,@RequestParam(value = "types") String types,@RequestParam(value = "cycle") String cycle,
                                   @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception{
        return ServerResponse.createBySuccess("查询成功",pqmService.listLoad(ids,types,cycle,startTime,endTime));
    }





}
