package com.wagarcdev.deolhonobusao.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lines_tbl")
data class LineEntity(

    @PrimaryKey
    //Código identificador da linha
    var cl: Int,

    //Indica se uma linha opera no modo circular
    var lc: Boolean,

    // Informa a primeira parte do letreiro numérico da linha
    var lt: String,

    // Informa a segunda parte do letreiro numérico da linha / BASE (10)
    var sl: Int,

    //Informa o sentido ao qual a linha atende, onde 1 significa Terminal Principal para Terminal Secundário e 2 para Terminal Secundário para Terminal Principal
    var tl: Int,

    //Informa o letreiro descritivo da linha no sentido Terminal Principal para Terminal Secundário
    var tp: String,

    // Informa o letreiro descritivo da linha no sentido Terminal Secundário para Terminal Principal
    var ts: String
)