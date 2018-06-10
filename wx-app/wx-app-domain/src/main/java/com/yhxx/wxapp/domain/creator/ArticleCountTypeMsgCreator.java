package com.yhxx.wxapp.domain.creator;

import java.util.ArrayList;
import java.util.List;
import com.yhxx.wxapp.domain.ArticleCountTypeMsg;

import java.util.Map; /**
 * @Author: Wanglf
 * @Date: Created in 23:11 2018/5/2
 * @modified By:
 */
public class ArticleCountTypeMsgCreator {
    public static ArticleCountTypeMsg create(Map<String, String> wxContent) {
        ArticleCountTypeMsg articleCountTypeMsg = new ArticleCountTypeMsg();

        String ToUserName = wxContent.get("FromUserName");
        String FromUserName = wxContent.get("ToUserName");
        articleCountTypeMsg.setFromUserName(FromUserName);
        articleCountTypeMsg.setToUserName(ToUserName);
        articleCountTypeMsg.setCreateTime((int) (System.currentTimeMillis()/1000));
        articleCountTypeMsg.setMsgType("news");

        ArticleCountTypeMsg.ArticleCount articleCountOne = new ArticleCountTypeMsg.ArticleCount();
        articleCountOne.setArticles("图文信息");
        articleCountOne.setDescription("第一篇");
        articleCountOne.setPicUrl("https://www.baidu.com");
        articleCountOne.setTitle("balabala");
        articleCountOne.setUrl("https://www.youyaodongxi.com");
        List<ArticleCountTypeMsg.ArticleCount> list = new ArrayList<>();
        list.add(articleCountOne);

        articleCountTypeMsg.setArticleCountList(list);
        return articleCountTypeMsg;
    }
}
