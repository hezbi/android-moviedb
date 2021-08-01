package com.islamistudio.gamedb

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.islamistudio.gamedb.core.di.databaseModule
import com.islamistudio.gamedb.core.di.networkModule
import com.islamistudio.gamedb.core.di.repositoryModule
import com.islamistudio.gamedb.di.useCaseModule
import com.islamistudio.gamedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GameApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger(Level.NONE)
            androidContext(this@GameApp)
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