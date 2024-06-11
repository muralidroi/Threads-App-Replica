package com.muralidhar.threadsapp

import android.app.Application
import com.muralidhar.threadsapp.koin.injectFeature
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ThreadsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ThreadsApplication)
            injectFeature()
        }
    }
}