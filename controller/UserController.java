package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Acl;
import com.byd.emg.pojo.RoleUser;
import com.byd.emg.pojo.User;
import com.byd.emg.service.AclService;
import com.byd.emg.service.RoleService;
import com.byd.emg.service.RoleUserService;
import com.byd.emg.service.UserService;
import com.byd.emg.util.DateTimeUtil;
import com.byd.emg.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterInvocation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AclService aclService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleUserService iRoleUser;

    //用户登录成功后向前端返回的信息
    @RequestMapping("/loginSuccessful")
    @ResponseBody
    public ServerResponse loginSuccessful(HttpSession session,String username) {
        //根据用户名查询该用户所对用的所有的角色id的集合
        List<Integer> roleIdList=roleService.selectIdsByUsername(username);
        //根据角色id查询该用户所拥有的所有的权限名称
        List<Acl> aclList=aclService.selectByRoleIdList(roleIdList);
        Map<String, Object> map = new HashMap<>();
        Map<String,String> aclNameList=new HashMap<String,String>();
        for(Acl acl:aclList){
            aclNameList.put(acl.getEnglishname(),acl.getName());
        }
        map.put("aclList",aclNameList);
        return ServerResponse.createBySuccess("登录成功",map);
    }

    //用户登录失败后向前端返回的错误信息
    @RequestMapping("/loginError")
    @ResponseBody
    public ServerResponse loginError(HttpSession session) {
        String errorMsg=(String)session.getAttribute("errorMsg");
        session.removeAttribute("errorMsg");
        return ServerResponse.createByErrorMessage(errorMsg);
    }

    //用户登录失败后向前端返回的错误信息
    @RequestMapping("/nologin")
    @ResponseBody
    public ServerResponse nologin() {
        return ServerResponse.createByErrorMessage("您尚未登录，请先登录!");
    }

    //退出系统时，清空session的值
    @RequestMapping("/loginOut")
    public ServerResponse loginOut(HttpSession session) {
        session.invalidate();
        return ServerResponse.createBySuccessMessage("已退出该系统");
    }

    @RequestMapping("/userAll")
    public ServerResponse selectUserAll(@RequestParam(value="keyWord",defaultValue = "") String keyWord) {
        List<User> userList=userService.selectUserAll(keyWord);
        if(userList.size()>0){
            return ServerResponse.createBySuccess("用户信息查询成功",userList);
        }
        return ServerResponse.createByErrorMessage("用户信息查询失败");
    }

    //验证旧密码是否正确的方法
    @RequestMapping("/checkPassword")
    public ServerResponse checkPassword(String password,String username) {
        User user=userService.checkPassword(password,username);
        if(user!=null){
            return ServerResponse.createBySuccess("旧密码正确",user.getId()+"");
        }
        return ServerResponse.createByErrorMessage("您输入的旧密码错误");
    }

    //新增及更新用户信息
    @RequestMapping("/addOrUpdateUser")
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public ServerResponse addOrUpdateUser(User user) {
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        user.setOperatetime(time);
        //根据角色名查询对应的id
        Integer roleId=roleService.selectIdByRoleName(user.getRolename());
        if(user.getId()==null){//新增
            User userExists=userService.selectByUser(user);
            if(userExists!=null) return ServerResponse.createByErrorMessage("该用户已存在");
            if(!StringUtils.isEmpty(user.getPassword())) user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
            int insertCount=userService.insertUser(user);
            int roleUserCount=0;
            if(insertCount>0){
                User userDb=userService.selectByUser(user);
                RoleUser roleUser=new RoleUser();
                roleUser.setUserid(userDb.getId());
                roleUser.setRoleid(roleId);
                roleUser.setOperatetime(time);
                roleUserCount=iRoleUser.insertRoleUser(roleUser);
            }
            if(roleUserCount>0&&insertCount>0) {
                return ServerResponse.createBySuccessMessage("添加成功");
            }else{
                return ServerResponse.createByErrorMessage("添加失败");
            }
        }else{//更新
            if(!StringUtils.isEmpty(user.getPassword())) user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
            int updateCount=userService.updateUser(user);
            RoleUser roleUser=new RoleUser();
            roleUser.setUserid(user.getId());
            roleUser.setRoleid(roleId);
            roleUser.setOperatetime(time);
            if(!StringUtils.isEmpty(user.getRolename())) iRoleUser.updateRoleUser(roleUser);
            if(updateCount>0) {
                return ServerResponse.createBySuccessMessage("更新成功");
            }else{
                return ServerResponse.createByErrorMessage("更新失败");
            }
        }
    }

    //删除用户信息
    @RequestMapping("/deleteUserById")
    public ServerResponse deleteUserById(Integer id) {
        int deleteCount=userService.deleteUserById(id);
        int deleteRoleUser=iRoleUser.deleteRoleUserByUserId(id);
        if(deleteCount>0&&deleteRoleUser>0){
            return ServerResponse.createBySuccessMessage("更新用户信息成功");
        }
        return ServerResponse.createByErrorMessage("更新用户信息失败");
    }

}
