package com.wagarcdev.deolhonobusao.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.data.remote.responses.LineEntity
import com.wagarcdev.deolhonobusao.domain.model.BusPositionMarker

@Database(
    entities = [
        BusStop::class,
        LineEntity::class,
        BusPositionMarker::class
    ],
    version = 6
)

abstract class AppDatabase: RoomDatabase() {

    abstract val dao: AppDatabaseDAO

}