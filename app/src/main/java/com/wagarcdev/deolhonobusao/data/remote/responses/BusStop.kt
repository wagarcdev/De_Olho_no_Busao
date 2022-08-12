package com.wagarcdev.deolhonobusao.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bus_stop_tbl")
data class BusStop(

    @PrimaryKey
    @SerializedName("cp") @Expose var id: Int,

    @SerializedName("np") @Expose var name: String,
    @SerializedName("ed") @Expose var address: String,
    @SerializedName("py") @Expose var lat: Double,
    @SerializedName("px") @Expose var lng: Double
)

