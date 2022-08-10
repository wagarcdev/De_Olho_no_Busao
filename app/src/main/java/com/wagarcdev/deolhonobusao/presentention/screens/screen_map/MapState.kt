package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import com.google.maps.android.compose.MapProperties
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStopEntity
import com.wagarcdev.deolhonobusao.domain.model.BusStop

data class MapState(
    val properties: MapProperties = MapProperties(),
    val isNoLabelsMap: Boolean = false,
    val busStops: List<BusStop> = emptyList()
)