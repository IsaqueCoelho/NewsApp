package com.example.android.newsapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsapp.Adapter.NewsAdapter;
import com.example.android.newsapp.R;
import com.example.android.newsapp.model.News;
import com.example.android.newsapp.viewmodel.PoliticsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PoliticsFragment extends Fragment {

    private static final String LOG_TAG = "PoliticsFragment";
    private PoliticsViewModel mPoliticsViewModel;
    private NewsAdapter mNewsAdapter;

    @BindView(R.id.recyclerview_politicslist)
    RecyclerView recyclerViewPoliticsList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View politicsView =
                inflater.inflate(R.layout.politics_fragment, container, false);

        ButterKnife.bind(this, politicsView);

        return politicsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPoliticsViewModel = ViewModelProviders.of(this).get(PoliticsViewModel.class);

        mPoliticsViewModel.init();

        mPoliticsViewModel.getNewsList().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {
                mNewsAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerViewNewsList();
    }

    private void initRecyclerViewNewsList() {
        mNewsAdapter = new NewsAdapter(mPoliticsViewModel.getNewsList().getValue(), new NewsAdapter.CardViewOnClickListener() {
            @Override
            public void onCardViewClick(String link) {
                Log.e(LOG_TAG, "item clicked, link: " + link);
            }
        });

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerViewPoliticsList.setLayoutManager(linearLayoutManager);
        recyclerViewPoliticsList.setAdapter(mNewsAdapter);
    }
}
