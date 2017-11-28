package com.mihanjk.fintechhomework.view.newsList

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mihanjk.fintechhomework.R
import com.mihanjk.fintechhomework.model.entity.News
import com.mihanjk.fintechhomework.presenter.newsList.NewsPresenter
import kotlinx.android.synthetic.main.activity_main.*

class NewsActivity : NewsView, MvpAppCompatActivity() {

    @InjectPresenter
    lateinit var mNewsPresenter: NewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList.layoutManager = LinearLayoutManager(this)
        newsList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        newsList.adapter = NewsAdapter(emptyList())

        swipeRefresh.setOnRefreshListener { mNewsPresenter.getNews() }
    }

    override fun showLoading() {
        if (!swipeRefresh.isRefreshing)
            progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
    }

    override fun showError(localizedMessage: String) {
        Snackbar.make(container, localizedMessage, Snackbar.LENGTH_LONG)
                .setAction(R.string.try_again, { mNewsPresenter.getNews() })
                .show()
    }

    override fun showData(news: List<News>) {
        newsList.adapter = NewsAdapter(news)
        newsList.adapter.notifyDataSetChanged()
    }
}
