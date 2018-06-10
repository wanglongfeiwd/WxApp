package com.yhxx.wxapp.domain.creator;

import com.yhxx.wxapp.domain.VoiceTypeMsg;

import java.util.Map; /**
 * @Author: Wanglf
 * @Date: Created in 22:49 2018/5/2
 * @modified By:
 */
public class VoiceTypeMsgCreator {
    public static VoiceTypeMsg create(Map<String, String> wxContent) {

        VoiceTypeMsg voiceTypeMsg = new VoiceTypeMsg();
        voiceTypeMsg.setFromUserName(wxContent.get("ToUserName"));
        voiceTypeMsg.setToUserName(wxContent.get("FromUserName"));
        voiceTypeMsg.setMsgType("voice");
        voiceTypeMsg.setCreateTime((int) (System.currentTimeMillis()/1000));
        VoiceTypeMsg.Voice voice = new VoiceTypeMsg.Voice();
        voice.setMediaId(wxContent.get("MediaId"));
        voiceTypeMsg.setVoice(voice);
        return voiceTypeMsg;
    }
}
