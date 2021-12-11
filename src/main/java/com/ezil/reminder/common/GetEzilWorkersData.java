package com.ezil.reminder.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezil.reminder.model.EzilWorkers;
import com.ezil.reminder.service.EzilWorkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

//用來處理微信返回的結果
public class GetEzilWorkersData {

    public static JSONArray findMachine(String wallet){

        String result=getHttpS(wallet);
        //JSONArray array =

      /*  for (int i = 0; i < array.size(); i++) {
            JSONObject jo = array.getJSONObject(i);
            ezilWorkers= (List) JSON.parseObject(jo.toString(),List.class);
        }*/
        return (JSONArray) JSONObject.parse(result);
    }

    public static String getHttpS(String wallet){

        String result=null;
        try {
            SimpleClientHttpRequestFactory simpleClientHttpRequestFactory=new SimpleClientHttpRequestFactory();
            simpleClientHttpRequestFactory.setConnectTimeout(2000);
            simpleClientHttpRequestFactory.setReadTimeout(2000);
            RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range
            //Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
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
