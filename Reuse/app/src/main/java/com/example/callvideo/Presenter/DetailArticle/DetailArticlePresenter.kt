package com.example.callvideo.Presenter.DetailArticle

import com.example.callvideo.Model.Article
import com.example.callvideo.View.detailarticle.DetailArticleView


class DetailArticlePresenter(private val detailArticleView: DetailArticleView) {

    fun loadDetailArticle(article: Article){
        detailArticleView.loadUrl(article.url)
    }
}