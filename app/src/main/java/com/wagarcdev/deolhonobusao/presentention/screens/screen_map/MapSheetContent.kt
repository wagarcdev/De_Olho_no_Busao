package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.wagarcdev.deolhonobusao.data.remote.responses.BusLine
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel
import com.wagarcdev.deolhonobusao.util.Resource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapSheetContent(
    mapViewModel: MapViewModel,
    sheetState: BottomSheetScaffoldState = mapViewModel.scaffoldState
) {

    val coroutineScope = rememberCoroutineScope()

    val response = mapViewModel.responseObj.collectAsState()




    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
    ) {


        when {
            response.value.isNotEmpty() -> {
                response.value.forEach {
                    it?.lines?.forEach { line ->
                        item {
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 8.dp),
                                onClick = {
                                    coroutineScope.launch {
                                        sheetState.bottomSheetState.collapse()
                                        mapViewModel.selectedLine.value = line
                                        mapViewModel.fetchBusPosFromLine(line.lineCode!!)
                                        mapViewModel.busPrimaryDisplay = line.destinyDisplay.toString()
                                        mapViewModel.busSecondaryDisplay = line.originDisplay.toString()
                                    }
                                }
                            ) {
                                Column() {
                                    Text(text = "${line.lineCode} - ${line.destinyDisplay}")
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "Veículos em circulação: ${line.vehiclesLocated.toString()}")

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}