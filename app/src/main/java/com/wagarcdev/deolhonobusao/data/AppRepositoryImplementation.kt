package com.wagarcdev.deolhonobusao.data

import android.util.Log
import androidx.lifecycle.Transformations.distinctUntilChanged
import com.wagarcdev.deolhonobusao.data.local.AppDatabaseDAO
import com.wagarcdev.deolhonobusao.data.remote.OlhoVivoAPI
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.data.remote.responses.ResponseObj
import com.wagarcdev.deolhonobusao.data.remote.responses.BusLine
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositionsFromLine
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException

class AppRepositoryImplementation(
    private val api: OlhoVivoAPI,
    private val dao: AppDatabaseDAO

): AppRepository {
    override suspend fun fetchLines(): Resource<List<BusLine>>  {

        val response = try {

            api.getAllBusLines()

        } catch (e: HttpException) {
            return Resource.Error("HTTP Exception error occurred. ERROR : $e ")
        } catch (e: IOException) {
            return Resource.Error("IO Exception error occurred. ERROR : $e ")
        }
//            response.forEach {
//                Log.i("FETCH LINES", it.linecode.toString())
//                Log.i("FETCH LINES", it.primaryTerminal)
//                Log.i("FETCH LINES", it.secondaryTerminal)
//                Log.i("FETCH LINES", "is circular ? ${it.isCircular}")
//            }
        return Resource.Success(response)
    }

    override suspend fun fetchBusPositionsFromLine(lineCode: Int): Resource<BusPositionsFromLine> {

        val response =  try {

            api.getBusFromLine(lineCode)

        } catch (e: HttpException) {
            return Resource.Error("HTTP Exception error occurred. ERROR : $e ")
        } catch (e: IOException) {
            return Resource.Error("IO Exception error occurred. ERROR : $e ")
        }
        return Resource.Success(response)
    }








    override suspend fun findBusStopById(id: Int): BusStop {
        return dao.findBusStopById(id)
    }

    override suspend fun findBusStopByNames(string: String): BusStop  {
        return dao.findBusStopByNames(string)
    }

//    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
//    override suspend fun getBusStops()
//     : Flow<Resource<List<BusStop>>> = flow {
//
//
//
//        do {
//            try {
//
////                GlobalScope.async { postRequestAuthentication() }.join()
//
//                val busStopPos = api.getAllBusStops()
//
//                busStopPos.forEach { busStop ->
//                    Log.i("GET_BUS_STOPS", "LIST BUS STOPS:  ")
//                    Log.i("GET_BUS_STOPS", " id = ${busStop.id} ")
//                    Log.i("GET_BUS_STOPS", " name = ${busStop.name} ")
//                    Log.i("GET_BUS_STOPS", " address = ${busStop.address} ")
//                    Log.i("GET_BUS_STOPS", " lat = ${busStop.lat} ")
//                    Log.i("GET_BUS_STOPS", " long = ${busStop.lng} ")
//                }
//                Log.i("GETBUSSTOPS", " TOTAL BUSTOPS FETCHED = ${busStopPos.size} ")
//                emit(Resource.Success(busStopPos))
//
//            } catch (e: HttpException) {
//                emit(Resource.Error(
//                    message = "Error : $e",
//                    data = null
//                ))
//
//            } catch (e: IOException) {
//                emit(Resource.Error(
//                    message = "Error : $e | Unable to reach Server, check you internet connection",
//                    data = null
//                ))
//            }
//
//            emit(Resource.Loading())
//
//            delay(12000)
//
//        }while (true)
//
//
//
//    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getResponse()
     : Flow<Resource<ResponseObj>> = flow {
        emit(Resource.Loading())

        try{

            GlobalScope.async { postRequestAuthentication() }.join()

            val responseObj = api.getResponse()

//                var vehiclesFound = 0
//
//                Log.i("GET_RESPONSE_OBJ", "${responseObj.timestamp} ")
//                responseObj.lines?.forEach { line ->
//                    Log.i("LINE", "LINE :${line.lineCode} - ${line.destinyDisplay} ")
//                    Log.i("LINE", "${line.fullDisplay} ")
//                    Log.i("LINE", "Vehicles found :${line.vehicles} ")
//                    Log.i("LINE", "Vehicles :")
//                    line.vehicles?.forEach { vehicle ->
//                        Log.i("VEHICLE", "VEHICLE :${vehicle.vehiclePrefix}")
//                        Log.i("VEHICLE", " Latitude: ${vehicle.lat} ")
//                        Log.i("VEHICLE", " Longitude: ${vehicle.lng} ")
//                        vehiclesFound++
//                    }
//                }
//                Log.i("LINE", "TOTAL Lines found :${responseObj.lines?.size} ")
//                Log.i("LINE", "TOTAL Vehicles found :${vehiclesFound} ")
//                Log.i("LINE", "Fetched at :${responseObj.timestamp} ")



            emit(Resource.Success(responseObj))

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

    override suspend fun getResponseFromDB(): Flow<List<ResponseObj>> {
        return dao.getResponses().conflate()
    }
//
//    override suspend fun getBusPositionsOnDB(): Flow<List<BusPositionMarker>> {
//        return  dao.getBusPositions().conflate()
//    }

    override suspend fun deleteAllResponsesFromDB() {
        dao.deleteAllResponsesFromDB()
    }

    override suspend fun postRequestAuthentication(): Resource<Boolean> {
        val response = try {
            api.postRequestAuth()
        } catch (e: Exception) {
            return Resource.Error("Error: $e")
        }
        return Resource.Success(response)
    }

//    override suspend fun addBusPosition(busPosition: BusPositionMarker): Long {
//        return dao.addBusPos(busPosition)
//    }
//
//    override suspend fun addBusStopPosition(busStop: BusStop): Long {
//        return dao.addBusStopPos(busStop)
//    }
////
//    override suspend fun addBusLine(busLine: BusLine): Long? {
//        return dao.addBusLine(busLine)
//    }
//
//    override suspend fun getBusLinesOnDB(): Flow<List<BusLine>> {
//        return dao.getBusLines().conflate()
//    }

    override fun addResponseObj(data: ResponseObj?): Long? {
        return dao.addResponseObj(data)
    }


}