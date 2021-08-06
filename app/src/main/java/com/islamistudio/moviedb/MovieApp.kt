package com.islamistudio.moviedb

import android.app.Application
import com.islamistudio.moviedb.core.di.databaseModule
import com.islamistudio.moviedb.core.di.networkModule
import com.islamistudio.moviedb.core.di.repositoryModule
import com.islamistudio.moviedb.di.useCaseModule
import com.islamistudio.moviedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger(Level.NONE)
            androidContext(this@MovieApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}