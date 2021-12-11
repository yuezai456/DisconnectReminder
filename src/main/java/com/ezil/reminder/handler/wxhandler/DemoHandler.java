package com.ezil.reminder.handler.wxhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezil.reminder.mapper.EzilWorkersMapper;
import com.ezil.reminder.service.EzilWorkersService;
import com.ezil.reminder.utils.Check;
import com.ezil.reminder.model.EzilWorkers;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.exception.WxErrorException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import static com.ezil.reminder.utils.Check.getHttpS;
@Data
public class DemoHandler implements WxMessageHandler{
    private String returnMsg;
    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context, IService iService)
            throws WxErrorException {

        WxXmlOutMessage xmlOutMsg = WxXmlOutMessage.TEXT().content(returnMsg).toUser(wxMessage.getFromUserName()).fromUser(wxMessage.getToUserName()).build();
        return xmlOutMsg;
    }

}
