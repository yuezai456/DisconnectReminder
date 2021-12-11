package com.ezil.reminder.common;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class PostMessage {

    public static String sendRemind(String appID,String secret,String username,String TemplateId,String machineID) {
        String token=getHttp(appID,secret);
        System.out.println(token);
        WechatValue wechatValue=new WechatValue();
        wechatValue.setTouser(username);
        wechatValue.setTemplate_id(TemplateId);
        first newFirst=new first();
        newFirst.setValue(machineID);
        newFirst.setColor("#60FF00");
        data newData=new data();
        newData.setFirst(newFirst);
        wechatValue.setData(newData);
        String json= JSON.toJSONString(wechatValue);
        return PostHttp(token, json);

    }
    public static String getHttp(String appID,String secret){
//        RestTemplate restTemplate = new RestTemplate();
//        Notice forObject = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={1}&secret={2}", Notice.class, appID, secret);
//        Notice body = forObject;
//        return body.getAccess_token();
        return null;
    }
//    public static String getUser(String token){
//        Map Temp=new HashMap();
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/user/get?access_token="+token+"&next_openid=",Notice.class).getBody().toString();
//
//
//    }
    public static String PostHttp(String token,String json){
        RestTemplate restTemplate = new RestTemplate();
        String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request=new HttpEntity<String>(json,headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        return response.getBody();
    }
}
