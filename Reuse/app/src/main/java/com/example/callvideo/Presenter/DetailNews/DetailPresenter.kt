package com.example.callvideo.Presenter.DetailNews

import com.example.callvideo.Model.GetArticles
import com.example.callvideo.Service.ApiRepository
import com.example.callvideo.Service.TheNewsOrg
import com.example.callvideo.View.DetailNews.DetailNewsView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val detailView:DetailNewsView) {
    fun getHeadlineArticles() {
        detailView.loadingData(true)
        doAsync {
            val data = ApiRepository<GetArticles>().requestGet(
                    TheNewsOrg.getTopHeadlines(), GetArticles::class.java)

            uiThread {
                if (data?.status.equals("ok")) {
                    detailView.insertListArticles(data?.articles)
                }
                detailView.loadingData(false)
            }
        }
    }
}