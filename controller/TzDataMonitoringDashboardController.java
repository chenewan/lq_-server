package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.EnergyMeterPage;
import com.byd.emg.pojo.ResourceMeter;
import com.byd.emg.pojo.ResultPageData;
import com.byd.emg.service.TzDataMonitoringDashboardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping("/energyMeterPage")
public class TzDataMonitoringDashboardController {

    @Autowired
    private TzDataMonitoringDashboardService tzDataMonitoringDashboardService;

    //涂装电表仪表盘分页查询
    @RequestMapping("/energyMeterPageList")
    @ResponseBody
    public ServerResponse<PageInfo> EnergyMeterPagination(@RequestParam(value = "pageNum") int pageNum,
                                                          @RequestParam(value = "pageSize") int pageSize,
                                                          @RequestParam(value = "Search_parameters") String Search_parameters,
                                                          @RequestParam(value = "cabinet_number")List<String> cabinet_number
                                                           ){
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="tz_data_monitoring_dashboard";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("涂装车间电表仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装车间电表仪表盘分页失败");
        }
    }

    //总装电表仪表盘分页查询
    @RequestMapping("/energyMeterPageList_zz")
    @ResponseBody
    public ServerResponse<PageInfo> EnergyMeterPagination_zz(@RequestParam(value = "pageNum") int pageNum,
                                                          @RequestParam(value = "pageSize") int pageSize,
                                                          @RequestParam(value = "Search_parameters") String Search_parameters,
                                                          @RequestParam(value = "cabinet_number")List<String> cabinet_number
    ){
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="zz_data_monitoring_dashboard";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("总装车间电表仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("总装车间电表仪表盘分页失败");
        }
    }

    //冲焊电表仪表盘分页查询
    @RequestMapping("/energyMeterPageList_ch")
    @ResponseBody
    public ServerResponse<PageInfo> EnergyMeterPagination_ch(@RequestParam(value = "pageNum") int pageNum,
                                                             @RequestParam(value = "pageSize") int pageSize,
                                                             @RequestParam(value = "Search_parameters") String Search_parameters,
                                                             @RequestParam(value = "cabinet_number")List<String> cabinet_number
    ){
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="ch_data_monitoring_dashboard";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("冲焊车间电表仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("冲焊车间电表仪表盘分页失败");
        }
    }

    //车架电表仪表盘分页查询
    @RequestMapping("/energyMeterPageList_cj")
    @ResponseBody
    public ServerResponse<PageInfo> EnergyMeterPagination_cj(@RequestParam(value = "pageNum") int pageNum,
                                                             @RequestParam(value = "pageSize") int pageSize,
                                                             @RequestParam(value = "Search_parameters") String Search_parameters,
                                                             @RequestParam(value = "cabinet_number")List<String> cabinet_number
    ){
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="cj_data_monitoring_dashboard";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("车架车间电表仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架车间电表仪表盘分页失败");
        }
    }

    //涂装车间天然气仪表盘分页查询
    @RequestMapping("/naturalGasPageList")
    @ResponseBody
    public ServerResponse<PageInfo> naturalGasPageList(@RequestParam(value = "pageNum") int pageNum,
                                                       @RequestParam(value = "pageSize") int pageSize,
                                                       @RequestParam(value = "Search_parameters") String Search_parameters,
                                                       @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="tz_data_monitoring_dashboard_NaturalGas";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getNaturalGasPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);

            return  ServerResponse.createBySuccess("涂装车间天然气仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装车间天然气仪表盘分页失败");
        }
    }

    //车架车间天然气仪表盘分页查询
    @RequestMapping("/naturalGasPageList_cj")
    @ResponseBody
    public ServerResponse<PageInfo> naturalGasPageList_cj(@RequestParam(value = "pageNum") int pageNum,
                                                       @RequestParam(value = "pageSize") int pageSize,
                                                       @RequestParam(value = "Search_parameters") String Search_parameters,
                                                       @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="cj_data_monitoring_dashboard_NaturalGas";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getNaturalGasPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);

            return  ServerResponse.createBySuccess("车架车间天然气仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架车间天然气仪表盘分页失败");
        }
    }

    //涂装车间水表仪表盘分页
    @RequestMapping("/waterPageList")
    @ResponseBody
    public ServerResponse<PageInfo> waterPageList(@RequestParam(value = "pageNum") int pageNum,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "Search_parameters") String Search_parameters,
                                                  @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="tz_data_monitoring_dashboard_water";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getWaterPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("涂装车间水表仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装车间水表仪表盘分页失败");
        }
    }

    //冲焊车间水表仪表盘分页
    @RequestMapping("/waterPageList_ch")
    @ResponseBody
    public ServerResponse<PageInfo> waterPageList_ch(@RequestParam(value = "pageNum") int pageNum,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "Search_parameters") String Search_parameters,
                                                  @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="ch_data_monitoring_dashboard_water";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getWaterPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("冲焊车间水表仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("冲焊车间水表仪表盘分页失败");
        }
    }

    //车架车间水表仪表盘分页
    @RequestMapping("/waterPageList_cj")
    @ResponseBody
    public ServerResponse<PageInfo> waterPageList_cj(@RequestParam(value = "pageNum") int pageNum,
                                                     @RequestParam(value = "pageSize") int pageSize,
                                                     @RequestParam(value = "Search_parameters") String Search_parameters,
                                                     @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="cj_data_monitoring_dashboard_water";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getWaterPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("车架车间水表仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架车间水表仪表盘分页失败");
        }
    }

    //涂装车间压缩空气仪表盘分页
    @RequestMapping("/compressedAirPageList")
    @ResponseBody
    public ServerResponse<PageInfo> compressedAirPageList(@RequestParam(value = "pageNum") int pageNum,
                                                          @RequestParam(value = "pageSize") int pageSize,
                                                          @RequestParam(value = "Search_parameters") String Search_parameters,
                                                          @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="tz_data_monitoring_dashboard_CompressedAir";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getCompressedAirPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("涂装车间压缩空气仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("涂装车间压缩空气仪表盘分页失败");
        }
    }

    //总装车间压缩空气仪表盘分页
    @RequestMapping("/compressedAirPageList_zz")
    @ResponseBody
    public ServerResponse<PageInfo> compressedAirPageList_zz(@RequestParam(value = "pageNum") int pageNum,
                                                          @RequestParam(value = "pageSize") int pageSize,
                                                          @RequestParam(value = "Search_parameters") String Search_parameters,
                                                          @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="zz_data_monitoring_dashboard_CompressedAir";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getCompressedAirPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("总装车间压缩空气仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("总装车间压缩空气仪表盘分页失败");
        }
    }

    //冲焊车间压缩空气仪表盘分页
    @RequestMapping("/compressedAirPageList_ch")
    @ResponseBody
    public ServerResponse<PageInfo> compressedAirPageList_ch(@RequestParam(value = "pageNum") int pageNum,
                                                             @RequestParam(value = "pageSize") int pageSize,
                                                             @RequestParam(value = "Search_parameters") String Search_parameters,
                                                             @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="ch_data_monitoring_dashboard_CompressedAir";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getCompressedAirPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("冲焊车间压缩空气仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("冲焊车间压缩空气仪表盘分页失败");
        }
    }

    //车架车间压缩空气仪表盘分页
    @RequestMapping("/compressedAirPageList_cj")
    @ResponseBody
    public ServerResponse<PageInfo> compressedAirPageList_cj(@RequestParam(value = "pageNum") int pageNum,
                                                             @RequestParam(value = "pageSize") int pageSize,
                                                             @RequestParam(value = "Search_parameters") String Search_parameters,
                                                             @RequestParam(value = "cabinet_number")List<String> cabinet_number){
        System.out.print(pageNum+',');
        System.out.print(pageSize+',');
        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        String table_name="cj_data_monitoring_dashboard_CompressedAir";
        //获取分页数据
        List<ResultPageData> energyMeterPageList= tzDataMonitoringDashboardService.getCompressedAirPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number,table_name);
        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);
            return  ServerResponse.createBySuccess("车架车间压缩空气仪表盘分页成功",pageResult);
        }else{
            return ServerResponse.createBySuccessMessage("车架车间压缩空气仪表盘分页失败");
        }
    }

    //新增电的仪表盘的方法
    @RequestMapping("/energyMeterAddGauge")
    @ResponseBody
    public ServerResponse  insertGauge(EnergyMeterPage energyMeterPage) {
        System.out.println("进入新增方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createByErrorMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard";
        }else{
            return  ServerResponse.createByErrorMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.insertGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("添加成功");
            return  ServerResponse.createBySuccess("添加成功");
        }
        System.out.println("添加失败");
        return  ServerResponse.createBySuccessMessage("添加失败");
    }

    //新增水的仪表盘的方法
    @RequestMapping("/energyMeterAddGauge_water")
    @ResponseBody
    public ServerResponse  insertGauge_water(EnergyMeterPage energyMeterPage) {
        System.out.println("进入新增方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createByErrorMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard_water";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard_water";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard_water";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard_water";
        }else{
            return  ServerResponse.createBySuccessMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.insertGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("添加成功");
            return  ServerResponse.createBySuccess("添加成功");
        }
        System.out.println("添加失败");
        return  ServerResponse.createBySuccessMessage("添加失败");
    }

    //新增压缩空气的仪表盘的方法
    @RequestMapping("/energyMeterAddGauge_CompressedAir")
    @ResponseBody
    public ServerResponse  insertGauge_CompressedAir(EnergyMeterPage energyMeterPage) {
        System.out.println("进入新增方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createBySuccessMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard_CompressedAir";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard_CompressedAir";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard_CompressedAir";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard_CompressedAir";
        }else{
            return  ServerResponse.createBySuccessMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.insertGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("添加成功");
            return  ServerResponse.createBySuccess("添加成功");
        }
        System.out.println("添加失败");
        return  ServerResponse.createBySuccessMessage("添加失败");
    }

    //新增天然气的仪表盘的方法
    @RequestMapping("/energyMeterAddGauge_NaturalGas")
    @ResponseBody
    public ServerResponse  insertGauge_NaturalGas(EnergyMeterPage energyMeterPage) {
        System.out.println("进入新增方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createBySuccessMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard_NaturalGas";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard_NaturalGas";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard_NaturalGas";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard_NaturalGas";
        }else{
            return  ServerResponse.createBySuccessMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.insertGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("添加成功");
            return  ServerResponse.createBySuccess("添加成功");
        }
        System.out.println("添加失败");
        return  ServerResponse.createBySuccessMessage("添加失败");
    }

    //修改电的仪表盘的方法
    @RequestMapping("/energyMeterUpdataGauge")
    @ResponseBody
    public ServerResponse  updateByPrimaryGauge (EnergyMeterPage energyMeterPage){
        System.out.println("进入修改方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createBySuccessMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard";
        }else{
            return  ServerResponse.createBySuccessMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.updateByPrimaryGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("修改成功");
            return  ServerResponse.createBySuccess("修改成功");
        }
        System.out.println("修改失败");
        return  ServerResponse.createBySuccessMessage("修改失败");
    }

    //修改水的仪表盘的方法
    @RequestMapping("/energyMeterUpdataGauge_water")
    @ResponseBody
    public ServerResponse  updateByPrimaryGauge_water (EnergyMeterPage energyMeterPage){
        System.out.println("进入修改方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createBySuccessMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard_water";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard_water";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard_water";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard_water";
        }else{
            return  ServerResponse.createBySuccessMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.updateByPrimaryGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("修改成功");
            return  ServerResponse.createBySuccess("修改成功");
        }
        System.out.println("修改失败");
        return  ServerResponse.createBySuccessMessage("修改失败");
    }

    //修改压缩空气的仪表盘的方法
    @RequestMapping("/energyMeterUpdataGauge_CompressedAir")
    @ResponseBody
    public ServerResponse  updateByPrimaryGauge_CompressedAir (EnergyMeterPage energyMeterPage){
        System.out.println("进入修改方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createBySuccessMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard_CompressedAir";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard_CompressedAir";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard_CompressedAir";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard_CompressedAir";
        }else{
            return  ServerResponse.createBySuccessMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.updateByPrimaryGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("修改成功");
            return  ServerResponse.createBySuccess("修改成功");
        }
        System.out.println("修改失败");
        return  ServerResponse.createBySuccessMessage("修改失败");
    }

    //修改天然气的仪表盘的方法
    @RequestMapping("/energyMeterUpdataGauge_NaturalGas")
    @ResponseBody
    public ServerResponse  updateByPrimaryGauge_NaturalGas (EnergyMeterPage energyMeterPage){
        System.out.println("进入修改方法");
        String table_name="";
        if(StringUtils.isEmpty(energyMeterPage.getDash_tagname())){
            return  ServerResponse.createBySuccessMessage("请选择数据源");
        }
        if(energyMeterPage.getDash_tagname().contains("TZ")){
            table_name="tz_data_monitoring_dashboard_NaturalGas";
        }else if(energyMeterPage.getDash_tagname().contains("ZZ")){
            table_name="zz_data_monitoring_dashboard_NaturalGas";
        }else if(energyMeterPage.getDash_tagname().contains("CH")){
            table_name="ch_data_monitoring_dashboard_NaturalGas";
        }else if(energyMeterPage.getDash_tagname().contains("CJ")){
            table_name="cj_data_monitoring_dashboard_NaturalGas";
        }else{
            return  ServerResponse.createBySuccessMessage("输入的数据源有误");
        }
        int result1 =tzDataMonitoringDashboardService.updateByPrimaryGauge(energyMeterPage,table_name);
        if(result1==1) {
            System.out.println("修改成功");
            return  ServerResponse.createBySuccess("修改成功");
        }
        System.out.println("修改失败");
        return  ServerResponse.createBySuccessMessage("修改失败");
    }

    //涂装电表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge")
    @ResponseBody
    public ServerResponse  deleteGuage (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="tz_data_monitoring_dashboard";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //涂装水表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_water_tz")
    @ResponseBody
    public ServerResponse  deleteGuage_water_tz (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="tz_data_monitoring_dashboard_water";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //涂装天然气仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_NaturalGas_tz")
    @ResponseBody
    public ServerResponse  deleteGuage_NaturalGas_tz (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="tz_data_monitoring_dashboard_NaturalGas";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //涂装压缩空气仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_CompressedAir_tz")
    @ResponseBody
    public ServerResponse  deleteGuage_CompressedAir_tz (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="tz_data_monitoring_dashboard_CompressedAir";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //总装电表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_zz")
    @ResponseBody
    public ServerResponse  deleteGuage_zz (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="zz_data_monitoring_dashboard";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //总装压缩空气仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_CompressedAir_zz")
    @ResponseBody
    public ServerResponse  deleteGuage_CompressedAir_zz (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="zz_data_monitoring_dashboard_CompressedAir";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //冲焊电表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_ch")
    @ResponseBody
    public ServerResponse  deleteGuage_ch (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="ch_data_monitoring_dashboard";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //冲焊水表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_water_ch")
    @ResponseBody
    public ServerResponse  deleteGuage_water_ch (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="ch_data_monitoring_dashboard_water";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //冲焊压缩空气表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_CompressedAir_ch")
    @ResponseBody
    public ServerResponse  deleteGuage_CompressedAir_ch (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="ch_data_monitoring_dashboard_CompressedAir";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //车架电表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_cj")
    @ResponseBody
    public ServerResponse  deleteGuage_cj (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="cj_data_monitoring_dashboard";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //车架水表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_water_cj")
    @ResponseBody
    public ServerResponse  deleteGuage_water_cj (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="cj_data_monitoring_dashboard_water";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //车架天然气表仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_NaturalGas_cj")
    @ResponseBody
    public ServerResponse  deleteGuage_NaturalGas_cj (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="cj_data_monitoring_dashboard_NaturalGas";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    //车架压缩空气仪表盘的删除方法
    @RequestMapping("/energyMeterDeleteGauge_CompressedAir_cj")
    @ResponseBody
    public ServerResponse  deleteGuage_CompressedAir_cj (Long tz_id) {
        System.out.println("进入删除方法");
        String table_name="cj_data_monitoring_dashboard_CompressedAir";
        int result1 =tzDataMonitoringDashboardService.deleteGuage(tz_id,table_name);
        if(result1==1) {
            System.out.println("删除成功");
            return  ServerResponse.createBySuccess("删除成功");
        }
        System.out.println("删除失败");
        return  ServerResponse.createBySuccessMessage("删除失败");
    }

    @RequestMapping("/energyMeterFindTagName")
    @ResponseBody
    public ServerResponse<List<String>> findTagname(){
        List<String> tag =  tzDataMonitoringDashboardService.findTagname();
        return  ServerResponse.createBySuccess("返回所有tag成功",tag);
    }

    //返回所有电表中文描述成功
    @RequestMapping("/energyMeterFindDescribe")
    @ResponseBody
    public ServerResponse< List<ResourceMeter>> findDescribe(@RequestParam(value = "tagname") String tagname ){
        List<ResourceMeter> Describe =  tzDataMonitoringDashboardService.findDescribe( tagname);
        if(Describe.size()>0){
            return  ServerResponse.createBySuccess("返回所有电表中文描述成功",Describe);
        }
        return  ServerResponse.createBySuccessMessage("返回所有电表中文描述失败");
    }

    //返回所有水表中文描述成功
    @RequestMapping("/energyMeterFindDescribe_water")
    @ResponseBody
    public ServerResponse< List<ResourceMeter>> findDescribe_water(@RequestParam(value = "tagname") String tagname ){
        List<ResourceMeter> Describe =  tzDataMonitoringDashboardService.findDescribe_water( tagname);
        if(Describe.size()>0){
            return  ServerResponse.createBySuccess("返回所有水表中文描述成功",Describe);
        }
        return  ServerResponse.createBySuccessMessage("返回所有水表中文描述失败");
    }

    //返回所有压缩空气表中文描述成功
    @RequestMapping("/energyMeterFindDescribe_CompressedAir")
    @ResponseBody
    public ServerResponse< List<ResourceMeter>> findDescribe_CompressedAir(@RequestParam(value = "tagname") String tagname ){
        List<ResourceMeter> Describe =  tzDataMonitoringDashboardService.findDescribe_CompressedAir( tagname);
        if(Describe.size()>0){
            return  ServerResponse.createBySuccess("返回所有压缩空气表中文描述成功",Describe);
        }
        return  ServerResponse.createBySuccessMessage("返回所有压缩空气表中文描述失败");
    }

    //返回所有天然气表中文描述成功
    @RequestMapping("/energyMeterFindDescribe_NaturalGas")
    @ResponseBody
    public ServerResponse< List<ResourceMeter>> findDescribe_NaturalGas(@RequestParam(value = "tagname") String tagname ){
        List<ResourceMeter> Describe =  tzDataMonitoringDashboardService.findDescribe_NaturalGas( tagname);
        if(Describe.size()>0){
            return  ServerResponse.createBySuccess("返回所有天然气表中文描述成功",Describe);
        }
        return  ServerResponse.createBySuccessMessage("返回所有天然气表中文描述失败");
    }
}


