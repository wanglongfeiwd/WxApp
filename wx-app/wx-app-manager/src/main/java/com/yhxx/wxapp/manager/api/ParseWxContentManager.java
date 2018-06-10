package com.yhxx.wxapp.manager.api;

import javax.servlet.ServletInputStream;
import java.util.Map;

/**
 * @Author: Wanglf
 * @Date: Created in 22:28 2018/5/1
 * @modified By:
 */
public interface ParseWxContentManager {
    Map<String,String> parseWxContent(ServletInputStream inputStream);
}
