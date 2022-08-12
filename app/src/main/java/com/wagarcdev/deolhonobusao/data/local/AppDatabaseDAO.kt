package com.wagarcdev.deolhonobusao.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.domain.model.BusPositionMarker
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDatabaseDAO {

    @Query("SELECT * from bus_stop_tbl")
    fun getBusStops(): Flow<List<BusStop>>

    @Query("SELECT * from bus_position_tbl")
    fun getBusPositions(): Flow<List<BusPositionMarker>>

    @Query("DELETE from bus_position_tbl")
    fun deleteAllBusPositions()

    @Query("SELECT * from bus_stop_tbl where id=:id")
    suspend fun findBusStopById(id: Int): BusStop

    @Query("SELECT * from bus_stop_tbl where name=:name")
    suspend fun findBusStopByNames(name: String): BusStop

    @Insert(onConflict = REPLACE)
    suspend fun addBusPos(busPosition: BusPositionMarker): Long

    @Insert(onConflict = REPLACE)
    suspend fun addBusStopPos(busStop: BusStop): Long


}