package com.example.callvideo.ViewHolder;

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.callvideo.Model.Source
import com.example.callvideo.R
import com.qornanali.footballclubkt.util.DateFormatter
import com.squareup.picasso.Picasso

import java.net.URL

class ItemSourceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivSourceLogo: ImageView = itemView.findViewById(R.id.iv_source_logo)
    private val tvSourceName: TextView = itemView.findViewById(R.id.tv_source_name)
    private val tvSourceDesc: TextView = itemView.findViewById(R.id.tv_source_desc)

    fun bind(source: Source) {
         val url = URL(source.url)
        Picasso.get().load("https://logo.clearbit.com/" + url.protocol + "/" + url.authority)
                .resize(80, 80).centerInside()
                .into(ivSourceLogo)
        tvSourceDesc.text = source.description
        tvSourceName.text = source.name
    }
}
