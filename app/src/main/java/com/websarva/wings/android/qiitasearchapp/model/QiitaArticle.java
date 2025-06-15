package com.websarva.wings.android.qiitasearchapp.model;

/**
 * Qiitaの記事データ
 */
public class QiitaArticle {
    /** 記事のタイトル */
    public String title;
    /** 記事のURL */
    public String url;
    /** 投稿したユーザー情報 */
    public User user;
    /** 記事の投稿日（ISO 8601形式の文字列。例: "2025-10-01T10:00:00+09:00"） */
    public String created_at;

    public static class User {
        /** ユーザーID（Qiitaのユーザー名） */
        public String id;
        /** プロフィール画像URL */
        public String profile_image_url;
    }

    /**
     * 記事のURLを取得
     */
    public String getUrl() {
        return url;
    }
}