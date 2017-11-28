package com.mihanjk.fintechhomework.view.newsList

import com.arellomobile.mvp.MvpView
import com.mihanjk.fintechhomework.model.entity.News


interface NewsView: MvpView {
    fun showLoading()
    fun hideLoading()
    fun showError(localizedMessage: String)
    fun showData(news: List<News>)
}