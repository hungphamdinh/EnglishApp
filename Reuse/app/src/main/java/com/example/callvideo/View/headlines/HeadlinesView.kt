package com.example.callvideo.View.headlines;

import com.example.callvideo.Model.Article


interface HeadlinesView {

    fun insertListArticles(data: List<Article>?)
    fun loadingData(isNotFinished: Boolean)

}