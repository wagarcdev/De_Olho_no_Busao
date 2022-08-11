package com.wagarcdev.deolhonobusao.data.remote

import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.util.Constants.AUTHENTICATION
import com.wagarcdev.deolhonobusao.util.Constants.POSITIONS
import com.wagarcdev.deolhonobusao.util.Constants.BUS_STOPS
import retrofit2.http.GET
import retrofit2.http.POST

interface OlhoVivoAPI {

    @POST(AUTHENTICATION)
    suspend fun postRequestAuth(): Boolean

    @GET(POSITIONS)
    suspend fun getBusPositions(): BusPositions


    @GET(BUS_STOPS)
    suspend fun getAllBusStops(): List<BusStop>


}