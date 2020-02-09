package com.example.callvideo.Presenter.DetailNews

import android.text.SpannableStringBuilder

interface IDetailNewsListener {
     fun getContent(content: SpannableStringBuilder)
     fun getTitle(title:String)
     fun getSource(source:String)
     fun getClickString(map: HashMap<String, Any>)
}