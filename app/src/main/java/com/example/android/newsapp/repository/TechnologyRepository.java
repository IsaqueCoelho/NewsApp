package com.example.android.newsapp.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.android.newsapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class TechnologyRepository {

    private static TechnologyRepository technologyRepositoryInstance;
    private ArrayList<News> mNewsDataSet = new ArrayList<>();

    public static TechnologyRepository getInstance(){
        if(technologyRepositoryInstance == null){
            technologyRepositoryInstance = new TechnologyRepository();
        }
        return technologyRepositoryInstance;
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
                "E3 2018: Microsoft's gaming chiefs on the future of Xbox",
                "Keza McDonald",
                "Jun 11, 2018",
                null
        ));
    }
}
