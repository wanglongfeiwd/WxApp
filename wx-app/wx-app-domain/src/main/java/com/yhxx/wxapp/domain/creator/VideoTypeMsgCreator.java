package com.yhxx.wxapp.domain.creator;

import com.yhxx.wxapp.domain.VideoTypeMsg;

import java.util.Map; /**
 * @Author: Wanglf
 * @Date: Created in 23:11 2018/5/2
 * @modified By:
 */
public class VideoTypeMsgCreator {
    public static VideoTypeMsg create(Map<String, String> wxContent) {

        VideoTypeMsg videoTypeMsg = new VideoTypeMsg();
        String ToUserName = wxContent.get("FromUserName");
        String FromUserName = wxContent.get("ToUserName");
        videoTypeMsg.setFromUserName(FromUserName);
        videoTypeMsg.setToUserName(ToUserName);
        videoTypeMsg.setCreateTime((int) (System.currentTimeMillis()/1000));
        videoTypeMsg.setMsgType("video");
        VideoTypeMsg.Video video = new VideoTypeMsg.Video();
        video.setMediaId(wxContent.get("MsgId"));
        videoTypeMsg.setVideo(video);
        return videoTypeMsg;
    }
}
