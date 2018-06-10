package com.yhxx.wxapp.domain;

import java.util.List;

/**
 * @Author: Wanglf
 * @Date: Created in 23:16 2018/4/22
 * @modified By:
 */
public class ArticleCountTypeMsg extends BaseTypeMsg {

    private List<ArticleCount> articleCountList;

    public static class ArticleCount {

        private String Articles;
        private String Title;
        private String Description;
        private String PicUrl;
        private String Url;

        public String getArticles() {
            return Articles;
        }

        public void setArticles(String articles) {
            Articles = articles;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getPicUrl() {
            return PicUrl;
        }

        public void setPicUrl(String picUrl) {
            PicUrl = picUrl;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }
    }

    public List<ArticleCount> getArticleCountList() {
        return articleCountList;
    }

    public void setArticleCountList(List<ArticleCount> articleCountList) {
        this.articleCountList = articleCountList;
    }
}
