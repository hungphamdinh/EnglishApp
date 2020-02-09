package com.example.callvideo.Presenter.DetailNews

import android.text.SpannableStringBuilder
import com.example.callvideo.Model.Article
import com.example.callvideo.View.DetailNews.DetailNewsView

class DetailPresenter : IDetailNewsListener {
    private var mainIterator:DetailNews
    private var iDetailNewsView:DetailNewsView
    constructor(iDetailNewsView: DetailNewsView)  {
        mainIterator= DetailNews(this)
        this.iDetailNewsView=iDetailNewsView
    }

    fun loadNews(articles:Article) {
//        detailView.loadingData(true)
//        doAsync {
//            val data = ApiRepository<GetArticles>().requestGet(
//                    TheNewsOrg.getTopHeadlines(), GetArticles::class.java)
//
//            uiThread {
//                if (data?.status.equals("ok")) {
//                    detailView.insertListArticles(data?.articles)
//                }
//                detailView.loadingData(false)
//            }
//        }
        mainIterator.init(articles)
    }
    override fun getContent(content: SpannableStringBuilder) {
        iDetailNewsView.getContent(content)

    }

    override fun getTitle(title: String) {
        iDetailNewsView.getTitle(title)
    }

    override fun getSource(source: String) {
        iDetailNewsView.getSource(source)
    }

    override fun getClickString(map: HashMap<String, Any>) {
        iDetailNewsView.getClickString(map)
    }


}