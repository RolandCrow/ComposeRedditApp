package com.example.composeredditapp

import android.app.Application
import com.example.composeredditapp.di.DI

class RedditApplication: Application() {
    lateinit var di: DI

    override fun onCreate() {
        super.onCreate()
        initDependencyInject()
    }

    private fun initDependencyInject() {
        di = DI(this)
    }
}