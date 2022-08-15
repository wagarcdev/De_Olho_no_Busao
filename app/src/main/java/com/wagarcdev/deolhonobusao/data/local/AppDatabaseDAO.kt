package com.wagarcdev.deolhonobusao.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.data.remote.responses.ResponseObj
import com.wagarcdev.deolhonobusao.domain.model.BusPositionMarker
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDatabaseDAO {

    @Query("SELECT * from bus_stop_tbl")
    fun getBusStops(): Flow<List<BusStop>>
//
//    @Query("SELECT * from bus_position_tbl")
//    fun getBusPositions(): Flow<List<BusPositionMarker>>

    @Query("SELECT * from response_obj_tbl")
    fun getResponses(): Flow<List<ResponseObj>>
//
//    @Query("SELECT * from bus_lines_tbl")
//    fun getBusLines(): Flow<List<BusLine>>

    @Query("DELETE from response_obj_tbl")
    fun deleteAllResponsesFromDB()

    @Query("SELECT * from bus_stop_tbl where id=:id")
    suspend fun findBusStopById(id: Int): BusStop

    @Query("SELECT * from bus_stop_tbl where name=:name")
    suspend fun findBusStopByNames(name: String): BusStop
//
//    @Insert(onConflict = REPLACE)
//    suspend fun addBusPos(busPosition: BusPositionMarker): Long
//
//    @Insert(onConflict = REPLACE)
//    suspend fun addBusStopPos(busStop: BusStop): Long
//
//    @Insert(onConflict = REPLACE)
//    suspend fun addBusLine(busLine: BusLine): Long?

    @Insert(onConflict = REPLACE)
    fun addResponseObj(data: ResponseObj?): Long?




}