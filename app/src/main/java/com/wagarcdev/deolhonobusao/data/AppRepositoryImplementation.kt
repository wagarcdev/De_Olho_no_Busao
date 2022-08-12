package com.wagarcdev.deolhonobusao.data

import android.util.Log
import com.wagarcdev.deolhonobusao.data.local.AppDatabaseDAO
import com.wagarcdev.deolhonobusao.data.remote.OlhoVivoAPI
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.domain.model.BusPositionMarker
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AppRepositoryImplementation(
    private val api: OlhoVivoAPI,
    private val dao: AppDatabaseDAO

): AppRepository {

    override suspend fun findBusStopById(id: Int): BusStop {
        return dao.findBusStopById(id)
    }

    override suspend fun findBusStopByNames(string: String): BusStop  {
        return dao.findBusStopByNames(string)
    }

    override suspend fun getBusStops()
     : Flow<Resource<List<BusStop>>> = flow {
        emit(Resource.Loading())

        try {
            val busStopPos = api.getAllBusStops()

            busStopPos.forEach { busStop ->
                Log.i("GETBUSSTOPS", "LIST BUS STOPS:  ")
                Log.i("GETBUSSTOPS", " id = ${busStop.id} ")
                Log.i("GETBUSSTOPS", " name = ${busStop.name} ")
                Log.i("GETBUSSTOPS", " address = ${busStop.address} ")
                Log.i("GETBUSSTOPS", " lat = ${busStop.lat} ")
                Log.i("GETBUSSTOPS", " long = ${busStop.lng} ")
            }
            Log.i("GETBUSSTOPS", " TOTAL BUSTOPS FETCHED = ${busStopPos.size} ")
            emit(Resource.Success(busStopPos))
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

    override suspend fun getBusMarkerPositions(): Flow<List<BusPositionMarker>> {
        return  dao.getBusPositions().conflate()
    }

    override suspend fun deleteAllBusMarkersPos() {
        dao.deleteAllBusPositions()
    }

    override suspend fun postRequestAuthentication(): Resource<Boolean> {
        val response = try {
            api.postRequestAuth()
        } catch (e: Exception) {
            return Resource.Error("Error: $e")
        }
        return Resource.Success(response)
    }

    override suspend fun addBusPosition(busPosition: BusPositionMarker): Long {
        return dao.addBusPos(busPosition)
    }

    override suspend fun addBusStopPosition(busStop: BusStop): Long {
        return dao.addBusStopPos(busStop)
    }


}