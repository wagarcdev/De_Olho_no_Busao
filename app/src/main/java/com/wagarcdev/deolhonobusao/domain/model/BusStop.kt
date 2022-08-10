package com.wagarcdev.deolhonobusao.domain.model

data class BusStop(
    val id: Int,
    val name: String,
    val address: String,
    val lat: Double,
    val lng: Double
)