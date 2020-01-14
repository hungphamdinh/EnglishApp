package com.example.callvideo.View.DetailNews

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.callvideo.Model.Article
import com.example.callvideo.R
import com.example.callvideo.View.Translate.TranslateActivity
import com.qornanali.footballclubkt.util.DateFormatter
import com.squareup.picasso.Picasso
import java.net.URL


class DeatailNewsActivity : AppCompatActivity() {

    private lateinit var article: Article
    private lateinit var logoNews: ImageView
    private lateinit var txtSource: TextView
    private lateinit var txtTitle: TextView
    private lateinit var  txtContent: TextView
    private lateinit var imgNews: ImageView
    private lateinit var listData:ArrayList<String>
    private lateinit var userPhone:String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deatail_news)
        logoNews = findViewById(R.id.newLogo)
        txtSource = findViewById(R.id.txtSourceNew)
        txtTitle = findViewById(R.id.txtTitleNew)
        txtContent = findViewById(R.id.txtContentNew)
        imgNews = findViewById(R.id.imageNew)
        if(intent!=null){
            listData=intent.getStringArrayListExtra("listData")
            userPhone=listData.get(0)
            article=listData.get(1) as Article;
        }
        init()

    }


    private fun init() {
        //article = intent.extras.getSerializable("article")
        val x = article
        Picasso.get().load(article.urlToImage).resize(200, 200).centerInside().into(imgNews)
        val url = URL(article.url)
        Picasso.get().load("https://logo.clearbit.com/" + url.protocol + "/" + url.authority)
                .resize(50, 50).centerInside()
                .into(logoNews)
        txtContent.text = article.content
        var temp:String=""
        val output = article.content.toString().split("\\ ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in output.indices) {
            temp=temp+"["+output[i]+"]"
            txtContent.text = temp
            //txtDemo.setText(Arrays.toString(output))
            txtContent.setMovementMethod(LinkMovementMethod.getInstance());
            txtContent.setText(addClickablePart(temp), TextView.BufferType.SPANNABLE);

        }
        txtTitle.text = article.title
        var source = ""
        try {
            val splitStrDate = article.publishedAt?.split("T")
            val strDate = splitStrDate?.get(0) + " " + splitStrDate?.get(1)?.substring(0, splitStrDate?.get(1)?.length-1)
            val articleDate = DateFormatter.toDate(strDate, "yyyy-MM-dd hh:mm:ss")
            source = DateFormatter.toString(articleDate, "dd MMM yy") + ", "
        } catch (e: Exception) {

        }
        txtSource.text = source + article.source?.name
    }

    private fun addClickablePart(str: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(str)

        var startIdx = str.indexOf("[")
        var endIdx = 0
        while (startIdx != -1) {
            endIdx = str.indexOf("]", startIdx)

            val clickString = str.substring(startIdx+1, endIdx)
            ssb.setSpan(object : ClickableSpan() {

                override fun onClick(widget: View) {
                    var intent:Intent= Intent(this@DeatailNewsActivity,TranslateActivity::class.java)
                    var listData:ArrayList<String>
                    listData=ArrayList()
                    listData.add(0, userPhone)
                    listData.add(1,clickString)
                    intent.putStringArrayListExtra("listData",listData);
                    startActivity(intent)
                }
            }, startIdx+1, endIdx, 0)
            startIdx = str.indexOf("[", endIdx)
        }

        return ssb
    }
}