package com.mihanjk.fintechhomework.model.entity

data class News(val id: Long,
                val title: String,
                val publicationDate: Long)

data class NewsList(val news: List<News>)
