package com.byd.emg.controller;

/**
 *  状态能耗
 * @author yancq
 * @date 2019/10/8
 */
import cn.hutool.core.date.DateUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.StateSetting;
import com.byd.emg.service.RoleService;
import com.byd.emg.service.StateEnergyService;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/stateEnergy")
public class StateEnergyController {

    @Autowired
    private StateEnergyService stateEnergyService;

    @RequestMapping("/listCurrent")
    public ServerResponse listCurrent(@RequestParam(value = "type") String type) throws Exception {
        return ServerResponse.createBySuccess("查询成功",stateEnergyService.listCurrent(type));
    }


    @RequestMapping("/listAll")
    public ServerResponse listAll(@RequestParam(value = "ids") String ids,@RequestParam(value = "cycle") String cycle,@RequestParam(value = "type") String type,
                                  @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",stateEnergyService.listAll(ids,cycle,type,startTime,endTime));
    }

    @RequestMapping("/list")
    public ServerResponse list(@RequestParam(value = "ids") String ids,@RequestParam(value = "cycle") String cycle,@RequestParam(value = "type") String type,
                               @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws ParseException {
        return ServerResponse.createBySuccess("查询成功",stateEnergyService.list(ids,cycle,type,startTime,endTime));
    }

    @RequestMapping("/listTime")
    public ServerResponse listTime(@RequestParam(value = "ids") String ids, @RequestParam(value = "startTime") String startTime,@RequestParam(value = "endTime") String endTime) throws Exception {
        return ServerResponse.createBySuccess("查询成功",stateEnergyService.listTime(ids,startTime,endTime));
    }



    @RequestMapping("/getNav")
    public ServerResponse getNav(@RequestParam String type) {
        return ServerResponse.createBySuccess("查询成功",stateEnergyService.getNav(type));
    }


    @RequestMapping("/getSetting")
    public ServerResponse getSetting(@RequestParam String workshopId) {
        return ServerResponse.createBySuccess("查询成功",stateEnergyService.getSetting(workshopId));
    }

    @RequestMapping("/save")
    public ServerResponse save(@RequestBody List<StateSetting> settings) {
        return ServerResponse.createBySuccess("报存成功",stateEnergyService.save(settings));
    }


    @RequestMapping("/statistics")
    public ServerResponse statistics(@RequestParam String dateTime) {
        stateEnergyService.statistics(DateUtil.parseDateTime(dateTime));
        return ServerResponse.createBySuccess("报存成功",true);
    }



    @RequestMapping("/export")
    public ServerResponse export(@RequestBody String body) {
        return ServerResponse.createBySuccess("报存成功",stateEnergyService.export(JSONObject.fromObject(body)));
    }

    @RequestMapping("/import")
    public ServerResponse importSet(@RequestParam("file") MultipartFile file) {

        return ServerResponse.createBySuccess("报存成功",stateEnergyService.importSet(file));
    }

    @RequestMapping("/exportSet")
    public ServerResponse export(@RequestBody List<StateSetting> settings) {
        return ServerResponse.createBySuccess("报存成功",stateEnergyService.exportSet(settings));
    }
}
