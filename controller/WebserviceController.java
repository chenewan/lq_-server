package com.byd.emg.controller;

import com.byd.emg.pojo.TZmonitor;
import com.byd.emg.resultentity.MesParameter;
import com.byd.emg.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.byd.emg.mapper.TimeJobMapper;
import com.byd.emg.pojo.TZmonitor;
import com.byd.emg.util.DateTimeUtil;
import lombok.extern.java.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.apache.cxf.endpoint.Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("webservice")
@Log
public class WebserviceController {

    Logger log_1 = LoggerFactory.getLogger(WebserviceController.class);
    private static Client client;

    @Autowired
    private ITZmonitor iTZmonitor;

    @Autowired
    private IAlarmPoint iAlarmPoint;

    //private static final String PATH="http://172.15.16.66:9233/MES?wsdl";
    private static final String PATH="http://172.29.0.51:9233/MES?wsdl";

    @RequestMapping("timeJobs")
    public void timeJobs(){
        try {
            if(client==null){
                creatClient();
            }else{
                //判断此连接是否可以正常访问
                URL url = new URL(PATH);
                InputStream in = url.openStream();
            }
            if(client!=null) {
                Object[] objects = new Object[0];
                List<String> dataList=iAlarmPoint.selectAllCode();
                MesParameter mesParameter=new MesParameter();
                mesParameter.setKey("PBSOU-TLQDF-CSYS-LDMES");
                mesParameter.setData(dataList);
                JSONObject json = JSONObject.fromObject(mesParameter);
                String _para=json.toString();
                objects = client.invoke("GetMonitored", _para);
                JSONObject obj = JSONObject.fromObject(objects[0]);
                String update_time = obj.getString("update_time");
                String data = obj.getString("data");
                JSONArray arr = JSONArray.fromObject(data);
                List<TZmonitor> tZmonitorList = new ArrayList<TZmonitor>();
                for (int index = 0; index < arr.size(); index++) {
                    String successed = arr.getJSONObject(index).get("successed").toString();
                    String monitored_code = arr.getJSONObject(index).get("monitored_code").toString();
                    String monitored_parameter = arr.getJSONObject(index).get("monitored_parameter").toString();
                    TZmonitor tZmonitor = new TZmonitor();
                    tZmonitor.setUpdate_time(update_time);
                    tZmonitor.setSuccessed(successed);
                    tZmonitor.setMonitored_code(monitored_code);
                    tZmonitor.setMonitored_parameter(monitored_parameter);
                    tZmonitorList.add(tZmonitor);
                }
                if (tZmonitorList.size() > 0) iTZmonitor.batchInsert(tZmonitorList);
                for(TZmonitor tZmonitor:tZmonitorList){
                    log_1.info(tZmonitor.getUpdate_time());
                    System.out.println(tZmonitor.getUpdate_time());
                    log_1.info(tZmonitor.getSuccessed());
                    System.out.println(tZmonitor.getSuccessed());
                    log_1.info(tZmonitor.getMonitored_code());
                    System.out.println(tZmonitor.getMonitored_code());
                    log_1.info(tZmonitor.getMonitored_parameter());
                    System.out.println(tZmonitor.getMonitored_parameter());
                }
                System.out.println("请求完毕");
                log_1.info("每分钟请求完毕=====================================================》");

            }
       }catch (Exception e){
            client=null;
            System.out.println("webservice拒绝连接，请检查连接是否有效");
        }
    }

    //创建动态客户端的方法
    public  void creatClient() {
        try {
            //判断此连接是否可以正常访问
            URL url = new URL(PATH);
            InputStream in = url.openStream();
            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            client = factory.createClient(PATH);
            /*HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(2000);  //连接超时
            httpClientPolicy.setAllowChunking(false);    //取消块编码
            httpClientPolicy.setReceiveTimeout(120000);     //响应超时
            conduit.setClient(httpClientPolicy);*/
        }catch (Exception e) {
            log_1.info("错误",e);
            e.printStackTrace();
        }
    }
}
