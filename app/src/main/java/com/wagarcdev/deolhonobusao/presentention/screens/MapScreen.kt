package com.wagarcdev.deolhonobusao.presentention.screens

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapContent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen() {

    val mapViewModel: MapViewModel = hiltViewModel()
    BottomSheetScaffold(
        topBar = {  },
        content = { MapContent(mapViewModel) },
        sheetContent = {  }
    )

}

