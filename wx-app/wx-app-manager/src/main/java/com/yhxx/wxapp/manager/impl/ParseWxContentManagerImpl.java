package com.yhxx.wxapp.manager.impl;

import com.yhxx.common.bean.CommonLogger;
import com.yhxx.common.utils.LoggerUtils;
import com.yhxx.wxapp.manager.api.ParseWxContentManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wanglf
 * @Date: Created in 22:28 2018/5/1
 * @modified By:
 */
@Component
public class ParseWxContentManagerImpl implements ParseWxContentManager {

    @Override
    public Map<String,String> parseWxContent(ServletInputStream inputStream){

        SAXReader reader = new SAXReader();
        Map<String,String> result = new HashMap<>();
        try {
            Document read = reader.read(inputStream);
            Element root = read.getRootElement();
            result = parseContent(root,result);
        } catch (DocumentException e) {
            LoggerUtils.error(CommonLogger.ERROR,"解析xml出错"+e);
        }
        return result;
    }

    private Map<String, String> parseContent(Element root, Map<String, String> result) {

        List<Element> elements = root.elements();

        if (elements.size() == 0) {
            result.put(root.getName(),root.getTextTrim());
        }else {
            for (Element element:elements) {
                parseContent(element,result);
            }
        }

        return result;
    }


}
