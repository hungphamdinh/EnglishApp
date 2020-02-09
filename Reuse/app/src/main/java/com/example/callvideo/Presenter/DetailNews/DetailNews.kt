package com.example.callvideo.Presenter.DetailNews

import android.text.SpannableStringBuilder
import com.example.callvideo.Model.Article
import com.qornanali.footballclubkt.util.DateFormatter
import java.net.URL

class DetailNews(private val iDetailNewsListener:IDetailNewsListener) {

     fun init(article: Article) {
        //article = intent.extras.getSerializable("article")
        val url = URL(article.url)
        //iDetailNewsListener.getContent(article.content.toString())
        var temp:String=""
        val output = article.content.toString().split("\\ ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in output.indices) {
            temp=temp+" "+output[i]+" "
            val textTranslate: SpannableStringBuilder = addClickablePart(temp);
            iDetailNewsListener.getContent(textTranslate)
            //txtDemo.setText(Arrays.toString(output))

        }
        iDetailNewsListener.getTitle(article.title.toString())
        var source = ""
        try {
            val splitStrDate = article.publishedAt?.split("T")
            val strDate = splitStrDate?.get(0) + " " + splitStrDate?.get(1)?.substring(0, splitStrDate?.get(1)?.length-1)
            val articleDate = DateFormatter.toDate(strDate, "yyyy-MM-dd hh:mm:ss")
            source = DateFormatter.toString(articleDate, "dd MMM yy") + ", "
        } catch (e: Exception) {

        }
        iDetailNewsListener.getSource(source + article.source?.name)
    }

    private fun addClickablePart(str: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(str)

        var startIdx = str.indexOf(" ")
        var endIdx = 0
        while (startIdx != -1) {
            endIdx = str.indexOf(" ", startIdx+1)
            // because endIdx can be confused " " from startIdx when it end at last index in element
            var map:HashMap<String,Any> = HashMap()
            map.put(key = "startIdx",value = startIdx)
            map.put(key = "endIdx",value = endIdx)
            map.put(key = "ssb",value = ssb)
            map.put(key = "str",value = str)

            iDetailNewsListener.getClickString(map)
            startIdx = str.indexOf(" ", endIdx+1)
            // avoid startIdx confused " " from endIdx when it start at new element at idx[0]
        }

        return ssb
    }
}