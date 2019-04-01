package com.example.android.newsapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> mNewsList;
    private CardViewOnClickListener mCardViewOnClickListener;

    public NewsAdapter(List<News> mNewsList, CardViewOnClickListener mCardViewOnClickListener) {
        this.mNewsList = mNewsList;
        this.mCardViewOnClickListener = mCardViewOnClickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View NewsView =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);

        return new NewsViewHolder(NewsView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {
        final News news = mNewsList.get(position);

        newsViewHolder.textViewNewsTitle.setText(news.getNewsTitle());
        newsViewHolder.textViewNewsDate.setText(news.getNewsDate());
        newsViewHolder.textViewNewsAuthor.setText(news.getNewsAuthor());
        newsViewHolder.cardViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardViewOnClickListener.onCardViewClick(news.getNewsLink());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardview_news)
        CardView cardViewNews;

        @BindView(R.id.textview_newstitle)
        TextView textViewNewsTitle;

        @BindView(R.id.textview_newsdate)
        TextView textViewNewsDate;

        @BindView(R.id.textview_newsauthor)
        TextView textViewNewsAuthor;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface CardViewOnClickListener{
        void onCardViewClick(String link);
    }
}
