package com.yhxx.wxapp.domain;

/**
 * @Author: Wanglf
 * @Date: Created in 23:07 2018/4/22
 * @modified By:
 */
public class ImageTypeMsg extends BaseTypeMsg {

    private Image Image;

    public static class Image{
        private String MediaId;

        public String getMediaId() {
            return MediaId;
        }

        public void setMediaId(String mediaId) {
            MediaId = mediaId;
        }
    }

    public ImageTypeMsg.Image getImage() {
        return Image;
    }

    public void setImage(ImageTypeMsg.Image image) {
        Image = image;
    }
}
