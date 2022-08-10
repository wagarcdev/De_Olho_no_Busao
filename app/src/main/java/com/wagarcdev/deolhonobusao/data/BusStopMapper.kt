package com.wagarcdev.deolhonobusao.data

import com.wagarcdev.deolhonobusao.data.remote.responses.BusStopEntity
import com.wagarcdev.deolhonobusao.domain.model.BusStop

fun BusStopEntity.toBusTop(): BusStop {
    return BusStop(
        id = cp,
        name = np,
        address = ed,
        lat = py,
        lng = px
    )
}

fun BusStop.toBusStopEntity(): BusStopEntity {
    return BusStopEntity(
        cp = id,
        np = name,
        ed = address,
        py = lat,
        px = lng
    )
}