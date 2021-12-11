package com.ezil.reminder.controller;

import com.ezil.reminder.service.GetIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/ip")
@RestController

public class FindIpController {
    @Autowired
    private GetIpService getIpService;
    @RequestMapping("/getip/{token}")
    public String getIpAddress(@PathVariable String token){
        String ip=getIpService.getip(token);

        return ip;
    }
}
