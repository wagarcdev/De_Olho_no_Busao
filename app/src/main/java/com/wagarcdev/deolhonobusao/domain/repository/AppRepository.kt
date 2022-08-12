package com.wagarcdev.deolhonobusao.domain.repository

import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.domain.model.BusPositionMarker
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun findBusStopById(id: Int): BusStop

    suspend fun findBusStopByNames(string: String): BusStop

    suspend fun getBusStops(): Flow<Resource<List<BusStop>>>

    suspend fun getBusPositions(): Flow<Resource<BusPositions>>

    suspend fun getBusMarkerPositions(): Flow<List<BusPositionMarker>>

    suspend fun deleteAllBusMarkersPos()

    suspend fun postRequestAuthentication(): Resource<Boolean>

    suspend fun addBusPosition(busPosition: BusPositionMarker): Long

    suspend fun addBusStopPosition(busStop: BusStop): Long
}