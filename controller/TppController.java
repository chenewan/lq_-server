package com.byd.emg.controller;

/**
 * @author yancq
 * @date 2019/11/24
 */

import cn.hutool.core.date.DateUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.StateSetting;
import com.byd.emg.pojo.TppSetting;
import com.byd.emg.service.PqmService;
import com.byd.emg.service.TppService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.script.ScriptException;
import java.util.List;

@RestController
@RequestMapping("/tpp")
public class TppController {

    @Autowired
    private TppService tppService;

    @RequestMapping("/getNav")
    public ServerResponse getNav(@RequestParam String type) {
        return ServerResponse.createBySuccess("查询成功",tppService.getNav(type));
    }

    @RequestMapping("/listSet")
    public ServerResponse listSet(@RequestParam String parentId) {
        return ServerResponse.createBySuccess("查询成功",tppService.listSet(parentId));
    }

    @RequestMapping("/exportSet")
    public ServerResponse export(@RequestBody List<TppSetting> settings) {
        return ServerResponse.createBySuccess("报存成功",tppService.exportSet(settings));
    }


    @RequestMapping("/save")
    public ServerResponse save(@RequestBody List<TppSetting> settings) {
        return ServerResponse.createBySuccess("报存成功",tppService.save(settings));
    }

    @RequestMapping("/import")
    public ServerResponse importSet(@RequestParam("file") MultipartFile file) {
        return ServerResponse.createBySuccess("报存成功",tppService.importSet(file));
    }



    @RequestMapping("/async")
    public ServerResponse async(String time) {
        tppService.async(time);
        return ServerResponse.createBySuccess("查询成功",true);
    }

    @RequestMapping("/getRealAndSet")
    public ServerResponse getRealAndSet(@RequestParam String ids) throws ScriptException {
        return ServerResponse.createBySuccess("查询成功",tppService.getRealAndSet(ids));
    }


    @RequestMapping("/getList")
    public ServerResponse getList(@RequestParam("ids") String ids,@RequestParam("cycle") String cycle,
                                  @RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) {
        return ServerResponse.createBySuccess("查询成功",tppService.getList(ids,cycle,startTime,endTime));
    }


}
