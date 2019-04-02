package com.example.android.newsapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.newsapp.model.News;

import java.util.List;

public class NewsLoaderUtils extends AsyncTaskLoader<List<News>> {

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

        if(mUrl == null){
            return null;
        }

        return QueryUtils.getNewsData(mUrl);
    }
}
