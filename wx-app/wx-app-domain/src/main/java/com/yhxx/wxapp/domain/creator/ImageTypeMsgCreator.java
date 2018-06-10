package com.yhxx.wxapp.domain.creator;

import com.yhxx.wxapp.domain.ImageTypeMsg;

import java.util.Map; /**
 * @Author: Wanglf
 * @Date: Created in 23:11 2018/5/2
 * @modified By:
 */
public class ImageTypeMsgCreator {
    public static ImageTypeMsg create(Map<String, String> wxContent) {

        ImageTypeMsg imageTypeMsg = new ImageTypeMsg();
        String ToUserName = wxContent.get("FromUserName");
        String FromUserName = wxContent.get("ToUserName");
        imageTypeMsg.setFromUserName(FromUserName);
        imageTypeMsg.setToUserName(ToUserName);
        imageTypeMsg.setCreateTime((int) (System.currentTimeMillis()/1000));
        imageTypeMsg.setMsgType("imgage");
        ImageTypeMsg.Image image = new ImageTypeMsg.Image();
        image.setMediaId(wxContent.get("MsgId"));
        imageTypeMsg.setImage(image);
        return imageTypeMsg;

    }
}
