package com.ezil.reminder.service.impl;

import com.ezil.reminder.service.GetIpService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Service
public class GetIpServiceImpl implements GetIpService {

    @Override
    public String getip(String token) {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,String> ipInformation=new HashMap<>();

        ipInformation =restTemplate.getForObject("http://api.ip138.com/ip/?datatype=json&token="+token,Map.class);
        String ip=ipInformation.get("ip");
        return ip;
    }
}
