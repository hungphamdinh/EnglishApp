package com.example.callvideo.View.DetailNews

import android.text.SpannableStringBuilder

interface DetailNewsView {
//
//    fun insertListArticles(data: List<Article>?)
//    fun loadingData(isNotFinished: Boolean)
    fun getContent(content: SpannableStringBuilder)
    fun getTitle(title:String)
    fun getSource(source:String)
    fun getClickString(map: HashMap<String, Any>)
}