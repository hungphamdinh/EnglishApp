package com.example.callvideo.Presenter.Headlines;


import com.example.callvideo.Model.GetArticles
import com.example.callvideo.Service.ApiRepository
import com.example.callvideo.Service.TheNewsOrg
import com.example.callvideo.View.headlines.HeadlinesView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HeadlinesPresenter(private val headlinesView: HeadlinesView) {

    fun getHeadlineArticles() {
        headlinesView.loadingData(true)
        doAsync {
            val data = ApiRepository<GetArticles>().requestGet(
                    TheNewsOrg.getTopHeadlines(), GetArticles::class.java)

            uiThread {
                if(data?.status.equals("ok")){
                    headlinesView.insertListArticles(data?.articles)
                }
                headlinesView.loadingData(false)
            }
        }
    }

}