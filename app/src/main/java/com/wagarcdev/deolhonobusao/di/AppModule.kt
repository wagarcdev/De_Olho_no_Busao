package com.wagarcdev.deolhonobusao.di

import android.app.Application
import androidx.room.Room
import com.wagarcdev.deolhonobusao.data.local.AppDatabase
import com.wagarcdev.deolhonobusao.data.AppRepositoryImplementation
import com.wagarcdev.deolhonobusao.data.remote.OlhoVivoAPI
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(OlhoVivoAPI::class.java)
    }

}