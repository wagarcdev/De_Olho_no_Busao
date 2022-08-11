package com.wagarcdev.deolhonobusao.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_stops_tbl")
data class BusStop(

    @PrimaryKey
    val id: Int,

    val name: String,
    val address: String,
    val lat: Double,
    val lng: Double
)
