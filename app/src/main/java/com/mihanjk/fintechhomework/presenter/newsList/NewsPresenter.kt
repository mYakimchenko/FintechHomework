package com.mihanjk.fintechhomework.presenter.newsList

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mihanjk.fintechhomework.model.entity.News
import com.mihanjk.fintechhomework.model.network.NewsApiSingleton
import com.mihanjk.fintechhomework.view.newsList.NewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


@InjectViewState
class NewsPresenter : MvpPresenter<NewsView>() {
    private val mNewsApi = NewsApiSingleton.getApi()

    private lateinit var mCachedNews: List<News>
    private var mLastUpdate: Long? = null

    fun getNews() {
        if (isCacheDataObsolete()) {
            mNewsApi.getNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { viewState.showLoading() }
                    .doAfterTerminate { viewState.hideLoading() }
                    .subscribe({
                        mCachedNews = it.news.sortedByDescending { it.publicationDate }
                        mLastUpdate = Date().time
                        viewState.showData(mCachedNews)
                    },
                            { viewState.showError(it.localizedMessage) })
        } else {
            viewState.hideLoading()
            viewState.showData(mCachedNews)
        }
    }

    private fun isCacheDataObsolete(): Boolean = if (mLastUpdate == null) true else
        (Date().time - mLastUpdate!!) >= 300000

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getNews()
    }
}