package com.ezil.reminder.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezil.reminder.common.GetEzilWorkersData;
import com.ezil.reminder.common.PostMessage;
import com.ezil.reminder.model.EzilUsers;
import com.ezil.reminder.model.EzilWorkers;
import com.ezil.reminder.service.impl.EzilUsersServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncService {
    @Autowired
    private EzilUsersServiceImpl ezilUsersService;
    // 指定使用beanname为doSomethingExecutor的线程池
    @Async("doSomethingExecutor")
    public String doSomething(String message) {

        try {
            log.info(ezilUsersService.getById("oyJia52XmAXz1jfxUkQ7pZ2XT5ls").toString());
            Thread.sleep(3000);
            log.info("do something, 2message={}", message);
        } catch (InterruptedException e) {
            log.error("do something error: ", e);
        }
        return message;
    }
    @Async("doSomethingExecutor")
    public String  autoMachineStatusRoutine(EzilUsers ezilUsers,int routineTime,String appID,String secret,String templateID) {

        try {
            //int workersTotal=ezilUsers.getWorkersTotal();
            //int nowWorkersTotal=workersTotal;
       while(ezilUsersService.getById(ezilUsers.getUsername()).getRemindStatus()==1){
           log.info(ezilUsers.toString());
           Thread.sleep(routineTime*1000);
            try {
                JSONArray jsonArray = GetEzilWorkersData.findMachine(ezilUsers.getWallet());
//               nowWorkersTotal=jsonArray.size();
//               log.info("总数 "+nowWorkersTotal);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    EzilWorkers ezilWorkers = JSON.parseObject(jo.toString(), EzilWorkers.class);
                    if (ezilWorkers.getReported_hashrate() == null) {
                        PostMessage.sendRemind(appID, secret, ezilUsers.getUsername(), templateID, ezilWorkers.getWorker());
                    }
                }
            }
            catch (Exception ex){
                log.info(ex.toString());
            }


       }
       log.info(ezilUsers.getUsername()+"已关闭提醒");
        } catch (InterruptedException e) {
            log.error("do something error: ", e);
        }
        return null;
    }
}