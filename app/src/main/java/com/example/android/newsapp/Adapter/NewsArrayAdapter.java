package com.example.android.newsapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.model.News;

import java.util.List;

public class NewsArrayAdapter extends ArrayAdapter<News> {

    public NewsArrayAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
        }


        News newsItem = getItem(position);

        TextView textViewTitle = listItemView.findViewById(R.id.textview_newstitle);
        TextView textViewDate = listItemView.findViewById(R.id.textview_newsdate);
        TextView textViewSection = listItemView.findViewById(R.id.textview_newssection);
        TextView textViewAuthor = listItemView.findViewById(R.id.textview_newsauthor);

        textViewTitle.setText(newsItem.getNewsTitle());
        textViewDate.setText(newsItem.getNewsDate());
        textViewSection.setText(newsItem.getNewsSection());
        textViewAuthor.setText(newsItem.getNewsAuthor());

        return listItemView;
    }
}
