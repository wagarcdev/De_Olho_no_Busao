package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.domain.model.BusMarkerPosition
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel

@Composable
fun MapContent(mapViewModel: MapViewModel) {

    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

    val busMarkersPosTEST = mapViewModel.busMarkersPositionsList.collectAsState().value

//    val busMarkersPos = remember { mapViewModel.busMarkersPos }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapViewModel.state.value.properties,
        uiSettings = uiSettings,
        onMapLongClick = {
            mapViewModel.onEvent(MapEvent.OnMapLongClick(it))
        }
    ) {



        busMarkersPosTEST.forEach{ busMarkerPos ->
            Marker(
                state = MarkerState(
                    position = LatLng(busMarkerPos.lat, busMarkerPos.lng)
                ),
                title = "${busMarkerPos.prefix}",
                snippet = "test",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_RED
                )
            )

        }

//        mapViewModel.state.value.busStops.forEach { busStop ->
//            Marker(
//                position = LatLng(busStop.lat, busStop.lng),
//                title = "Map busStop (${busStop.lat}, ${busStop.lng})",
//                snippet = "test",
//                onInfoWindowLongClick = {
//                    mapViewModel.onEvent(MapEvent.OnInfoWindowClick(busStop))
//                },
//                onClick = {
//                    it.showInfoWindow()
//                    true
//                },
//                icon = BitmapDescriptorFactory.defaultMarker(
//                    BitmapDescriptorFactory.HUE_RED
//                )
//            )
//
//        }
    }

}