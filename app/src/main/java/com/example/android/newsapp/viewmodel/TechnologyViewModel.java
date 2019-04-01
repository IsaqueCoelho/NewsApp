package com.example.android.newsapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.android.newsapp.model.News;
import com.example.android.newsapp.repository.TechnologyRepository;

import java.util.List;

public class TechnologyViewModel extends ViewModel {

    private MutableLiveData<List<News>> mNewsList;

    public void init(){
        if (mNewsList != null){
            return;
        }

        TechnologyRepository mNewsRepository = TechnologyRepository.getInstance();
        mNewsRepository.clearNewsList();
        mNewsList = mNewsRepository.getNewsList();
    }

    public LiveData<List<News>> getNewsList(){
        return mNewsList;
    }
}
