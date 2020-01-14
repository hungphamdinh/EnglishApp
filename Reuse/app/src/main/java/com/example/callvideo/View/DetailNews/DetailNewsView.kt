package com.example.callvideo.View.DetailNews

import com.example.callvideo.Model.Article

interface DetailNewsView {

    fun insertListArticles(data: List<Article>?)
    fun loadingData(isNotFinished: Boolean)

}