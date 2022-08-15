package com.wagarcdev.deolhonobusao.data.remote

import com.wagarcdev.deolhonobusao.data.remote.responses.ResponseObj
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.data.remote.responses.BusLine
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositionsFromLine
import com.wagarcdev.deolhonobusao.util.Constants.AUTHENTICATION
import com.wagarcdev.deolhonobusao.util.Constants.BUS_LINES
import com.wagarcdev.deolhonobusao.util.Constants.POSITIONS
import com.wagarcdev.deolhonobusao.util.Constants.BUS_STOPS
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OlhoVivoAPI {

    @POST(AUTHENTICATION)
    suspend fun postRequestAuth(): Boolean

    @GET(POSITIONS)
    suspend fun getResponse(): ResponseObj



    @GET(BUS_STOPS)
    suspend fun getAllBusStops(): MutableList<BusStop>


    @GET(BUS_LINES)
    suspend fun getAllBusLines(): List<BusLine>

    @GET(BUS_LINES + "{Line}")
    suspend fun getBusFromLine(@Path("Line") line: Int): BusPositionsFromLine



}