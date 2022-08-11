package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import com.google.android.gms.maps.model.LatLng
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop

sealed class MapEvent {
    object ToggleNoLabelsMap: MapEvent()
    data class OnMapClick(val latlng: LatLng): MapEvent()
    data class OnMapLongClick(val latlng: LatLng): MapEvent()
    data class OnInfoWindowClick(val busStop: BusStop): MapEvent()
}
