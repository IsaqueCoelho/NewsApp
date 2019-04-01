package com.example.android.newsapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.newsapp.model.News;
import com.example.android.newsapp.repository.NewsRepository;
import com.example.android.newsapp.utils.ConstantUtils;

import java.util.List;

public class TechnologyViewModel extends ViewModel {

    private MutableLiveData<List<News>> mNewsList;

    public void init(){
        if (mNewsList != null){
            return;
        }

        NewsRepository mNewsRepository = NewsRepository.getInstance();
        mNewsRepository.clearNewsList();
        mNewsRepository.buildNewsList(ConstantUtils.CONSTANT_TECHNOLOGY);
        mNewsList = mNewsRepository.getNewsList();
    }

    public LiveData<List<News>> getNewsList(){
        return mNewsList;
    }
}
