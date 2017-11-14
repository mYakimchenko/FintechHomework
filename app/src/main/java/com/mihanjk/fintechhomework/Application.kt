package com.mihanjk.fintechhomework

import android.app.Application
import com.facebook.stetho.Stetho

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        val initializerBuilder = Stetho.newInitializerBuilder(this)
        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        )
        Stetho.initialize(initializerBuilder.build())
    }
}

