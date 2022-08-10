package com.wagarcdev.deolhonobusao.di

import android.app.Application
import androidx.room.Room
import com.wagarcdev.deolhonobusao.AppDatabase
import com.wagarcdev.deolhonobusao.data.AppRepositoryImplementation
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
            "app_database.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideAppRepository(db: AppDatabase): AppRepository{
        return  AppRepositoryImplementation(db.dao)
    }

}