package com.ezil.reminder.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezil.reminder.common.GetEzilWorkersData;
import com.ezil.reminder.handler.wxhandler.DemoHandler;

import com.ezil.reminder.mapper.AsyncService;
import com.ezil.reminder.model.EzilUsers;
import com.ezil.reminder.model.EzilWorkers;
import com.ezil.reminder.service.EzilUsersService;
import com.ezil.reminder.service.EzilWorkersService;
import com.ezil.reminder.utils.SignUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageRouter;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.util.xml.XStreamTransformer;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
@RequestMapping("/wx")
@RestController
public class WechatSetController {
    private static final Log log = LogFactory.getLog(WechatSetController.class);
    private EzilWorkers ezilWorkers;
    private EzilUsers ezilUsers;
    @Autowired
    private EzilWorkersService ezilWorkersService;
    @Autowired
    private EzilUsersService ezilUsersService;
    @Autowired
    private AsyncService asyncService;
    @RequestMapping("/test")
    public void getValue(HttpServletResponse response, @RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr){
        //配置微信接口信息 只需要用到一次
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {

            }
        } catch (Exception e) {

        }

    }
    @RequestMapping("/1")
    public String test(){
        //配置微信接口信息 只需要用到一次
     return "测试成功！";

    }
  @RequestMapping(value = "/test1", produces = {"application/xml;charset=UTF-8"})
  @ResponseBody
  public String handleXmlParams(@RequestBody String xmlRequest) throws Exception {
      log.info(xmlRequest);
      // 创建一个路由器
      IService iService=new WxService();
      DemoHandler demoHandler=new DemoHandler();
      WxMessageRouter router = new WxMessageRouter(iService);
      WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, xmlRequest);
      String content=wx.getContent();
      log.info(content);
      if(content==null){
          return "";
      }


      String returnMsg="";
      ezilUsers = ezilUsersService.getById(wx.getFromUserName());
      if(ezilUsers!=null&&content.equals("算力")){

          try {
              JSONArray jsonArray = GetEzilWorkersData.findMachine(ezilUsers.getWallet());
              double allHashRate=0;
              int count=0;
              if (jsonArray.size()>3) {
                  for (int i = 0; i < jsonArray.size(); i++) {
                      JSONObject jo = jsonArray.getJSONObject(i);
                      ezilWorkers =  JSON.parseObject(jo.toString(), EzilWorkers.class);
                      double hashRate= (double) ezilWorkers.getAverage_hashrate()/(1024*1024);
                      allHashRate=hashRate+allHashRate;
                      returnMsg=ezilWorkers.getWorker()+" : "+hashRate+"  MH/S\r"+returnMsg;
                      count++;
                  }
                  returnMsg=returnMsg+count+"台设备 总算力 : "+allHashRate+" MH/S";
              }
          } catch (Exception ex) {
              returnMsg="网站无法响应！请晚点再试！";
          }
      }
      else if(ezilUsers!=null&&content.equals("提醒")){
          ezilUsers.setRemindStatus(1);
          ezilUsersService.updateById(ezilUsers);
          returnMsg="已经打开掉线提醒";

          asyncService.autoMachineStatusRoutine(ezilUsers,60,"wx1fb4e35bd6601dda","2924c0d7bb30955d891a5c29cf7c438a","hGSSupGsKeAGW0YLjgzohLu2DE5CT_K5YrAP1l3-8H4");

          //returnMsg=PostMessage.sendRemind("wx1fb4e35bd6601dda","2924c0d7bb30955d891a5c29cf7c438a",byId.getUsername(),"hGSSupGsKeAGW0YLjgzohLu2DE5CT_K5YrAP1l3-8H4","213");
      }
      else if(ezilUsers==null){
          JSONArray jsonArray = GetEzilWorkersData.findMachine(wx.getContent());
          if (jsonArray.size()>3) {
              for (int i = 0; i < jsonArray.size(); i++) {
                  JSONObject jo = jsonArray.getJSONObject(i);
                  ezilWorkers = (EzilWorkers) JSON.parseObject(jo.toString(), EzilWorkers.class);
                  String getFromUserName = wx.getFromUserName();
                  ezilWorkers.setId(getFromUserName + "|" + ezilWorkers.getWorker());
                  log.info(ezilWorkers);
                  ezilWorkersService.save(ezilWorkers);


              }
              ezilUsers = new EzilUsers();
              ezilUsers.setUsername(wx.getFromUserName());
              ezilUsers.setWallet(ezilWorkers.getWallet());
              ezilUsers.setWorkersTotal(jsonArray.size());
              ezilUsers.setRemindStatus(0);
              ezilUsers.setCreateTime(new Date().getTime());
              System.out.println(ezilUsers);
              ezilUsersService.save(ezilUsers);
              returnMsg="绑定成功";
          }
          else{
              returnMsg="输入错误 请先输入钱包地址绑定账号";
          }

      }
      else if(content=="event"){
          return "";
      }
      else {
          returnMsg="已绑定！请输入关键词 算力 查看当前算力 或 提醒 设置掉线提醒";
      }
      demoHandler.setReturnMsg(returnMsg);
      router.rule().handler(demoHandler).end();
      WxXmlOutMessage xmlOutMsg = router.route(wx);

      return xmlOutMsg.toXml();
  }
}
