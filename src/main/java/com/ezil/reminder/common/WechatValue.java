package com.ezil.reminder.common;

import lombok.Data;

@Data
public class WechatValue {
    private String touser;
   // private String appid;
    private String template_id;
    private String url;
    private data data;
    //private String first;

}

@Data
class data {
    first first;



}

@Data
class first{
    private String value;
    private String color;
}