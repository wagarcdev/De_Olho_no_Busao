package com.wagarcdev.deolhonobusao.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusLine(
    @SerializedName("cl") @Expose var linecode: Int,
    @SerializedName("lc") @Expose var isCircular: Boolean,
    @SerializedName("lt") @Expose var firstNumbers: String,
    @SerializedName("tl") @Expose var lastNumbers: Int,
    @SerializedName("sl") @Expose var way: Int, //1 Prim to Sec | 2 Sec to Prim
    @SerializedName("tp") @Expose var primaryTerminal: String,
    @SerializedName("ts") @Expose var secondaryTerminal: String
)