package com.wagarcdev.deolhonobusao.data

import com.wagarcdev.deolhonobusao.data.local.AppDatabaseDAO
import com.wagarcdev.deolhonobusao.data.remote.OlhoVivoAPI
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.domain.model.BusMarkerPosition
import com.wagarcdev.deolhonobusao.domain.model.BusStop
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class AppRepositoryImplementation(
    private val api: OlhoVivoAPI,
    private val dao: AppDatabaseDAO

): AppRepository {

    override suspend fun findBusStopById(id: Int): BusStop {
        return dao.findBusStopById(id).toBusTop()
    }

    override suspend fun findBusStopByNames(string: String): BusStop  {
        return dao.findBusStopByNames(string).toBusTop()
    }

    override suspend fun getBusStops(): Flow<List<BusStop>> {
        return  dao.getBusStops().map { busStopEntities ->
            busStopEntities.map { it.toBusTop() }
        }
    }

    override suspend fun getBusPositions()
     : Flow<Resource<BusPositions>> = flow {
        emit(Resource.Loading())

        try{
            val busPositions = api.getBusPositions()
            emit(Resource.Success(busPositions))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Error : $e",
                data = null
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Error : $e | Unable to reach Server, check you internet connection",
                data = null
            ))
        }
    }

    override suspend fun getBusMarkerPositions(): Flow<List<BusMarkerPosition>> {
        return  dao.getBusPositions().conflate()
    }

    override suspend fun postRequestAuthentication(): Resource<Boolean> {
        val response = try {
            api.postRequestAuth()
        } catch (e: Exception) {
            return Resource.Error("Error: $e")
        }
        return Resource.Success(response)
    }

    override suspend fun addBusPosition(busPosition: BusMarkerPosition): Long {
        return dao.addBusPos(busPosition)
    }
}