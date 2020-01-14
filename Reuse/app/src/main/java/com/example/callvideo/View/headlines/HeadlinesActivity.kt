package com.example.callvideo.View.headlines;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callvideo.Model.Article
import com.example.callvideo.OnItemClickListener
import com.example.callvideo.Presenter.Headlines.HeadlinesPresenter
import com.example.callvideo.R
import com.example.callvideo.Service.ListArticlesAdapter
import com.example.callvideo.View.DetailNews.DeatailNewsActivity
import com.example.callvideo.View.detailarticle.DetailArticleActivity
import org.jetbrains.anko.startActivity

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HeadlinesActivity : AppCompatActivity(), HeadlinesView {

    private lateinit var rvArticles: RecyclerView
    private lateinit var pbArticles: ProgressBar
    private lateinit var adapter: ListArticlesAdapter
    private var articles = ArrayList<Article>()
    private lateinit var presenter: HeadlinesPresenter
    private lateinit var userPhone:String


    override fun insertListArticles(data: List<Article>?) {
        data?.let {
            articles.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun loadingData(isNotFinished: Boolean) {
        pbArticles.visibility = if(isNotFinished) View.VISIBLE else View.GONE
        rvArticles.visibility = if(isNotFinished) View.GONE else View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_headlines)
        if(intent!=null){
            userPhone=intent.getStringExtra("userPhone")
        }
        init()

        presenter.getHeadlineArticles()
    }

    private fun init() {

        pbArticles = findViewById<ProgressBar>(R.id.pb_articles)

        rvArticles = findViewById<RecyclerView>(R.id.rv_articles)
        rvArticles.layoutManager = LinearLayoutManager(this)
        rvArticles.isNestedScrollingEnabled = true

        adapter = ListArticlesAdapter(articles)
        adapter.setOnItemClickListener(OnItemClickListener {
            var intent:Intent= Intent(this@HeadlinesActivity,DeatailNewsActivity::class.java)
            var listData:ArrayList<Any>;
            listData= ArrayList();
            listData.add(0,userPhone)
            listData.add(1,it);
            intent.putExtra("listData",listData)

            startActivity(intent)
        })

        rvArticles.adapter = adapter

        presenter = HeadlinesPresenter(this)
    }

    fun onCvBrowseNewsClicked(view: View){
        //startActivity<ListSourcesActivity>()
    }

    fun onIvOpenSearchClicked(view: View){
        //startActivity<SearchArticlesActivity>()
    }

}