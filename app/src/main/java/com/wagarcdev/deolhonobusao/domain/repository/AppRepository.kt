package com.wagarcdev.deolhonobusao.domain.repository

import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.domain.model.BusMarkerPosition
import com.wagarcdev.deolhonobusao.domain.model.BusStop
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun findBusStopById(id: Int): BusStop

    suspend fun findBusStopByNames(string: String): BusStop

    suspend fun getBusStops(): Flow<List<BusStop>>

    suspend fun getBusPositions(): Flow<Resource<BusPositions>>

    suspend fun getBusMarkerPositions(): Flow<List<BusMarkerPosition>>

    suspend fun postRequestAuthentication(): Resource<Boolean>

    suspend fun addBusPosition(busPosition: BusMarkerPosition): Long
}