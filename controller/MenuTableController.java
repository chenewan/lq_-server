package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.MenuTable;
import com.byd.emg.service.MenuTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menuTable")
public class MenuTableController {

    @Autowired
    private MenuTableService menuTableService;

    @RequestMapping("/menuList")
    public ServerResponse menuList(){
        List<MenuTable> bdz_power=menuTableService.menuList("menu_table_power","BDZ");
        List<MenuTable> tz_power=menuTableService.menuList("menu_table_power","TZ");
        List<MenuTable> tz_comparison_power=menuTableService.menuList("menu_table_power","TZ_TB");
        List<MenuTable> tz_water=menuTableService.menuList("menu_table_water","TZ");
        List<MenuTable> tz_NaturalGas=menuTableService.menuList("menu_table_NaturalGas","TZ");
        List<MenuTable> tz_comparison_NaturalGas=menuTableService.menuList("menu_table_NaturalGas","TZ_TB");
        List<MenuTable> tz_CompressdeAir=menuTableService.menuList("menu_table_CompressdeAir","TZ");
        List<MenuTable> zz_power=menuTableService.menuList("menu_table_power","ZZ");
        List<MenuTable> zz_comparison_power=menuTableService.menuList("menu_table_power","ZZ_TB");
        List<MenuTable> zz_CompressdeAir=menuTableService.menuList("menu_table_CompressdeAir","ZZ");
        List<MenuTable> ch_power=menuTableService.menuList("menu_table_power","CH");
        List<MenuTable> ch_comparison_power=menuTableService.menuList("menu_table_power","CH_TB");
        List<MenuTable> cj_comparison_power=menuTableService.menuList("menu_table_power","CJ_TB");
        List<MenuTable> ch_water=menuTableService.menuList("menu_table_water","CH");
        List<MenuTable> ch_CompressdeAir=menuTableService.menuList("menu_table_CompressdeAir","CH");
        List<MenuTable> cj_power=menuTableService.menuList("menu_table_power","CJ");
        List<MenuTable> cj_water=menuTableService.menuList("menu_table_water","CJ");
        List<MenuTable> cj_NaturalGas=menuTableService.menuList("menu_table_NaturalGas","CJ");
        List<MenuTable> cv_NaturalGas=menuTableService.menuList("menu_table_NaturalGas_cv","CV");
        List<MenuTable> cj_comparison_NaturalGas=menuTableService.menuList("menu_table_NaturalGas","CJ_TB");
        List<MenuTable> cj_CompressdeAir=menuTableService.menuList("menu_table_CompressdeAir","CJ");
        List<MenuTable> equipment_menu=menuTableService.menuList("menu_table_equipment","");
        List<MenuTable> oilMenu=menuTableService.menuList("menu_table_oil","OIL");
        List<MenuTable> reportMenu=menuTableService.reportMenuList("menu_table_report","0");
        List<MenuTable> reportMenuPoint=menuTableService.reportMenuList("menu_table_report","1");
        //区治能耗导航栏
        List<MenuTable> dflzmMenu=menuTableService.menuList("menu_table_DFLZM","");
        //光伏导航栏
        List<MenuTable> gf_power=menuTableService.menuList("menu_table_power","GF");
        Map<String,List<MenuTable>> resultMap=new HashMap<String,List<MenuTable>>();
        resultMap.put("bdz_power",bdz_power);
        resultMap.put("tz_power",tz_power);
        resultMap.put("tz_comparison_power",tz_comparison_power);
        resultMap.put("tz_water",tz_water);
        resultMap.put("tz_NaturalGas",tz_NaturalGas);
        resultMap.put("tz_comparison_NaturalGas",tz_comparison_NaturalGas);
        resultMap.put("tz_CompressdeAir",tz_CompressdeAir);
        resultMap.put("zz_power",zz_power);
        resultMap.put("zz_comparison_power",zz_comparison_power);
        resultMap.put("zz_CompressdeAir",zz_CompressdeAir);
        resultMap.put("ch_power",ch_power);
        resultMap.put("ch_comparison_power",ch_comparison_power);
        resultMap.put("ch_water",ch_water);
        resultMap.put("ch_CompressdeAir",ch_CompressdeAir);
        resultMap.put("cj_power",cj_power);
        resultMap.put("cj_comparison_power",cj_comparison_power);
        resultMap.put("cj_water",cj_water);
        resultMap.put("cj_NaturalGas",cj_NaturalGas);
        resultMap.put("cv_NaturalGas",cv_NaturalGas);
        resultMap.put("cj_comparison_NaturalGas",cj_comparison_NaturalGas);
        resultMap.put("cj_CompressdeAir",cj_CompressdeAir);
        resultMap.put("equipment_menu",equipment_menu);
        resultMap.put("oilMenu",oilMenu);
        resultMap.put("report",reportMenu);
        resultMap.put("reportPoint",reportMenuPoint);
        resultMap.put("dflzmMenu",dflzmMenu);
        resultMap.put("gf_power",gf_power);
        if(tz_power.size()>0||tz_water.size()>0||tz_NaturalGas.size()>0||tz_CompressdeAir.size()>0||zz_power.size()>0||zz_CompressdeAir.size()>0||
                ch_power.size()>0||ch_water.size()>0||ch_CompressdeAir.size()>0||cj_power.size()>0||cj_water.size()>0||cj_NaturalGas.size()>0||
                cj_CompressdeAir.size()>0){
            return  ServerResponse.createBySuccess("返回菜单列表成功",resultMap);
        }
        return  ServerResponse.createBySuccessMessage("返回菜单列表失败");
    }

}
