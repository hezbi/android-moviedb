package com.islamistudio.moviedb.core.di

import androidx.room.Room
import com.islamistudio.moviedb.core.BuildConfig
import com.islamistudio.moviedb.core.data.MovieRepository
import com.islamistudio.moviedb.core.data.source.local.LocalDataSource
import com.islamistudio.moviedb.core.data.source.local.room.MovieDatabase
import com.islamistudio.moviedb.core.data.source.remote.RemoteDataSource
import com.islamistudio.moviedb.core.data.source.remote.network.ApiService
import com.islamistudio.moviedb.core.domain.repository.IMovieRepository
import com.islamistudio.moviedb.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.DB_PASS.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.BASE_DOMAIN, BuildConfig.SSL_PIN_1)
            .add(BuildConfig.BASE_DOMAIN, BuildConfig.SSL_PIN_2)
            .build()
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(
            get(),
            get(),
            get()
        )
    }
}