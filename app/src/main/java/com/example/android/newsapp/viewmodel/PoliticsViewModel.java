package com.example.android.newsapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.newsapp.model.News;
import com.example.android.newsapp.repository.PoliticsRepository;

import java.util.List;

public class PoliticsViewModel extends ViewModel {

    private MutableLiveData<List<News>> mNewsList;

    public void init(){
        if (mNewsList != null){
            return;
        }

        PoliticsRepository mNewsRepository = PoliticsRepository.getInstance();
        mNewsRepository.clearNewsList();
        mNewsList = mNewsRepository.getNewsList();
    }

    public LiveData<List<News>> getNewsList(){
        return mNewsList;
    }
}
