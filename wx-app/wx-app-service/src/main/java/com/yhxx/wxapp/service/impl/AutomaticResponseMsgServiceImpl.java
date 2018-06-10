package com.yhxx.wxapp.service.impl;

import com.yhxx.common.bean.CommonLogger;
import com.yhxx.common.utils.LoggerUtils;
import com.yhxx.wxapp.service.api.AutomaticResponseMsgService;
import com.yhxx.wxapp.manager.api.ParseWxContentManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.yhxx.wxapp.domain.*;
import com.yhxx.wxapp.domain.creator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import java.io.Writer;
import java.util.Map;

/**
 * @Author: Wanglf
 * @Date: Created in 19:22 2018/5/1
 * @modified By:
 */
@Service
public class AutomaticResponseMsgServiceImpl implements AutomaticResponseMsgService {

    @Autowired
    private ParseWxContentManager parseWxContentManager;

    @Override
    public String receiveAllInfo(ServletInputStream inputStream) {

        //对参数进行解析，将数据存放到map中
        Map<String, String> wxContent = parseWxContentManager.parseWxContent(inputStream);
        LoggerUtils.info(CommonLogger.BIZ,"解析微信发送之后的消息："+wxContent.toString());
        //根据不同的map值进行判断应该返回对应的消息提体
        return respDifferentTypeMsg(wxContent);
    }

    private String respDifferentTypeMsg(Map<String, String> wxContent) {

        String result = "";
        if (wxContent.containsKey("MsgType")) {
            switch (wxContent.get("MsgType")){
                case "text":
                    result = respTextTypeMsg(wxContent);
                    break;
                case "image":
                    result = respImageTypeMsg(wxContent);
                    break;
                case "voice":
                    //需要对声音进行判断，然后回复对应的音乐数据
                    result = respVoiceTypeMsg(wxContent);
                    break;
                case "video":
                    result = respVideoTypeMsg(wxContent);
                    break;
                case "shortvideo":
                    //需要对视频进行判断，然后进行返回数据
                    result = respShortVideoTypeMsg(wxContent);
                    break;
                case "location":
                    //返回对应的地址信息
                    result = respLocationTypeMsg(wxContent);
                    break;
                case "link":
                    //返回对应的连接地址
                    result = respLinkTypeMsg(wxContent);
                    break;
            }

        }
        LoggerUtils.info(CommonLogger.BIZ,"返回的消息体：" + result );
        return result;
    }

    private String respImageTypeMsg(Map<String, String> wxContent) {

        ImageTypeMsg imageTypeMsg = ImageTypeMsgCreator.create(wxContent);
        xstream.alias("xml",ImageTypeMsg.class);
        xstream.alias("Image",ImageTypeMsg.Image.class);
        return xstream.toXML(imageTypeMsg);
    }

    private String respMusicTypeMsg(Map<String, String> wxContent) {

        MusicTypeMsg musicTypeMsg = MusicTypeMsgCreator.create(wxContent);
        xstream.alias("xml",MusicTypeMsg.class);
        xstream.alias("Music",MusicTypeMsg.Music.class);
        return xstream.toXML(musicTypeMsg);
    }

    private String respArticleCountTypeMsg(Map<String, String> wxContent){

        ArticleCountTypeMsg articleCountTypeMsg = ArticleCountTypeMsgCreator.create(wxContent);
        xstream.alias("xml",ArticleCountTypeMsg.class);
        xstream.alias("item",ArticleCountTypeMsg.ArticleCount.class);
        return xstream.toXML(articleCountTypeMsg);
    }

    private String respVoiceTypeMsg(Map<String, String> wxContent) {

        VoiceTypeMsg voiceTypeMsg = VoiceTypeMsgCreator.create(wxContent);
        xstream.alias("xml",VoiceTypeMsg.class);
        xstream.alias("Voice",VoiceTypeMsg.Voice.class);
        return xstream.toXML(voiceTypeMsg);
    }

    private String respVideoTypeMsg(Map<String, String> wxContent) {

        VideoTypeMsg videoTypeMsg = VideoTypeMsgCreator.create(wxContent);
        xstream.alias("xml",VideoTypeMsg.class);
        xstream.alias("Video",VideoTypeMsg.Video.class);
        return xstream.toXML(videoTypeMsg);
    }

    private String respShortVideoTypeMsg(Map<String, String> wxContent) {
        return respVideoTypeMsg(wxContent);
    }

    private String respLocationTypeMsg(Map<String, String> wxContent) {
        return respTextTypeMsg(wxContent);
    }

    private String respLinkTypeMsg(Map<String, String> wxContent) {
        return respTextTypeMsg(wxContent);
    }

    private String respTextTypeMsg(Map<String, String> wxContent) {

        TextTypeMsg textTypeMsg = TextTypeMsgCreator.create(wxContent);
        xstream.alias("xml",TextTypeMsg.class);
        return xstream.toXML(textTypeMsg);
    }

    private static XStream xstream = new XStream(new XppDriver() {

        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点都增加CDATA标记
                boolean cdata = true;

                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }
                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

}
