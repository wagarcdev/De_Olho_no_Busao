package com.wagarcdev.deolhonobusao.di

import android.app.Application
import androidx.room.Room
import com.wagarcdev.deolhonobusao.data.AppRepositoryImplementation
import com.wagarcdev.deolhonobusao.data.local.AppDatabase
import com.wagarcdev.deolhonobusao.data.remote.OlhoVivoAPI
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAppRepository(db: AppDatabase, api: OlhoVivoAPI): AppRepository{
        return  AppRepositoryImplementation(api, db.dao)
    }

    @Singleton
    @Provides
    fun provideOlhoVivoApi(): OlhoVivoAPI {

        val cookieManager = CookieManager()

        val httpClient = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(OlhoVivoAPI::class.java)
    }

}