package com.ezil.reminder.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezil.reminder.mapper.EzilWorkersMapper;
import com.ezil.reminder.model.EzilWorkers;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Check {

    private List<EzilWorkers> ezilWorkers;

    public  List<EzilWorkers>  findMachine(){

        String result=getHttpS("0x91d027eb528d6cceb4bc2b527dd0f5aa5ba7973b.zil1v2k64uhqenskfkuhj6x6e6nz9mqm02kwxayfpg");
        System.out.println(result);
        JSONArray array = (JSONArray) JSONObject.parse(result);

        for (int i = 0; i < array.size(); i++) {
            JSONObject jo = array.getJSONObject(i);
            ezilWorkers= (List) JSON.parseObject(jo.toString(),List.class);
        }
        return ezilWorkers;
    }

    public static String getHttpS(String wallet){

        String result=null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);
            String url="https://stats.ezil.me/current_stats/wallet/workers".replace("wallet",wallet);
            System.out.println(url);
            ResponseEntity<String> forObject = restTemplate.exchange(url, HttpMethod.GET, httpEntity,String.class);
            //EzilWorkers ezilWorkers= (EzilWorkers) JSON.parseObject(forObject.getBody(),EzilWorkers.class);
            result=forObject.getBody();
        } catch (RestClientException e) {
           result=null;
        } finally {
            return result;
        }

    }
}
