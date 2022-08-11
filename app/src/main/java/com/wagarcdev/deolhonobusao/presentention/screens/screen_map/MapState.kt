package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import com.google.maps.android.compose.MapProperties
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop

data class MapState(
    val properties: MapProperties = MapProperties(),
    val isNoLabelsMap: Boolean = false,
    val busStops: List<BusStop> = emptyList(),
    val busPositions: BusPositions? = null,
    val isLoading: Boolean = false
)