package com.wagarcdev.deolhonobusao.data.remote

import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.util.Constants.AUTH_TOKEN
import retrofit2.http.GET
import retrofit2.http.POST

interface OlhoVivoAPI {

    @POST("Login/Autenticar?token=$AUTH_TOKEN")
    suspend fun postRequestAuth(): Boolean

    @GET("Posicao")
    suspend fun getBusPositions(): BusPositions


}