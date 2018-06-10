package com.yhxx.wxapp.domain;

/**
 * @Author: Wanglf
 * @Date: Created in 23:10 2018/4/22
 * @modified By:
 */
public class VoiceTypeMsg extends BaseTypeMsg {

    private Voice Voice;

    public static class Voice {
        private String MediaId;

        public String getMediaId() {
            return MediaId;
        }

        public void setMediaId(String mediaId) {
            MediaId = mediaId;
        }
    }

    public VoiceTypeMsg.Voice getVoice() {
        return Voice;
    }

    public void setVoice(VoiceTypeMsg.Voice voice) {
        Voice = voice;
    }
}
