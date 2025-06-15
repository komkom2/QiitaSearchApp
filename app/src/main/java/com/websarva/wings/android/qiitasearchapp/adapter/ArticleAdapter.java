package com.websarva.wings.android.qiitasearchapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.websarva.wings.android.qiitasearchapp.R;
import com.websarva.wings.android.qiitasearchapp.ArticleWebViewActivity;
import com.websarva.wings.android.qiitasearchapp.model.QiitaArticle;

import java.util.List;

/**
 * Qiitaの記事リストをRecyclerViewに表示するアダプタークラス
 * 記事タイトル、ユーザー名、投稿日、プロフィール画像を表示
 *
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    /** 記事リスト */
    private List<QiitaArticle> articles;
    /** コンテキスト */
    private Context context;

    /**
     * コンストラクタ
     * @param context 呼び出し元のコンテキスト
     * @param articles 表示する記事リスト
     */
    public ArticleAdapter(Context context, List<QiitaArticle> articles) {
        this.context = context;
        this.articles = articles;
    }

    /**
     * ViewHolderの作成
     */
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    /**
     * ViewHolderのUIに記事データをセット
     */
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        QiitaArticle article = articles.get(position);

        holder.titleText.setText(article.title);
        holder.userNameText.setText(article.user.id);
        holder.createdAtText.setText(article.created_at.substring(0, 10)); // yyyy-MM-dd だけ表示

        Glide.with(context)
                .load(article.user.profile_image_url)
                .circleCrop()
                .into(holder.profileImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleWebViewActivity.class);
            intent.putExtra("url", article.getUrl());
            context.startActivity(intent);
        });
    }

    /**
     * 記事の件数を返す
     */
    @Override
    public int getItemCount() {
        return articles.size();
    }

    /**
     * 記事リストの1行分のViewHolderクラス
     */
    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView userNameText;
        TextView createdAtText;
        TextView titleText;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            userNameText = itemView.findViewById(R.id.userNameText);
            createdAtText = itemView.findViewById(R.id.createdAtText);
            titleText = itemView.findViewById(R.id.titleText);
        }
    }
}