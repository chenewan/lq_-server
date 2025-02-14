package com.byd.emg.controller;


import com.byd.emg.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//向前端返回错误信息的控制类
@Controller
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping("/noauthorityError")
    @ResponseBody
    public ServerResponse noauthorityError() {
        return ServerResponse.createByErrorMessage("没有访问权限，如需要访问，请联系管理员");
    }
}
