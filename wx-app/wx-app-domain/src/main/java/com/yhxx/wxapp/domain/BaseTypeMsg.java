package com.yhxx.wxapp.domain;

/**
 * @Author: Wanglf
 * @Date: Created in 23:04 2018/4/22
 * @modified By:
 */
public class BaseTypeMsg {

    private String ToUserName;
    private String FromUserName;
    private Integer CreateTime;
    private String MsgType;


    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Integer getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Integer createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
