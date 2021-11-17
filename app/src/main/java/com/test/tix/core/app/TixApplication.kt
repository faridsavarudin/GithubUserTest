package com.test.tix.core.app

import android.app.Application
import androidx.annotation.Keep
import com.test.tix.core.app.di.repositoryModule
import com.test.tix.core.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Keep
class TixApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@TixApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}