package com.wagarcdev.deolhonobusao.presentention.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.domain.model.BusPositionMarker
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapEvent
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapState
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapStyle
import com.wagarcdev.deolhonobusao.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {


    /**TODO cache BusStops in Room then retrieve BusStopsMarkers from ROOM*/

    private val _state = mutableStateOf<MapState>(MapState())
    val state: State<MapState> = _state

    private var _busPositionMarkers = MutableStateFlow<List<BusPositionMarker>>(emptyList())
    val busPositionMarkers = _busPositionMarkers.asStateFlow()

    var busStopsMarkers = mutableStateOf<List<BusStop>>(listOf())



    init {
        viewModelScope.launch {

            launch { requestAuth() }.join()

//          launch(Dispatchers.Default) { refreshBusMarkers() }
//          launch(Dispatchers.Default) { autoUpdateMarkers(300) }

            launch(Dispatchers.Default) {
                busStopsMarkers.value = repository.getBusStops()
            }

        }
    }



    private suspend fun autoUpdateMarkers(seconds: Int) {

        var updateTime = seconds

        viewModelScope.launch {

            do {

                delay(1000)
                updateTime--

                when (updateTime) {
                     0 -> {
                         refreshBusMarkers()
                         updateTime = seconds
                    }
                }

            } while (true)
        }
    }


    private suspend fun refreshBusMarkers() {
        viewModelScope.launch(Dispatchers.Default) {

            repository.deleteAllBusMarkersPos()
        }

        viewModelScope.launch(Dispatchers.IO) {

            fetchBusPositions()
        }

        viewModelScope.launch(Dispatchers.Default) { fetchBusMarkersPos() }
    }


    private suspend fun requestAuth() {
        repository.postRequestAuthentication()
    }

    private suspend fun fetchBusPositions() {
        repository.getBusPositions().collect() { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        busPositions = resource.data,
                        isLoading = false
                    )

                    _state.value.busPositions?.l?.forEach { line ->
                        line.vs.forEach { vehicle ->
                            addBusPosition(
                                BusPositionMarker(
                                    prefix = vehicle.p,
                                    haveAcess = vehicle.a,
                                    infoTimestap = vehicle.ta,
                                    lat = vehicle.py,
                                    lng = vehicle.px
                                )
                            )
                        }

                    }
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        busPositions = resource.data,
                        isLoading = false
                    )
                    /** TODO Error handling */

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        busPositions = resource.data,
                        isLoading = true
                    )

                }

            }


        }
    }

    private suspend fun fetchBusMarkersPos() {
        repository.getBusMarkerPositions().collect { list ->
            _busPositionMarkers.value = list

        }
    }

    private suspend fun addBusPosition(busPosition: BusPositionMarker): Long? {

        var busPosId: Long? = null

        viewModelScope.launch {
           busPosId = repository.addBusPosition(busPosition)
        }.join()

        return busPosId
    }

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.ToggleNoLabelsMap -> {
                _state.value = _state.value.copy(
                    properties = _state.value.properties.copy(
                        mapStyleOptions =
                        if (_state.value.isNoLabelsMap) {
                            null
                        } else {
                            MapStyleOptions(MapStyle.noLabelMapJson)
                        }
                    ),
                    isNoLabelsMap = !_state.value.isNoLabelsMap
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