package com.example.android.newsapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.newsapp.model.News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsLoaderUtils extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = "NewsLoaderUtils";
    private String mUrl;

    public NewsLoaderUtils(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {

        String newsJson = null;
        List<News> newsList;

        if(mUrl == null){
            return null;
        }

        try {
            newsJson = NewsUtils.getNewsData(mUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        newsList = NewsUtils.newsFormatingData(newsJson);


        return null;
    }
}
