package com.wagarcdev.deolhonobusao.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_stops_tbl")
data class BusStopEntity(

    @PrimaryKey
    val cp: Int,

    val np: String,
    val ed: String,
    val py: Double,
    val px: Double,
)
