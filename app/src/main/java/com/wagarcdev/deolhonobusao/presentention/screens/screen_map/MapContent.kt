package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel

@Composable
fun MapContent(mapViewModel: MapViewModel) {

    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

    val busMarkersPos = mapViewModel.busPositionMarkers.collectAsState().value

    val busStopsMarkers = mapViewModel.busStopsMarkers.value

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapViewModel.state.value.properties,
        uiSettings = uiSettings,
        onMapLongClick = {
            mapViewModel.onEvent(MapEvent.OnMapLongClick(it))
        }
    ) {



        busStopsMarkers.forEach{ busStopsMarkers ->
            Marker(
                state = MarkerState(
                    position = LatLng(busStopsMarkers.lat, busStopsMarkers.lng)
                ),
                title = busStopsMarkers.name,
                snippet = busStopsMarkers.address,
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