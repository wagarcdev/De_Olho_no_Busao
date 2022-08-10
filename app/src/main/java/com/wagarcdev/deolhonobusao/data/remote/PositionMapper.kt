package com.wagarcdev.deolhonobusao.data.remote

//
//fun BusPositionEntity.toPosition(): BusPosition {
//    return BusPosition(
//         hour = hr,
//         lines = listOf(BusPosition.L(
//             busCodeDisplay = BusPositionEntity.L.c,
//
//
//             var lineCode: Int, // Código identificador da linha
//             var direction: Int, //1 = Terminal Principal -> Secundário | 2 = Terminal Secundário -> Principal
//        var destiny: String,// Letreiro de destino da linha
//    var origin: String, // Letreiro de origem da linha
//    var vehiclesFound: Int, //Quantidade de veículos localizados
//    var vehiclesList: List<V>// Relação de veículos localizados
//         ))
//    ) {
//        data class L( //Linhas
//
//
//            var
//        ) {
//            data class V( // Veículos
//                var prefix: Int, //Prefixo do veículo
//                var haveAcess: Boolean, //se é acessível para pessoas com deficiência
//                var infoTimestap: String, //info fetch timestamp
//                var lat: Double, //latitude do veículo
//                var lng: Double  //longitude do veiculo
//            )
//        }
//    }
//    )
//}

//fun BusPosition.toPositionEntity(): BusPositionEntity