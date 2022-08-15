package com.wagarcdev.deolhonobusao.presentention.screens.screen_map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapTopBar(
    mapViewModel: MapViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    coroutineScope.launch {
                        mapViewModel.scaffoldState.bottomSheetState.expand()
                    }
                },
            imageVector = Icons.Rounded.Search,
            contentDescription = "Search Icon"
        )

    }
}