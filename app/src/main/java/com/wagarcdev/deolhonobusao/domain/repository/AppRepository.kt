package com.wagarcdev.deolhonobusao.domain.repository

import com.wagarcdev.deolhonobusao.domain.model.BusStop
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun findBusStopById(id: Int): BusStop

    suspend fun findBusStopByNames(string: String): BusStop

    fun getBusStops(): Flow<List<BusStop>>

}