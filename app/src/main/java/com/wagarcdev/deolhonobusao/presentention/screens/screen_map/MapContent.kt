package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.wagarcdev.deolhonobusao.R
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel

@Composable
fun MapContent(mapViewModel: MapViewModel) {

    val uiSettings = remember {
        MapUiSettings(
            compassEnabled = false,
            zoomControlsEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = true,
            rotationGesturesEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false,
            tiltGesturesEnabled = false
        )
    }

    val context = LocalContext.current

    val access = "ACESSIBILIDADE"

    val busFromLine = mapViewModel.busPosFromLine.collectAsState()

    var mapDisplay = mutableListOf(
        mapViewModel.busPrimaryWayCode to mapViewModel.busPrimaryDisplay ,
        mapViewModel.busSecondaryWayCode to mapViewModel.busSecondaryDisplay,
    )

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = mapViewModel.cameraPositionState,
            properties = mapViewModel.state.value.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                mapViewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            when {
                busFromLine.value != null -> {
                    busFromLine.value?.vs?.forEach { vehicle ->

                            val pos = vehicle.let { LatLng(it.lat!!, it.lng!!) }

                            val lineName = mapViewModel.selectedLine.value?.lineCode
                                .toString() + mapDisplay.filter { pair ->
                                pair.first == vehicle.vehiclePrefix
                            }.first().second

                            MapMarker(
                                context = context,
                                position = pos,
                                title = lineName,
                                snippet = if (vehicle.haveAccess == true) access else "",
                                rotation = 0f,
                                iconResourceId = R.drawable.ic_bus_gps
                            )
                    }
                }
            }
        }
    }
}


