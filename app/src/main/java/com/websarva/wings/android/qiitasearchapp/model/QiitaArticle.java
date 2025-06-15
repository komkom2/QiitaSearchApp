package com.websarva.wings.android.qiitasearchapp.model;

public class QiitaArticle {
    public String title;
    public String url;
    public User user;
    public String created_at;

    public static class User {
        public String id;
        public String profile_image_url;
    }

    public String getUrl() {
        return url;
    }
}