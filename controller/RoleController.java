package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/allRoleName")
    public ServerResponse selectAllRoleName() {
        List<String> roleNameList=roleService.selectAllRoleName();
        if(roleNameList.size()>0){
            return ServerResponse.createBySuccess("查询成功",roleNameList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }


}
