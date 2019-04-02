package com.example.android.newsapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.newsapp.Adapter.NewsArrayAdapter;
import com.example.android.newsapp.R;
import com.example.android.newsapp.model.News;
import com.example.android.newsapp.utils.ConstantUtils;
import com.example.android.newsapp.utils.NewsLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TechnologyFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private NewsArrayAdapter mNewsArrayAdapter;

    @BindView(R.id.listview_newslist)
    ListView listViewNews;

    @BindView(R.id.progressbar_loadingdata)
    ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View technologyView = inflater.inflate(R.layout.news_fragment, container, false);

        ButterKnife.bind(this, technologyView);

        initView(technologyView);

        initRecyclerViewNewsList();

        initRequest();

        return technologyView;
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {

        Uri baseUri = Uri.parse(ConstantUtils.CONSTANT_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(ConstantUtils.CONSTANT_PARAM_Q, ConstantUtils.CONSTANT_VALUE_Q_TECHNOLOGY);
        uriBuilder.appendQueryParameter(ConstantUtils.CONSTANT_PARAM_ORDER_BY, ConstantUtils.CONSTANT_VALUE_ORDER_BY);
        uriBuilder.appendQueryParameter(ConstantUtils.CONSTANT_PARAM_PAGE_SIZE, ConstantUtils.CONSTANT_VALUE_PAGE_SIZE);
        uriBuilder.appendQueryParameter(ConstantUtils.CONSTANT_PARAM_SHOW_TAGS, ConstantUtils.CONSTANT_VALUE_SHOW_TAGS);
        uriBuilder.appendQueryParameter(ConstantUtils.CONSTANT_PARAM_API_KEY, ConstantUtils.CONSTANT_VALUE_API_KEY);

        return new NewsLoaderUtils(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        mNewsArrayAdapter.clear();

        if (news != null && !news.isEmpty()) {
            mNewsArrayAdapter.addAll(news);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mNewsArrayAdapter.clear();
    }

    private void initView(View technologyView) {
        progressBar = technologyView.findViewById(R.id.progressbar_loadingdata);
        listViewNews = technologyView.findViewById(R.id.listview_newslist);
    }

    private void initRecyclerViewNewsList() {

        mNewsArrayAdapter = new NewsArrayAdapter(getContext(), new ArrayList<News>());

        listViewNews.setAdapter(mNewsArrayAdapter);

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News newsItem = mNewsArrayAdapter.getItem(position);

                Uri newsUri = Uri.parse(newsItem.getNewsLink());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });
    }

    private void initRequest() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            progressBar.setVisibility(View.VISIBLE);
            loaderManager.initLoader(ConstantUtils.CONSTANT_NEWS_LOADER_ID, null, this);
        } else {
            Toast.makeText(getContext(), getString(R.string.error_technologyfragment_connection_failure), Toast.LENGTH_LONG).show();
        }
    }
}
