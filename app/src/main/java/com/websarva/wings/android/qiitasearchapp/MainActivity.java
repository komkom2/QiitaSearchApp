package com.websarva.wings.android.qiitasearchapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.websarva.wings.android.qiitasearchapp.adapter.ArticleAdapter;
import com.websarva.wings.android.qiitasearchapp.model.QiitaArticle;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button searchButton = findViewById(R.id.searchButton);
        EditText searchEditText = findViewById(R.id.searchEditText);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        OkHttpClient client = new OkHttpClient();

        searchButton.setOnClickListener(v -> {

            searchEditText.setSelection(0);
            progressBar.setVisibility(View.VISIBLE);

            String keyword = searchEditText.getText().toString().trim();
            if (keyword.isEmpty()) {
                // TODO:実装
                return;
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

            searchEditText.clearFocus();

            String url = "https://qiita.com/api/v2/items?query=" + Uri.encode(keyword) + "&per_page=20";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        // TODO: ダイアログ表示とかする
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        Log.d("QiitaResponse", json);

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<QiitaArticle>>(){}.getType();
                        List<QiitaArticle> articles = gson.fromJson(json, listType);

                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE);
                            RecyclerView recyclerView = findViewById(R.id.articleRecyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.setAdapter(new ArticleAdapter(MainActivity.this, articles));                        });
                    }
                }
            });
        });
    }
}