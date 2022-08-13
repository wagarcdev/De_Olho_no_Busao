package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.wagarcdev.deolhonobusao.R
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel

@Composable
fun MapContent(mapViewModel: MapViewModel) {

    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

    val context = LocalContext.current

    val busPosMarkerList = mapViewModel.busPosMarkerList.collectAsState()

    val access = "ACESSIBILIDADE"

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(-23.5475, -46.63611),
            12f
        )
    }

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapViewModel.state.value.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                mapViewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            val showMarkers = remember { mutableStateOf(false) }

            when {
                cameraPositionState.position.zoom > 13f -> { showMarkers.value = true }

                cameraPositionState.position.zoom < 13f -> { showMarkers.value = false }
            }
            mapViewModel.state.value.busStops?.forEach{ busStopsMarkers ->

                Marker(
                    visible = showMarkers.value

                )



            }
        }
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {

            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bus_gps),
                contentDescription = "marker",
            )
        }

        Text(text = "Is camera moving: ${cameraPositionState.isMoving}" +
                "\n left lat ${cameraPositionState.projection?.visibleRegion?.farLeft?.longitude}" +
                "\n left long ${cameraPositionState.projection?.visibleRegion?.farLeft?.latitude}" +
                "\n Latitude and Longitude: ${cameraPositionState.position.target.latitude} " +
                "and ${cameraPositionState.position.target.longitude}",
            textAlign = TextAlign.Center
        )
    }


//        busPosMarkerList.value.forEach { busPos ->
//            Marker(
//                state = MarkerState(LatLng(busPos.lat, busPos.lng)),
//                title = busPos.prefix.toString(),
//                snippet = if (busPos.haveAcess) access else ""
//            )
//        }


}

