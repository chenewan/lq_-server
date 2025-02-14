package com.byd.emg.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.mapper.HistoryRecordMapper;
import com.byd.emg.mapper.TestDataMapper;
import com.byd.emg.mapper.TimeJobMapper;
import com.byd.emg.pojo.CycleValue;
import com.byd.emg.pojo.Sampling_period_5min;
import com.byd.emg.resultentity.ContrastData;
import com.byd.emg.resultentity.HistoryParameter;
import com.byd.emg.service.HistoryRecordService;
import com.byd.emg.service.MenuTableService;
import com.byd.emg.service.TimeJobService;
import com.byd.emg.service.TzDataMonitoringService;
import com.byd.emg.util.DateTimeUtil;
import com.byd.emg.util.LogUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.MONTH;

@RestController
@RequestMapping("data")
public class DataController {

    @Autowired
    private HistoryRecordMapper historyRecordMapper;

    @Autowired
    private TestDataMapper testDataMapper;

    @Autowired
    private TimeJobService timeJobService;

    @Autowired
    private TimeJobMapper timeJobMapper;

        @Autowired
        private com.byd.emg.service.TzDataMonitoringService TzDataMonitoringService;

        @Autowired
        private MenuTableService menuTableService;


        //补5分钟的耗值
        @RequestMapping("/testData")
        @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
        @ResponseBody
        public ServerResponse testHomepage() {
            try {
   /*             String realTable = "tz_real_time_power";
                String consumeTable = "tz_5min_consume_power";
                String startDate = "2020-01-01";
                String entDate = "2020-01-09";
                //List<String> canumberList = testDataMapper.allTagList(realTable);
                List<String> canumberList =new ArrayList<>();canumberList.add();
                testDataMapper.delete(consumeTable, startDate, entDate);
                List<Sampling_period_5min> dataList = this.getHistoryRecordByTagname(canumberList, "_EP", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电");
                dataList.addAll(this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电"));
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "ch_real_time_power";
                consumeTable = "ch_5min_consume_power";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EP", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电");
                dataList.addAll(this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电"));
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "cj_real_time_power";
                consumeTable = "cj_5min_consume_power";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EP", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电");
                dataList.addAll(this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电"));
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "zz_real_time_power";
                consumeTable = "zz_5min_consume_power";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EP", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电");
                dataList.addAll(this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电"));
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "BDZ_real_time_power";
                consumeTable = "BDZ_5min_consume_power";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EP", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电");
                dataList.addAll(this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "电"));
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();*/

                String realTable = "tz_real_time_water";
                String consumeTable = "tz_5min_consume_water";
                String startDate = "2020-01-01";
                String entDate = "2020-01-11";
                List<String> canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                List<Sampling_period_5min> dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "水");
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "tz_real_time_NaturalGas";
                consumeTable = "tz_5min_consume_NaturalGas";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "天然气");
                dataList.addAll(this.getHistoryRecordByTagname(canumberList, "_REQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "天然气"));
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "tz_real_time_CompressedAir";
                consumeTable = "tz_5min_consume_CompressedAir";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "压缩空气");
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "ch_real_time_water";
                consumeTable = "ch_5min_consume_water";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "水");
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "ch_real_time_CompressedAir";
                consumeTable = "ch_5min_consume_CompressedAir";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "压缩空气");
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "cj_real_time_water";
                consumeTable = "cj_5min_consume_water";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "水");
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "cj_real_time_CompressedAir";
                consumeTable = "cj_5min_consume_CompressedAir";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "压缩空气");
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "cj_real_time_NaturalGas";
                consumeTable = "cj_5min_consume_NaturalGas";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "天然气");
                dataList.addAll(this.getHistoryRecordByTagname(canumberList, "_REQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "天然气"));
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                realTable = "zz_real_time_CompressedAir";
                consumeTable = "zz_5min_consume_CompressedAir";
                canumberList = testDataMapper.allTagList(realTable);
                testDataMapper.delete(consumeTable, startDate, entDate);
                dataList = this.getHistoryRecordByTagname(canumberList, "_EQ", "00:00:00", "23:59:59", "2020-01-01", "2020-01-09", "5分钟", "消耗值", "压缩空气");
                timeJobMapper.batchInsertConsumeData(dataList, consumeTable);
                dataList.clear();
                canumberList.clear();

                //小时
                this.TimeJobByHourConsume();

                //天
                this.TimeJobByDayConsume();

                return ServerResponse.createBySuccess("查询成功");
            }catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createBySuccessMessage("查询失败");
            }

        }

        //根据tagname和日期来查询当天的历史记录
        public List<Sampling_period_5min> getHistoryRecordByTagname(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle, String showValue, String changing) {
            String table_name="";
            String table_prefix_name="";
            String time_value="time_value";
            if(changing.equals("电")){
                table_prefix_name="_power";
                time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
            }else if(changing.equals("水")){
                table_prefix_name="_water";
                time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
            }else if(changing.equals("压缩空气")){
                table_prefix_name="_CompressedAir";
                time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
            }else if(changing.equals("天然气")){
                table_prefix_name="_NaturalGas";
                time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
            }
            List<String[]> cn = new ArrayList<String[]>();
            List<HistoryParameter> historyParameterList=new ArrayList<HistoryParameter>();
            int cnSize=cabinet_number.size();

            if(StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)) return null;
             if (showValue.equals("消耗值")) {
                 if (cycle.equals("5分钟")) {
                     String cn1 = null;
                     String month_number = DateTimeUtil.strToStr(startDate.trim(), "yyyy-MM-dd", "MM");
                     String endMonth_number = DateTimeUtil.strToStr(endDate.trim(), "yyyy-MM-dd", "MM");
                     List<CycleValue> cycleValueList = this.getDateDayList(startTime, endTime, startDate, endDate, cycle);
                     for (String number : cabinet_number) {
                         if (!StringUtils.isEmpty(number)) {
                             if (number.contains("TZ")) {
                                 //table_name="tz_5min"+table_prefix_name;
                                 //table_name="tz_5min_power_month_test";
                                 table_name = "tz" + table_prefix_name + "_" + "5min_" + month_number;
                             } else if (number.contains("ZZ")) {
                                 if (table_prefix_name.contains("_water") || table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "zz" + table_prefix_name + "_" + "5min_" + month_number;
                             } else if (number.contains("CH")) {
                                 if (table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "ch" + table_prefix_name + "_" + "5min_" + month_number;

                             } else if (number.contains("CJ")) {
                                 table_name = "cj" + table_prefix_name + "_" + "5min_" + month_number;
                             }else if (number.contains("BDZ")) {
                                 table_name = "BDZ" + table_prefix_name + "_" + "5min_" + month_number;
                             }
                         } else {
                             continue;
                         }
                         HistoryParameter historyParameter = new HistoryParameter();
                         historyParameter.setCa_number(number);
                         historyParameter.setTable_name(table_name);
                         historyParameterList.add(historyParameter);
                         //跨年处理
                         if (!startDate.substring(0, 4).equals(endDate.substring(0, 4))) {
                             HistoryParameter historyParameterYear = new HistoryParameter();
                             historyParameterYear.setCa_number(number);
                             historyParameterYear.setTable_name(table_name + endMonth_number);
                             historyParameterList.add(historyParameterYear);
                         }
                     }
                     Map<String, List<Sampling_period_5min>> minMap = new HashMap<>();
                     if (historyParameterList.size() > 0) {
                         minMap = this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate, type, time_value, historyParameterList);
                     }
                     return this.getTimevalueToDate(cycleValueList, cabinet_number, cycle, showValue, minMap);
                 } else if (cycle.equals("一小时")) {
                     String cn1 = null;
                     String year_number = DateTimeUtil.strToStr(startDate.trim(), "yyyy-MM-dd", "yyyy");
                     String table_number = Integer.parseInt(year_number) % 20 + 1 + "";
                     String endYear_number = DateTimeUtil.strToStr(endDate.trim(), "yyyy-MM-dd", "yyyy");
                     String endTable_number = Integer.parseInt(endYear_number) % 20 + 1 + "";
                     List<CycleValue> cycleValueList = this.getDateDayList(startTime, endTime, startDate, endDate, cycle);
                     for (String number : cabinet_number) {
                         if (!StringUtils.isEmpty(number)) {
                             if (number.contains("TZ")) {
                                 table_name = "tz" + table_prefix_name + "_" + "hour_" + table_number;
                             } else if (number.contains("ZZ")) {
                                 if (table_prefix_name.contains("_water") || table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "zz" + table_prefix_name + "_" + "hour_" + table_number;

                             } else if (number.contains("CH")) {
                                 if (table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "ch" + table_prefix_name + "_" + "hour_" + table_number;

                             } else if (number.contains("CJ")) {
                                 table_name = "cj" + table_prefix_name + "_" + "hour_" + table_number;

                             }
                         } else {
                             continue;
                         }
                         HistoryParameter historyParameter = new HistoryParameter();
                         historyParameter.setCa_number(number);
                         historyParameter.setTable_name(table_name);
                         historyParameterList.add(historyParameter);
                         //跨年处理
                         if (!startDate.substring(0, 4).equals(endDate.substring(0, 4))) {
                             HistoryParameter historyParameterYear = new HistoryParameter();
                             historyParameterYear.setCa_number(number);
                             historyParameterYear.setTable_name(table_name + endTable_number);
                             historyParameterList.add(historyParameterYear);
                         }
                     }
                     Map<String, List<Sampling_period_5min>> minMap = new HashMap<>();
                     if (historyParameterList.size() > 0) {
                         minMap = this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate, type, time_value, historyParameterList);
                     }
                     return this.getTimevalueToDate(cycleValueList, cabinet_number, cycle, showValue, minMap);
                 } else if (cycle.equals("一天")) {
                     String year_number = DateTimeUtil.strToStr(startDate.trim(), "yyyy-MM-dd", "yyyy");
                     String table_number = Integer.parseInt(year_number) % 20 + 1 + "";
                     String endYear_number = DateTimeUtil.strToStr(endDate.trim(), "yyyy-MM-dd", "yyyy");
                     String endTable_number = Integer.parseInt(endYear_number) % 20 + 1 + "";
                     List<CycleValue> cycleValueList = this.getDateDayList(startTime, endTime, startDate, endDate, cycle);
                     String cn1 = null;
                     for (String number : cabinet_number) {
                         if (!StringUtils.isEmpty(number)) {
                             if (number.contains("TZ")) {
                                 table_name = "tz" + table_prefix_name + "_" + "day_" + table_number;
                             } else if (number.contains("ZZ")) {
                                 if (table_prefix_name.contains("_water") || table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "zz" + table_prefix_name + "_" + "day_" + table_number;
                             } else if (number.contains("CH")) {
                                 if (table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "ch" + table_prefix_name + "_" + "day_" + table_number;

                             } else if (number.contains("CJ")) {
                                 table_name = "cj" + table_prefix_name + "_" + "day_" + table_number;

                             }
                         } else {
                             continue;
                         }
                         HistoryParameter historyParameter = new HistoryParameter();
                         historyParameter.setCa_number(number);
                         historyParameter.setTable_name(table_name);
                         historyParameterList.add(historyParameter);
                         //跨年处理
                         if (!startDate.substring(0, 4).equals(endDate.substring(0, 4))) {
                             HistoryParameter historyParameterYear = new HistoryParameter();
                             historyParameterYear.setCa_number(number);
                             historyParameterYear.setTable_name(table_name + endTable_number);
                             historyParameterList.add(historyParameterYear);
                         }
                     }
                     Map<String, List<Sampling_period_5min>> minMap = new HashMap<>();
                     if (historyParameterList.size() > 0) {
                         minMap = this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate, type, time_value, historyParameterList);
                     }
                     return this.getTimevalueToDate(cycleValueList, cabinet_number, cycle, showValue, minMap);
                 } else if (cycle.equals("月")) {
                     String cn1 = null;
                     endDate = this.addMonth(startDate);
                     List<CycleValue> cycleValueList = this.getDateDayList(startTime, endTime, startDate, endDate, cycle);
                     for (String number : cabinet_number) {
                         if (!StringUtils.isEmpty(number)) {
                             if (number.contains("TZ")) {
                                 table_name = "tz" + table_prefix_name + "_month";
                             } else if (number.contains("ZZ")) {
                                 if (table_prefix_name.contains("_water") || table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "zz" + table_prefix_name + "_month";

                             } else if (number.contains("CH")) {
                                 if (table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "ch" + table_prefix_name + "_month";

                             } else if (number.contains("CJ")) {
                                 table_name = "cj" + table_prefix_name + "_month";

                             }
                         } else {
                             continue;
                         }
                         HistoryParameter historyParameter = new HistoryParameter();
                         historyParameter.setCa_number(number);
                         historyParameter.setTable_name(table_name);
                         historyParameterList.add(historyParameter);
                     }
                     Map<String, List<Sampling_period_5min>> minMap = new HashMap<>();
                     if (historyParameterList.size() > 0) {
                         minMap = this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate, type, time_value, historyParameterList);
                     }
                     return this.getTimevalueToDate(cycleValueList, cabinet_number, cycle, showValue, minMap);
                 } else if (cycle.equals("年")) {
                     String cn1 = null;
                     List<CycleValue> cycleValueList = this.getDateDayList(startTime, endTime, startDate, endDate, cycle);
                     for (String number : cabinet_number) {
                         if (!StringUtils.isEmpty(number)) {
                             if (number.contains("TZ")) {
                                 table_name = "tz" + table_prefix_name + "_year";
                             } else if (number.contains("ZZ")) {
                                 if (table_prefix_name.contains("_water") || table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "zz" + table_prefix_name + "_year";

                             } else if (number.contains("CH")) {
                                 if (table_prefix_name.contains("_NaturalGas")) {
                                     continue;
                                 }
                                 table_name = "ch" + table_prefix_name + "_year";

                             } else if (number.contains("CJ")) {
                                 table_name = "cj" + table_prefix_name + "_year";

                             }
                         } else {
                             continue;
                         }
                         HistoryParameter historyParameter = new HistoryParameter();
                         historyParameter.setCa_number(number);
                         historyParameter.setTable_name(table_name);
                         historyParameterList.add(historyParameter);
                     }
                     Map<String, List<Sampling_period_5min>> minMap = new HashMap<>();
                     if (historyParameterList.size() > 0) {
                         minMap = this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate, type, time_value, historyParameterList);
                     }
                     return this.getTimevalueToDate(cycleValueList, cabinet_number, cycle, showValue, minMap);
                 }

             }
            return null;
        }

        private Map<String, List<Sampling_period_5min>> getHistoryRecordByTagnameMap(String startTime, String endTime, String startDate, String endDate, String type, String time_value, List<HistoryParameter> historyParameterList) {
            Map<String, List<Sampling_period_5min>> resultMap=new HashMap<>();
            List<Sampling_period_5min> minList=new ArrayList<Sampling_period_5min>();
            if(historyParameterList.get(0).getTable_name().contains("BDZ")){
                minList=historyRecordMapper.getBdzRecordByTagnameList(startTime,endTime,startDate,endDate,type,time_value,historyParameterList);
            }else{
                minList=historyRecordMapper.getHistoryRecordByTagnameList(startTime,endTime,startDate,endDate,type,time_value,historyParameterList);
            }
            for(HistoryParameter history:historyParameterList){
                if(StringUtils.isEmpty(history.getCa_number())) continue;
                List<Sampling_period_5min> minList_2=new ArrayList<>();
                for(int index=0;index<minList.size();index++){
                    Sampling_period_5min minDb=minList.get(index);
                    if(history.getCa_number().equals(minDb.getCabinet_number())){
                        minList_2.add(minDb);
                        minList.remove(index);
                        index--;
                    }
                }
                resultMap.put(history.getCa_number(),minList_2);
            }
            return resultMap;
        }

        //将历史值和时间映射上
        public List<Sampling_period_5min> getTimevalueToDate(List<CycleValue> cycleValueList,List<String> cabinet_numberList,String cycle,String showValue,Map<String,List<Sampling_period_5min>> minMap) {
            List<Sampling_period_5min> resultList=new ArrayList<Sampling_period_5min>();
            Map<String,List<String[]>> resultMap=new HashMap<>();
            String start_time=null;
            String end_time=null;
            int index=0;
            int length=cabinet_numberList.size();
            for(CycleValue cycleValue:cycleValueList){
                if(cycle.equals("5分钟")){
                    start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","HH时mm分");
                    end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","HH时mm分");
                }else if(cycle.equals("一小时")){
                    start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日HH时");
                    end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日HH时");
                }else if(cycle.equals("一天")){
                    start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日");
                    end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日");
                }else if(cycle.equals("月")){
                    start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","yy年MM月");
                    end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","yy年MM月");
                }else if(cycle.equals("年")){
                    start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","yyyy年");
                    end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","yyyy年");
                }
                index=0;
                String data[]=new String[length+1];
                if(showValue.equals("消耗值")){
                    data[index]=start_time+"—"+end_time;
                    resultList.addAll(returnShowValue(index,cabinet_numberList,minMap,cycleValue,data,showValue));
                }
            }
            return resultList;
        }

        //返回表码值的方法
        public List<Sampling_period_5min> returnShowValue(int index,List<String> cabinet_numberList,Map<String,List<Sampling_period_5min>> minMap,CycleValue cycleValue,String[] data,String showValue){
            List<Sampling_period_5min> resultList=new ArrayList<Sampling_period_5min>();
            boolean flag=true;
            //历史值的最小时间、最大时间
            String min_date="";
            String max_date="";
            BigDecimal aa;
            BigDecimal bb;
            index++;
            int next_index=0;
            for(String cn1:cabinet_numberList){
                List<Sampling_period_5min> minList=minMap.get(cn1);
                if(minList==null) minList=new ArrayList<Sampling_period_5min>();
                //按照时间升序排序
                Collections.sort(minList, new Comparator<Sampling_period_5min>() {
                    @Override
                    public int compare(Sampling_period_5min o1, Sampling_period_5min o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });
                if(minList.size()>0){
                    min_date=minList.get(0).getTime();
                    int num=minList.size()-1;
                    max_date=minList.get(num).getTime();
                }
                flag=true;
                if(!StringUtils.isEmpty(cn1)&&this.compareToDate_2(min_date, max_date, cycleValue)==1) {
                    for (int j = 0; j < minList.size(); j++) {
                        Sampling_period_5min sampling_period_5min = minList.get(j);
                        String queryDate = sampling_period_5min.getTime();
                        if (this.compareToDate(cycleValue, queryDate)) {
                            if (showValue.equals("表码值")) {
                                data[index] = sampling_period_5min.getTime_value();
                                index++;
                            } else if (showValue.equals("消耗值")) {
                                next_index = j + 1;
                                if (next_index >= minList.size()) break;
                                String value_1 = sampling_period_5min.getTime_value();
                                if (StringUtils.isEmpty(value_1)) {
                                    data[index] = "";
                                    index++;
                                    minList.remove(j);
                                    j--;
                                    flag = false;
                                    break;
                                }
                                while (value_1.contains(",")) {
                                    int index_1 = value_1.indexOf(",");
                                    value_1 = value_1.substring(0, index_1) + value_1.substring(index_1 + 1);
                                }
                                aa = new BigDecimal(value_1);

                                String value_2 = minList.get(next_index).getTime_value();
                                if (StringUtils.isEmpty(value_2)) {
                                    data[index] = "";
                                    index++;
                                    minList.remove(j);
                                    j--;
                                    flag = false;
                                    break;
                                }
                                while (value_2.contains(",")) {
                                    int index_2 = value_2.indexOf(",");
                                    value_2 = value_2.substring(0, index_2) + value_2.substring(index_2 + 1);
                                }
                                bb = new BigDecimal(value_2);
                                bb=bb.subtract(aa);
                                if(bb.compareTo(BigDecimal.ZERO)>0){
                                    minList.get(next_index).setTime_value(bb.toString());
                                    resultList.add(minList.get(next_index));
                                }

                                index++;
                            }
                            minList.remove(j);
                            j--;
                            flag = false;
                            break;
                        }
                    }
                }
                if(flag) {
                    data[index]="";
                    index++;
                }
            }
            return resultList;
        }

        //判断数据库查询出来的时间是否在周期时间段内
        public boolean compareToDate(CycleValue cycleValue, String queryDate){
            Date date_Db=null;
            Date startDate=null;
            Date endDate=null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date_Db = sdf.parse(queryDate);
                startDate = sdf.parse(cycleValue.getStartTime());
                endDate=sdf.parse(cycleValue.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date_Db.compareTo(startDate)>=0&&date_Db.compareTo(endDate)<0){
                return true;
            }else{
                return false;
            }
        }

        /*状态码："1"表示历史值的最小时间小于周期时间段的结束时间，并且历史值的最大时间大于周期时间段的起始时间(可以开始循环)，
         *         "0"表示历史值的最小时间大于等于周期时间段的结束时间(循环尚未开始，这部分需要补空字符串)
         *         "-1"表示周期时间段已超过历史值的最大时间(这部分可以不需要补空字符串，减少循环次数)
         */
        public int compareToDate_2(String min_date, String max_date, CycleValue cycleValue) {
            if(StringUtils.isEmpty(min_date)||StringUtils.isEmpty(max_date)) return -10;
            Date startDate_db=null;
            Date endDate_db=null;
            Date startDate=null;
            Date endDate=null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                startDate_db = sdf.parse(min_date);
                endDate_db = sdf.parse(max_date);
                startDate = sdf.parse(cycleValue.getStartTime());
                endDate=sdf.parse(cycleValue.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(startDate_db.compareTo(endDate)<0&&endDate_db.compareTo(startDate)>=0){
                return 1;
            }else if(endDate_db.compareTo(startDate)<0){
                return -1;
            }else{
                return 0;
            }
        }

        //将传入的日期加上一个月
        public String addMonth(String dateDb){
            Date dateParameter=null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateParameter = sdf.parse(dateDb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dateParameter);
            rightNow.add(MONTH,1);
            return sdf.format(rightNow.getTime());
        }

        //获取周期的时间段的集合
        public List<CycleValue> getDateDayList(String startTime, String endTime, String startDate, String endDate,String cycle) {
            List<CycleValue> cycleValueList=new ArrayList<CycleValue>();
            String beginTime=startDate.trim()+" "+startTime.trim();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begin_d1=df.parse(beginTime);
                Date end_d1=df.parse(endDate+" "+endTime);
                String setEndtime="";
                while(begin_d1.before(end_d1)){
                    Calendar rightNow = Calendar.getInstance();
                    rightNow.setTime(begin_d1);
                    if(cycle.equals("5分钟")){
                        rightNow.add(Calendar.MINUTE,5);
                    }else if(cycle.equals("一小时")){
                        rightNow.add(Calendar.HOUR_OF_DAY,1);
                    }else if(cycle.equals("一天")){
                        rightNow.add(Calendar.DAY_OF_MONTH,1);
                    }else if(cycle.equals("月")){
                        rightNow.add(Calendar.MONTH,1);
                    }else if(cycle.equals("年")){
                        rightNow.add(Calendar.YEAR,1);
                    }else{
                        rightNow.add(Calendar.MINUTE,5);
                    }
                    setEndtime=df.format(rightNow.getTime());
                    CycleValue cycleValue=new CycleValue();
                    cycleValue.setStartTime(beginTime);
                    cycleValue.setEndTime(setEndtime);
                    cycleValueList.add(cycleValue);
                    beginTime=setEndtime;
                    begin_d1=df.parse(beginTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return cycleValueList;
        }

        //根据传入的年月减去一个月
        public String subtractMonth(String date,String cycle,int num) {
            SimpleDateFormat df=null;
            int type=2;
            if(cycle.equals("月")) {
                df = new SimpleDateFormat("yyyy-MM");
                type=Calendar.MONTH;
            }else if(cycle.equals("年")){
                df = new SimpleDateFormat("yyyy");
                type=Calendar.YEAR;
            }
            String returnDate=date;
            try {
                Calendar rightNow = Calendar.getInstance();
                Date begin_d1=df.parse(date);
                rightNow.setTime(begin_d1);
                rightNow.add(type,num);
                returnDate=df.format(rightNow.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return returnDate;
        }

        //计算两个时间之间相差的月数
        public int getMonthSpace(String date1, String date2){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            try {
                c1.setTime(sdf.parse(date1));
                c2.setTime(sdf.parse(date2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int result = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            int month = (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12;
            return month + result;
        }

    public void TimeJobByHourConsume(){
        String hh="";
        String dd="";
        String date= "";
        String time= "";
        String queryHour="";
        for(int day=1;day<12;day++){
            if(day<10){dd="0"+day;}else{dd=day+"";}
            for(int hour=0;hour<24;hour++){
                if(hour<10){hh="0"+hour;}else{hh=hour+"";}
                date="2020-01-"+dd;
                time=hh+":00:00";
                queryHour=date+" "+hh;
                Map<String, String> dataMap = timeJobService.selectCurrData(queryHour);
                //存储每小时的耗值(将该小时内所有5min的耗值相加)
                /*String dataSourceTableName="tz_5min_consume_power";
                String historyTableNameConsume="tz_hour_consume_power";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //冲焊电
                dataSourceTableName="ch_5min_consume_power";
                historyTableNameConsume="ch_hour_consume_power";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //车架电
                dataSourceTableName="cj_5min_consume_power";
                historyTableNameConsume="cj_hour_consume_power";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //总装电
                dataSourceTableName="zz_5min_consume_power";
                historyTableNameConsume="zz_hour_consume_power";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //变电站
                dataSourceTableName="BDZ_5min_consume_power";
                historyTableNameConsume="BDZ_hour_consume_power";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);*/

                //涂装天然气
                String  dataSourceTableName="tz_5min_consume_NaturalGas";
                String historyTableNameConsume="tz_hour_consume_NaturalGas";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //涂装水
                dataSourceTableName="tz_5min_consume_water";
                historyTableNameConsume="tz_hour_consume_water";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //涂装压缩空气
                dataSourceTableName="tz_5min_consume_CompressedAir";
                historyTableNameConsume="tz_hour_consume_CompressedAir";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //冲焊水
                dataSourceTableName="ch_5min_consume_water";
                historyTableNameConsume="ch_hour_consume_water";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //冲焊压缩空气
                dataSourceTableName="ch_5min_consume_CompressedAir";
                historyTableNameConsume="ch_hour_consume_CompressedAir";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //车架水
                dataSourceTableName="cj_5min_consume_water";
                historyTableNameConsume="cj_hour_consume_water";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //车架压缩空气
                dataSourceTableName="cj_5min_consume_CompressedAir";
                historyTableNameConsume="cj_hour_consume_CompressedAir";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //车架天然气
                dataSourceTableName="cj_5min_consume_NaturalGas";
                historyTableNameConsume="cj_hour_consume_NaturalGas";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);
                //总装压缩空气
                dataSourceTableName="zz_5min_consume_CompressedAir";
                historyTableNameConsume="zz_hour_consume_CompressedAir";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);

                //油
                /*String dataSourceTableName="oil_5min_consume_power";
                String historyTableNameConsume="oil_hour_consume_power";
                testDataMapper.deleteByDate(historyTableNameConsume, queryHour);
                timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,queryHour,date,time,dataMap);*/
            }
        }
    }


    public void TimeJobByDayConsume() {
        String hh="";
        String dd="";
        String date= "";
        String time= "";
        for(int day=1;day<12;day++){
            if(day<10){dd="0"+day;}else{dd=day+"";}
            date="2020-01-"+dd;
            time="23:59:00";
            //String queryDate=subtractDate(date,"天");
            Map<String, String> dataMap = timeJobService.selectCurrData(date);
            /*String dataSourceTableName="tz_hour_consume_power";
            String historyTableNameConsume="tz_day_consume_power";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //冲焊电
            dataSourceTableName="ch_hour_consume_power";
            historyTableNameConsume="ch_day_consume_power";
            testDataMapper.deleteByDate(historyTableNameConsume,date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
           //车架电
            dataSourceTableName="cj_hour_consume_power";
            historyTableNameConsume="cj_day_consume_power";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
           //总装电
            dataSourceTableName="zz_hour_consume_power";
            historyTableNameConsume="zz_day_consume_power";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //变电站
            dataSourceTableName="BDZ_hour_consume_power";
            historyTableNameConsume="BDZ_day_consume_power";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);*/

            //涂装天然气
            String dataSourceTableName="tz_hour_consume_NaturalGas";
            String historyTableNameConsume="tz_day_consume_NaturalGas";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //涂装水
            dataSourceTableName="tz_hour_consume_water";
            historyTableNameConsume="tz_day_consume_water";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //涂装压缩空气
            dataSourceTableName="tz_hour_consume_CompressedAir";
            historyTableNameConsume="tz_day_consume_CompressedAir";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //冲焊水
            dataSourceTableName="ch_hour_consume_water";
            historyTableNameConsume="ch_day_consume_water";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //冲焊压缩空气
            dataSourceTableName="ch_hour_consume_CompressedAir";
            historyTableNameConsume="ch_day_consume_CompressedAir";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //车架水
            dataSourceTableName="cj_hour_consume_water";
            historyTableNameConsume="cj_day_consume_water";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //车架压缩空气
            dataSourceTableName="cj_hour_consume_CompressedAir";
            historyTableNameConsume="cj_day_consume_CompressedAir";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //车架天然气
            dataSourceTableName="cj_hour_consume_NaturalGas";
            historyTableNameConsume="cj_day_consume_NaturalGas";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);
            //总装压缩空气
            dataSourceTableName="zz_hour_consume_CompressedAir";
            historyTableNameConsume="zz_day_consume_CompressedAir";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);

           /* String dataSourceTableName="oil_hour_consume_power";
            String historyTableNameConsume="oil_day_consume_power";
            testDataMapper.deleteByDate(historyTableNameConsume, date);
            timeJobService.insertConsumeData(dataSourceTableName,historyTableNameConsume,date,date,time,dataMap);*/

        }
     }
}


