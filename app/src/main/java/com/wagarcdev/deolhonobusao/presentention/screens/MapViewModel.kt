package com.wagarcdev.deolhonobusao.presentention.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapEvent
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapState
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())

    init {
        viewModelScope.launch {
            repository.getBusStops().collectLatest { listOfStops ->
                state = state.copy(
                    busStops = listOfStops
                )
            }
        }
    }

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.ToggleNoLabelsMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions =
                        if (state.isNoLabelsMap) {
                            null
                        } else {
                            MapStyleOptions(MapStyle.noLabelMapJson)
                        }
                    ),
                    isNoLabelsMap = !state.isNoLabelsMap
                )

            }

            is MapEvent.OnMapClick -> {
                viewModelScope.launch {

                }
            }

            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {

                }
            }

            is MapEvent.OnInfoWindowClick -> {
                viewModelScope.launch {

                }
            }


        }
    }
}