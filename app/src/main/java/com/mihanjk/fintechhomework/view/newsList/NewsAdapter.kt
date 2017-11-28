package com.mihanjk.fintechhomework.view.newsList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mihanjk.fintechhomework.R
import com.mihanjk.fintechhomework.model.entity.News
import kotlinx.android.synthetic.main.news_item.view.*


class NewsAdapter(var values: List<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindNews(values[position])
    }

    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindNews(news: News) {
            itemView.newsTitle.text = news.title
        }
    }

    fun updateData(news: List<News>) {
        values = news
        notifyDataSetChanged()
    }
}
