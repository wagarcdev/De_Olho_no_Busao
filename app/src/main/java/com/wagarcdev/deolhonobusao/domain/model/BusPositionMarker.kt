package com.wagarcdev.deolhonobusao.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_position_tbl")
data class BusPositionMarker( // Veículos
    @PrimaryKey
    var prefix: Int, //Prefixo do veículo

    var haveAcess: Boolean, //se é acessível para pessoas com deficiência
    var infoTimestap: String, //info fetch timestamp
    var lat: Double, //latitude do veículo
    var lng: Double  //longitude do veiculo
)


