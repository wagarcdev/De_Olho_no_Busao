package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel

@Composable
fun MapContent(mapViewModel: MapViewModel) {

    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapViewModel.state.properties,
        uiSettings = uiSettings,
        onMapLongClick = {
            mapViewModel.onEvent(MapEvent.OnMapLongClick(it))
        }
    ) {
        mapViewModel.state.busStops.forEach { busStop ->
            Marker(
                position = LatLng(busStop.lat, busStop.lng),
                title = "Map busStop (${busStop.lat}, ${busStop.lng})",
                snippet = "Long click to delete",
                onInfoWindowLongClick = {
                    mapViewModel.onEvent(MapEvent.OnInfoWindowClick(busStop))
                },
                onClick = {
                    it.showInfoWindow()
                    true
                },
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_RED
                )
            )

        }
    }

}