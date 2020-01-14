package com.example.callvideo.View.detailarticle

import android.os.Bundle

import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.callvideo.Model.Article
import com.example.callvideo.Presenter.DetailArticle.DetailArticlePresenter
import com.example.callvideo.R
import org.jetbrains.anko.find



class DetailArticleActivity : AppCompatActivity(), DetailArticleView {

    private lateinit var presenter: DetailArticlePresenter
    private lateinit var toolbar: Toolbar
    private lateinit var article: Article
    private lateinit var wvArticleContent: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailarticle)

        init()

        presenter.loadDetailArticle(article)
    }

    override fun loadUrl(url: String?) {
        wvArticleContent.loadUrl(url)
    }


    private fun init() {
        article = intent.extras.getSerializable("article") as Article

//        toolbar = find(R.id.toolbarNew)
//        setSupportActionBar(toolbar)
        supportActionBar?.title = article.title
        supportActionBar?.subtitle = article.url
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        wvArticleContent = find(R.id.wv_article_content)
        wvArticleContent.settings.loadsImagesAutomatically = true
        wvArticleContent.settings.javaScriptEnabled = true
        wvArticleContent.settings.domStorageEnabled = true
        wvArticleContent.settings.setSupportZoom(true)
        wvArticleContent.settings.builtInZoomControls = true
        wvArticleContent.settings.displayZoomControls = false

        presenter = DetailArticlePresenter(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}