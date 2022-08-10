package com.wagarcdev.deolhonobusao.data.local

import androidx.room.Dao
import androidx.room.Query
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStopEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDatabaseDAO {

    @Query("SELECT * from bus_stops_tbl")
    fun getBusStops(): Flow<List<BusStopEntity>>

    @Query("SELECT * from bus_stops_tbl where cp=:id")
    suspend fun findBusStopById(id: Int): BusStopEntity

    @Query("SELECT * from bus_stops_tbl where np=:name")
    suspend fun findBusStopByNames(name: String): BusStopEntity




}