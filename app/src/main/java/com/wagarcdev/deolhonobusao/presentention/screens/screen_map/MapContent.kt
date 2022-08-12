package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.wagarcdev.deolhonobusao.R
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel

@Composable
fun MapContent(mapViewModel: MapViewModel) {

    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

    val context = LocalContext.current



//    val busMarkersPos = mapViewModel.busPositionMarkers.collectAsState().value
//
//    val busStopsMarkers = mapViewModel.busStopsMarkers

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapViewModel.state.value.properties,
        uiSettings = uiSettings,
        onMapLongClick = {
            mapViewModel.onEvent(MapEvent.OnMapLongClick(it))
        }
    ) {
        mapViewModel.state.value.busStops?.forEach{ busStopsMarkers ->

            MapMarker(
                context,
                LatLng(busStopsMarkers.lat, busStopsMarkers.lng),
                title = busStopsMarkers.name,
                snippet = busStopsMarkers.address,
                rotation = 0f,
                R.drawable.ic_ponto_onibus
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