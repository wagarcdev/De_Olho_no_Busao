package com.wagarcdev.deolhonobusao.domain.repository

import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.data.remote.responses.ResponseObj
import com.wagarcdev.deolhonobusao.data.remote.responses.BusLine
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositionsFromLine
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun fetchLines(): Resource<List<BusLine>>

    suspend fun fetchBusPositionsFromLine(lineCode: Int):  Resource<BusPositionsFromLine>

    suspend fun findBusStopById(id: Int): BusStop

    suspend fun findBusStopByNames(string: String): BusStop

//    suspend fun getBusStops(): Flow<Resource<List<BusStop>>> //*

    suspend fun getResponse(): Flow<Resource<ResponseObj>> //*

    suspend fun getResponseFromDB(): Flow<List<ResponseObj>> //*

//    suspend fun getBusPositionsOnDB(): Flow<List<BusPositionMarker>>

    suspend fun deleteAllResponsesFromDB()

    suspend fun postRequestAuthentication(): Resource<Boolean>

//    suspend fun addBusPosition(busPosition: BusPositionMarker): Long
//
//    suspend fun addBusStopPosition(busStop: BusStop): Long
//
//    suspend fun addBusLine(busLine: BusLine): Long?
//
//    suspend fun getBusLinesOnDB(): Flow<List<BusLine>>

    fun addResponseObj(data: ResponseObj?): Long?
}