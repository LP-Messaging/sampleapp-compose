package com.liveperson.compose.sample

import android.app.Application
import com.liveperson.compose.sample.di.dataModule
import com.liveperson.compose.sample.di.domainModule
import com.liveperson.compose.sample.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@SampleApp)
            modules(dataModule, domainModule, presentationModule)
        }
    }
}