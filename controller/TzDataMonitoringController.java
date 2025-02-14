package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.*;
import com.byd.emg.service.TzDataMonitoringService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/TzDataMonitoring")
public class TzDataMonitoringController {

    @Autowired
    private TzDataMonitoringService tzDataMonitoringService;

    //返回所有预报警和报警的tagname
    @RequestMapping("/tagnameList")
    @ResponseBody
    public ServerResponse tagnameList(){
        Map<String,Map<String,List<String>>> resultMap=new HashMap<String,Map<String,List<String>>>();
        Map<String,List<String>> tzDataMap=tzDataMonitoringService.selectAllAlarmData("tz_data_monitoring");
        Map<String,List<String>> zzDataMap=tzDataMonitoringService.selectAllAlarmData("zz_data_monitoring");
        Map<String,List<String>> cjDataMap=tzDataMonitoringService.selectAllAlarmData("cj_data_monitoring");
        Map<String,List<String>> chDataMap=tzDataMonitoringService.selectAllAlarmData("ch_data_monitoring");
        resultMap.put("tz",tzDataMap);
        resultMap.put("zz",zzDataMap);
        resultMap.put("cj",cjDataMap);
        resultMap.put("ch",chDataMap);
        if(tzDataMap.values().size()>0||zzDataMap.values().size()>0||cjDataMap.values().size()>0||chDataMap.values().size()>0){
            return  ServerResponse.createBySuccess("查询成功",resultMap);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    //变电站电表监控实时查询
    @RequestMapping("/bdzTable")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring>> getPowerPageData_power_bdz(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                             @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                             @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("变电站电表监控实时查询失败");
        }
        String table_name="BDZ_data_monitoring";
        PageInfo<TzDataMonitoring> pageResult = new PageInfo<TzDataMonitoring>();
        List<TzDataMonitoring> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("变电站电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("变电站电表监控实时查询失败");
        }
    }

    //涂装电表监控实时查询
    @RequestMapping("/table")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring>> getPowerPageData_power(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                       @RequestParam(value = "pageSize",defaultValue = "20") int pageSize,
                                                                       @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("涂装电表监控实时查询失败");
        }
        String table_name="tz_data_monitoring";
        PageInfo<TzDataMonitoring> pageResult = new PageInfo<TzDataMonitoring>();
        List<TzDataMonitoring> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("涂装电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装电表监控实时查询失败");
        }
    }

    //总装电表监控实时查询
    @RequestMapping("/table_zz")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring>> getPowerPageData_power_zz(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                             @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                             @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("总装电表监控实时查询失败");
        }
        String table_name="zz_data_monitoring";
        PageInfo<TzDataMonitoring> pageResult = new PageInfo<TzDataMonitoring>();
        List<TzDataMonitoring> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("总装电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("总装电表监控实时查询失败");
        }
    }

    //总装电表监控实时查询
    @RequestMapping("/table_gf")
    @ResponseBody
    public ServerResponse<PageInfo<GfDataMonitoring>> getPowerPageData_power_gf(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("光伏实时查询失败");
        }
        String table_name="gf_data_monitoring";
        PageInfo<GfDataMonitoring> pageResult = new PageInfo<GfDataMonitoring>();
        List<GfDataMonitoring> tdm= tzDataMonitoringService.AllDataByGfDataMonitoring(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("光伏实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("光伏实时查询失败");
        }
    }

    //冲焊电表监控实时查询
    @RequestMapping("/table_ch")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring>> getPowerPageData_power_ch(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("冲焊电表监控实时查询失败");
        }
        String table_name="ch_data_monitoring";
        PageInfo<TzDataMonitoring> pageResult = new PageInfo<TzDataMonitoring>();
        List<TzDataMonitoring> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("冲焊电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("冲焊电表监控实时查询失败");
        }
    }

    //车架电表监控实时查询
    @RequestMapping("/table_cj")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring>> getPowerPageData_power_cj(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("车架电表监控实时查询失败");
        }
        String table_name="cj_data_monitoring";
        PageInfo<TzDataMonitoring> pageResult = new PageInfo<TzDataMonitoring>();
        List<TzDataMonitoring> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("车架电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架电表监控实时查询失败");
        }
    }

    //涂装水表监控实时查询
    @RequestMapping("/table_water")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_water>> getPowerPageData_water(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                   @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                   @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("涂装水表监控实时查询失败");
        }
        String table_name="tz_data_monitoring_water";
        PageInfo<TzDataMonitoring_water> pageResult = new PageInfo<TzDataMonitoring_water>();
        List<TzDataMonitoring_water> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_water(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("涂装水表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装水表监控实时查询失败");
        }
    }

    //冲焊水表监控实时查询
    @RequestMapping("/table_water_ch")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_water>> getPowerPageData_water_ch(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                             @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                             @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("冲焊水表监控实时查询失败");
        }
        String table_name="ch_data_monitoring_water";
        PageInfo<TzDataMonitoring_water> pageResult = new PageInfo<TzDataMonitoring_water>();
        List<TzDataMonitoring_water> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_water(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult .setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("冲焊水表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("冲焊水表监控实时查询失败");
        }
    }

    //车架水表监控实时查询
    @RequestMapping("/table_water_cj")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_water>> getPowerPageData_water_cj(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("车架水表监控实时查询失败");
        }
        String table_name="cj_data_monitoring_water";
        PageInfo<TzDataMonitoring_water> pageResult = new PageInfo<TzDataMonitoring_water>();
        List<TzDataMonitoring_water> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_water(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult .setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("车架水表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架水表监控实时查询失败");
        }
    }

    //涂装压缩空气监控实时查询
    @RequestMapping("/table_CompressedAir")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_CompressdeAir>> getPowerPageData_CompressedAir(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                       @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                       @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("涂装压缩空气监控实时查询失败");
        }
        String table_name="tz_data_monitoring_CompressdeAir";
        PageInfo<TzDataMonitoring_CompressdeAir> pageResult = new PageInfo<TzDataMonitoring_CompressdeAir>();
        List<TzDataMonitoring_CompressdeAir> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_CompressedAir(cabinet_number,table_name,pageNum,pageSize,pageResult);
        pageResult .setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("涂装压缩空气监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装压缩空气监控实时查询失败");
        }
    }

    //总装压缩空气监控实时查询
    @RequestMapping("/table_CompressedAir_zz")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_CompressdeAir>> getPowerPageData_CompressedAir_zz(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                     @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                     @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("总装压缩空气监控实时查询失败");
        }
        String table_name="zz_data_monitoring_CompressdeAir";
        PageInfo<TzDataMonitoring_CompressdeAir> pageResult = new PageInfo<TzDataMonitoring_CompressdeAir>();
        List<TzDataMonitoring_CompressdeAir> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_CompressedAir(cabinet_number,table_name,pageNum,pageSize, pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("总装压缩空气监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("总装压缩空气监控实时查询失败");
        }
    }

    //冲焊压缩空气监控实时查询
    @RequestMapping("/table_CompressedAir_ch")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_CompressdeAir>> getPowerPageData_CompressedAir_ch(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                        @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                        @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("冲焊压缩空气监控实时查询失败");
        }
        String table_name="ch_data_monitoring_CompressdeAir";
        PageInfo<TzDataMonitoring_CompressdeAir> pageResult = new PageInfo<TzDataMonitoring_CompressdeAir>();
        List<TzDataMonitoring_CompressdeAir> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_CompressedAir(cabinet_number,table_name,pageNum,pageSize, pageResult);
        pageResult .setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("冲焊压缩空气监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("冲焊压缩空气监控实时查询失败");
        }
    }

    //车架压缩空气监控实时查询
    @RequestMapping("/table_CompressedAir_cj")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_CompressdeAir>> getPowerPageData_CompressedAir_cj(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                        @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                        @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("冲焊压缩空气监控实时查询失败");
        }
        String table_name="cj_data_monitoring_CompressdeAir";
        PageInfo<TzDataMonitoring_CompressdeAir> pageResult = new PageInfo<TzDataMonitoring_CompressdeAir>();
        List<TzDataMonitoring_CompressdeAir> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_CompressedAir(cabinet_number,table_name,pageNum,pageSize, pageResult);
        pageResult .setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("车架压缩空气监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架压缩空气监控实时查询失败");
        }
    }

    //涂装天然气监控实时查询
    @RequestMapping("/table_NaturalGas")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_NaturalGas>> getPowerPageData_NaturalGas(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                       @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                       @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("涂装天然气监控实时查询失败");
        }
        String table_name="tz_data_monitoring_NaturalGas";
        PageInfo<TzDataMonitoring_NaturalGas> pageResult = new PageInfo<TzDataMonitoring_NaturalGas>();
        List<TzDataMonitoring_NaturalGas> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_NaturalGas(cabinet_number,table_name,pageNum,pageSize, pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("涂装天然气监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装天然气监控实时查询失败");
        }
    }

    //车架天然气监控实时查询
    @RequestMapping("/table_NaturalGas_cj")
    @ResponseBody
    public ServerResponse<PageInfo<TzDataMonitoring_NaturalGas>> getPowerPageData_NaturalGas_cj(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum,
                                                                                  @RequestParam(value = "pageSize",defaultValue ="20") int pageSize,
                                                                                  @RequestParam(value = "cabinet_number") List<String> cabinet_number){
        if(cabinet_number==null||cabinet_number.size()==0){
            return ServerResponse.createBySuccessMessage("涂装天然气监控实时查询失败");
        }
        String table_name="cj_data_monitoring_NaturalGas";
        PageInfo<TzDataMonitoring_NaturalGas> pageResult = new PageInfo<TzDataMonitoring_NaturalGas>();
        List<TzDataMonitoring_NaturalGas> tdm= tzDataMonitoringService.AllDataByTzDataMonitoring_NaturalGas(cabinet_number,table_name,pageNum,pageSize, pageResult);
        pageResult.setList(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("车架天然气监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架天然气监控实时查询失败");
        }
    }

}
