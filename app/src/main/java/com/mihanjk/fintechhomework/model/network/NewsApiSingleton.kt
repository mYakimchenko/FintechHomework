package com.mihanjk.fintechhomework.model.network

import android.util.Log
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.mihanjk.fintechhomework.model.entity.News
import com.mihanjk.fintechhomework.model.entity.NewsList
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.reflect.Type

interface TinkoffNewsApi {
    @GET("news")
    fun getNews(): Single<NewsList>
}

object NewsApiSingleton {
    private val mGson: Gson = GsonBuilder()
            // todo why token with type List<News> doesn't work?
            .registerTypeAdapter(object : TypeToken<NewsList>() {}.type, NewsTypeAdapter())
            .create()

    private val mNewsApi: TinkoffNewsApi = Retrofit.Builder()
            .baseUrl("https://api.tinkoff.ru/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .build()
            .create(TinkoffNewsApi::class.java)

    fun getApi() = mNewsApi
}

class NewsTypeAdapter : JsonDeserializer<NewsList> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?,
                             context: JsonDeserializationContext?): NewsList {
        val result = ArrayList<News>()
        json?.run {
            asJsonObject.get("payload").asJsonArray
                    .map { it.asJsonObject }
                    .forEach {
                        result.add(News(it.get("id").asLong, it.get("text").asString,
                                it.get("publicationDate").asJsonObject.get("milliseconds").asLong))
                    }
        }
        Log.d("News", result.toString())
        return NewsList(result)
    }
}
