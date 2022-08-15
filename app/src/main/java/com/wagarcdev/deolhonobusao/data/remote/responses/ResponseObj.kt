package com.wagarcdev.deolhonobusao.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "response_obj_tbl")
data class ResponseObj(

    @PrimaryKey
    @SerializedName("hr") @Expose var timestamp: String,
    @SerializedName("l") @Expose var lines: List<Line>?
) {
    data class Line(

        @SerializedName("c") @Expose var fullDisplay: String?, // Letreiro completo
        @SerializedName("cl") @Expose var lineCode: Int?, // Código identificador da linha
        @SerializedName("sl") @Expose var way: Int?, //1 = Terminal Principal -> Secundário | 2 = Terminal Secundário -> Principal
        @SerializedName("lt0") @Expose var destinyDisplay: String?,// Letreiro de destino da linha
        @SerializedName("lti") @Expose var originDisplay: String?, // Letreiro de origem da linha
        @SerializedName("qv") @Expose var vehiclesLocated: Int?, //Quantidade de veículos localizados
        @SerializedName("vs") @Expose var vehicles: List<Vehicle>?// Relação de veículos localizados

    ) {
        data class Vehicle(

            @SerializedName("p") @Expose var vehiclePrefix: Int?, //Prefixo do veículo
            @SerializedName("a") @Expose var haveAccess: Boolean?, //se é acessível para pessoas com deficiência
            @SerializedName("ta") @Expose var vehicleTimestamp: String?, //info fetch timestamp
            @SerializedName("py") @Expose var lat: Double?, //latitude do veículo
            @SerializedName("px") @Expose var lng: Double?  //longitude do veiculo
        )
    }

}