package com.wagarcdev.deolhonobusao.presentention.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapContent
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapSheetContent
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapTopBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen() {

    val mapViewModel: MapViewModel = hiltViewModel()

    BottomSheetScaffold(
        scaffoldState = mapViewModel.scaffoldState,
        sheetPeekHeight = 0.dp,
        topBar = { MapTopBar(mapViewModel) },
        content = { MapContent(mapViewModel) },
        sheetContent = { MapSheetContent(mapViewModel) }
    )

}

