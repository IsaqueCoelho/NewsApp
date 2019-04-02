package com.example.android.newsapp.model;

public class News {

    private String newsTitle;
    private String newsAuthor;
    private String newsDate;
    private String newsSection;
    private String newsLink;

    public News(String newsTitle, String newsAuthor, String newsDate, String newsSection, String newsLink) {
        this.newsTitle = newsTitle;
        this.newsAuthor = newsAuthor;
        this.newsDate = newsDate;
        this.newsSection = newsSection;
        this.newsLink = newsLink;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsSection() {
        return newsSection;
    }

    public String getNewsLink() {
        return newsLink;
    }
}
