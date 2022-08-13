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
                Log.i("GET_BUS_STOPS", "LIST BUS STOPS:  ")
                Log.i("GET_BUS_STOPS", " id = ${busStop.id} ")
                Log.i("GET_BUS_STOPS", " name = ${busStop.name} ")
                Log.i("GET_BUS_STOPS", " address = ${busStop.address} ")
                Log.i("GET_BUS_STOPS", " lat = ${busStop.lat} ")
                Log.i("GET_BUS_STOPS", " long = ${busStop.lng} ")
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
//            busPositions.lines.forEach { line ->
//                line.vehicles.forEach { vehicle ->
//                    Log.i("GET_BUS_POSITIONS", "LIST VEHICLES:  ")
//                    Log.i("GET_BUS_POSITIONS", "vehicle: ${vehicle.vehiclePrefix}  ")
//                    Log.i("GET_BUS_POSITIONS", "last updated: ${vehicle.vehicleTimestamp}  ")
//                    Log.i("GET_BUS_POSITIONS", "lat: ${vehicle.lat}  ")
//                    Log.i("GET_BUS_POSITIONS", "long: ${vehicle.lng}  ")
//                }
//            }
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