package com.yhxx.wxapp.service.api;

import javax.servlet.ServletInputStream;

/**
 * @Author: Wanglf
 * @Date: Created in 19:20 2018/5/1
 * @modified By:
 */
public interface AutomaticResponseMsgService {

    String receiveAllInfo(ServletInputStream inputStream);
}
