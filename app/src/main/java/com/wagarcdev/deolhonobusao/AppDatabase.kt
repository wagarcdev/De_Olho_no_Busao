package com.wagarcdev.deolhonobusao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wagarcdev.deolhonobusao.data.local.AppDatabaseDAO
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStopEntity
import com.wagarcdev.deolhonobusao.data.remote.responses.LineEntity

@Database(entities = [BusStopEntity::class, LineEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: AppDatabaseDAO

}