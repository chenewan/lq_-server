package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Equipment;
import com.byd.emg.resultentity.EquipDeception;
import com.byd.emg.resultentity.EquipZtAlarmData;
import com.byd.emg.service.IEquipAlarmOriginal;
import com.byd.emg.service.IEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private IEquipment iEquipment;

    @Autowired
    private IEquipAlarmOriginal iEquipAlarmOriginal;

    //返回设备的网络图结构及报警状态,(勾选导航栏时，请求此方法)
    @RequestMapping("/networkData")
    public ServerResponse networkData(String name) {
        List<Equipment> equipmentList=iEquipment.networkData(name);
        if(equipmentList.size()>0){
            return ServerResponse.createBySuccess("查询成功",equipmentList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //返回设备描述(鼠标移入图标时，显示信息；移出图标时，不显示)
    @RequestMapping("/returnDesception")
    public ServerResponse returnDesception(String tagname) {
        EquipDeception equipment=iEquipAlarmOriginal.selectEquipDesception(tagname);
        if(equipment!=null){
            return ServerResponse.createBySuccess("查询成功",equipment);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //故障管理
    @RequestMapping("/ztAlarm")
    public ServerResponse ztAlarm(String name) {
        List<EquipZtAlarmData> ZtAlarmList=iEquipment.ztAlarm(name);
        if(ZtAlarmList.size()>0){
            return ServerResponse.createBySuccess("查询成功",ZtAlarmList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }


}
