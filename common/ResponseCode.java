package com.byd.emg.common;

public enum ResponseCode {
    OK("OK","OK"),
    SUCCESS("SUCCESS","SUCCESS"),
    ERROR("ERROR","ERROR"),
    NEED_LOGIN("NEED_LOGIN","NEED_LOGIN"),
    NEED_ACCESS("NEED_ACCESS","NEED_ACCESS"),
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT","ILLEGAL_ARGUMENT");

    private final String code;




    private final String desc;
    public  static String user="user";


    ResponseCode(String code, String desc){
        this.code = code;
        this.desc = desc;
    }


    public String getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

    public static void main(String[] args) {
        String str=ResponseCode.SUCCESS.getDesc();

    }
}
