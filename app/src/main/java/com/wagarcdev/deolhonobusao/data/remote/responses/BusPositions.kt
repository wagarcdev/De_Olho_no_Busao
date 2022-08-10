package com.wagarcdev.deolhonobusao.data.remote.responses

import androidx.room.Entity

data class BusPositions(
    // Horário de referência da geração das informações
    var hr: String,

    // Relação de linhas
    var l: List<L>
) {
    data class L( //Linhas


        var c: String, // Letreiro completo
        var cl: Int, // Código identificador da linha
        var sl: Int, //1 = Terminal Principal -> Secundário | 2 = Terminal Secundário -> Principal
        var lt0: String,// Letreiro de destino da linha
        var lt1: String, // Letreiro de origem da linha
        var qv: Int, //Quantidade de veículos localizados
        var vs: List<V>// Relação de veículos localizados
    ) {
        data class V( // Veículos
            var p: Int, //Prefixo do veículo
            var a: Boolean, //se é acessível para pessoas com deficiência
            var ta: String, //info fetch timestamp
            var py: Double, //latitude do veículo
            var px: Double  //longitude do veiculo
        )
    }
}