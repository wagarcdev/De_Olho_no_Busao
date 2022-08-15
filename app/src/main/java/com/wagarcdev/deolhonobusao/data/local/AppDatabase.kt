package com.wagarcdev.deolhonobusao.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.data.remote.responses.ResponseObj

@Database(
    entities = [
        ResponseObj::class,
        BusStop::class,
    ],
    version = 11
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: AppDatabaseDAO

}