package com.wagarcdev.deolhonobusao.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lines_tbl")
data class LineEntity(

    @PrimaryKey
    var cl: Int,

    var lc: Boolean,
    var lt: String,
    var sl: Int,
    var tl: Int,
    var tp: String,
    var ts: String
)