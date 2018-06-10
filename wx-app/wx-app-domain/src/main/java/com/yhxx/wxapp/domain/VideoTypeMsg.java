package com.yhxx.wxapp.domain;

/**
 * @Author: Wanglf
 * @Date: Created in 23:11 2018/4/22
 * @modified By:
 */
public class VideoTypeMsg extends BaseTypeMsg {

    private Video video;

    public static class Video{
        private String MediaId;

        public String getMediaId() {
            return MediaId;
        }

        public void setMediaId(String mediaId) {
            MediaId = mediaId;
        }
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
