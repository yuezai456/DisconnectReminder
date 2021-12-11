package com.ezil.reminder;

import com.ezil.reminder.mapper.EzilWorkersMapper;
import com.ezil.reminder.model.EzilWorkers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootTest()
public class test {
    @Autowired
    private EzilWorkersMapper ezilWorkersMapper;
    @Test
    public void testSelect() {

        Map<String,Object> selectMap=new HashMap<String,Object>();
        selectMap.put("wallet","0x91d027eb528d6cceb4bc2b527dd0f5aa5ba7973b.zil1v2k64uhqenskfkuhj6x6e6nz9mqm02kwxayfpg");
        selectMap.put("worker","470");
        List<EzilWorkers> ezilWorkers1 = ezilWorkersMapper.selectByMap(selectMap);
        System.out.println(ezilWorkers1);
        for(EzilWorkers ezilWorkers: ezilWorkers1){
            System.out.println(ezilWorkers);
        }
    }
    @Test
    public void testInsert() {

    }
}
