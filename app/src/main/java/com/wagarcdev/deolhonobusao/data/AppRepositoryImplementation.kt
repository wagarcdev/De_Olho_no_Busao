package com.wagarcdev.deolhonobusao.data

import com.wagarcdev.deolhonobusao.data.local.AppDatabaseDAO
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStopEntity
import com.wagarcdev.deolhonobusao.domain.model.BusStop
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepositoryImplementation(
    private val dao: AppDatabaseDAO
): AppRepository {

    override suspend fun findBusStopById(id: Int): BusStop {
        return dao.findBusStopById(id).toBusTop()
    }

    override suspend fun findBusStopByNames(string: String): BusStop  {
        return dao.findBusStopByNames(string).toBusTop()
    }

    override fun getBusStops(): Flow<List<BusStop>> {
        return  dao.getBusStops().map { busStopEntities ->
            busStopEntities.map { it.toBusTop() }
        }
    }
}