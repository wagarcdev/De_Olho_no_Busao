package com.wagarcdev.deolhonobusao.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStopEntity
import com.wagarcdev.deolhonobusao.domain.model.BusMarkerPosition
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDatabaseDAO {

    @Query("SELECT * from bus_stops_tbl")
    fun getBusStops(): Flow<List<BusStopEntity>>

    @Query("SELECT * from bus_position_tbl")
    fun getBusPositions(): Flow<List<BusMarkerPosition>>

    @Query("SELECT * from bus_stops_tbl where cp=:id")
    suspend fun findBusStopById(id: Int): BusStopEntity

    @Query("SELECT * from bus_stops_tbl where np=:name")
    suspend fun findBusStopByNames(name: String): BusStopEntity

    @Insert(onConflict = REPLACE)
    suspend fun addBusPos(busPosition: BusMarkerPosition): Long




}