package com.example.android.newsapp.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.android.newsapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class PoliticsRepository {

    private static PoliticsRepository politicsRepositoryInstance;
    private ArrayList<News> mNewsDataSet = new ArrayList<>();

    public static PoliticsRepository getInstance(){
        if(politicsRepositoryInstance == null){
            politicsRepositoryInstance = new PoliticsRepository();
        }
        return politicsRepositoryInstance;
    }

    public void clearNewsList(){
        mNewsDataSet.clear();
    }

    // Pretend to get data from a webservice or Firebase
    public MutableLiveData<List<News>> getNewsList(){

        buildNewsList();
        MutableLiveData<List<News>> newsData = new MutableLiveData<>();
        newsData.setValue(mNewsDataSet);

        return newsData;
    }

    private void buildNewsList(){
        mNewsDataSet.add(new News(
                "The 11 best games on Xbox One",
                "Kelth Stuart",
                "Nov 1, 2018",
                null
        ));
    }
}
