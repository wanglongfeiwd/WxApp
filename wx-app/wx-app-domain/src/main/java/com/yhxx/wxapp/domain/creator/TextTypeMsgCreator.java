package com.yhxx.wxapp.domain.creator;

import com.yhxx.wxapp.domain.TextTypeMsg;

import java.util.Map; /**
 * @Author: Wanglf
 * @Date: Created in 22:45 2018/5/2
 * @modified By:
 */
public class TextTypeMsgCreator {
    public static TextTypeMsg create(Map<String, String> wxContent) {

        TextTypeMsg textTypeMsg = new TextTypeMsg();
        String ToUserName = wxContent.get("FromUserName");
        String FromUserName = wxContent.get("ToUserName");
        textTypeMsg.setFromUserName(FromUserName);
        textTypeMsg.setToUserName(ToUserName);
        textTypeMsg.setCreateTime((int) (System.currentTimeMillis()/1000));
        textTypeMsg.setMsgType("text");
        textTypeMsg.setContent("返回文本消息");
        return textTypeMsg;
    }
}
