package com.wagarcdev.deolhonobusao.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStopEntity
import com.wagarcdev.deolhonobusao.data.remote.responses.LineEntity
import com.wagarcdev.deolhonobusao.domain.model.BusMarkerPosition

@Database(
    entities = [
        BusStopEntity::class,
        LineEntity::class,
        BusMarkerPosition::class
    ],
    version = 5
)

abstract class AppDatabase: RoomDatabase() {

    abstract val dao: AppDatabaseDAO

}