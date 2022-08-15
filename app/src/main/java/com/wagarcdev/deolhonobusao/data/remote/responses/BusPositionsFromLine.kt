package com.wagarcdev.deolhonobusao.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusPositionsFromLine(
    @SerializedName("hr") @Expose var hr: String,
    @SerializedName("vs") @Expose var vs: List<ResponseObj.Line.Vehicle>
)