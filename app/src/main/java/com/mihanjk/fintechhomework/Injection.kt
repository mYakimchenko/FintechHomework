package com.mihanjk.fintechhomework

import android.content.Context


object Injection {
    private var database: NodeDatabaseHelper? = null

    fun provideUserDataSource(context: Context) = database ?:
            NodeDatabaseHelper(context).apply { database = this }
}
