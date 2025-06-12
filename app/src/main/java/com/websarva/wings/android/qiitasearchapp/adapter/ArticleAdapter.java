package com.websarva.wings.android.qiitasearchapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websarva.wings.android.qiitasearchapp.R;
import com.websarva.wings.android.qiitasearchapp.ArticleWebViewActivity;
import com.websarva.wings.android.qiitasearchapp.model.QiitaArticle;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<QiitaArticle> articles;
    private Context context;

    public ArticleAdapter(Context context, List<QiitaArticle> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        QiitaArticle article = articles.get(position);
        holder.titleText.setText(article.title);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleWebViewActivity.class);
            intent.putExtra("url", article.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
        }
    }
}