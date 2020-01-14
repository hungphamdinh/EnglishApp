package com.example.callvideo.Service;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callvideo.Model.Article
import com.example.callvideo.OnItemClickListener
import com.example.callvideo.R
import com.example.callvideo.ViewHolder.ItemArticleHolder
import java.util.*


class ListArticlesAdapter(val list: ArrayList<Article> = ArrayList()) : RecyclerView.Adapter<ItemArticleHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener<Article>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArticleHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article, parent, false)
        return ItemArticleHolder(view)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<Article>) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: ItemArticleHolder, position: Int) {
        holder.bind(list.get(position))
        holder.itemView.setOnClickListener { it ->
            if(onItemClickListener != null){
                onItemClickListener.onClicked(list.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}