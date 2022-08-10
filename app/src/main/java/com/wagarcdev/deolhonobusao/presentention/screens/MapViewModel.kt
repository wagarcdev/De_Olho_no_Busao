package com.wagarcdev.deolhonobusao.presentention.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositions
import com.wagarcdev.deolhonobusao.domain.model.BusMarkerPosition
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapEvent
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapState
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapStyle
import com.wagarcdev.deolhonobusao.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _state = mutableStateOf<MapState>(MapState())
    val state: State<MapState> = _state

    private val _busPositions = mutableStateOf<BusPositions?>(null)
    val busPositions: State<BusPositions?> = _busPositions

    private val _busPosIdList = mutableListOf<Long>()
    val busPosIdList: MutableList<Long> = _busPosIdList

    private var _busMarkersPos = mutableListOf<BusMarkerPosition>()
    val busMarkersPos: MutableList<BusMarkerPosition> = _busMarkersPos

    init {
        viewModelScope.launch {

            launch { repository.postRequestAuthentication()
            }.join()

            repository.getBusPositions().onEach { resource ->
                when(resource) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            busPositions = resource.data,
                            isLoading = false
                        )

                        _state.value.busPositions?.l?.forEach{ line ->
                            line.vs.forEach { vehicle ->
                                addBusPosition(
                                    BusMarkerPosition(
                                        prefix = vehicle.p,
                                        haveAcess = vehicle.a,
                                        infoTimestap = vehicle.ta,
                                        lat = vehicle.py,
                                        lng = vehicle.px
                                    )
                                )?.let {
                                    _busPosIdList.add( it )
                                }
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

            repository.getBusMarkerPositions().distinctUntilChanged().collect { list ->
                _busMarkersPos = list as MutableList<BusMarkerPosition>

            }
        }
    }

    suspend fun getBusMarkersPos(): List<BusMarkerPosition> {

        val listOfMarkers: MutableList<BusMarkerPosition> = arrayListOf()

        viewModelScope.launch {
            repository.getBusMarkerPositions().collectLatest { list ->

                list.forEach{ busMarkerPos ->
                    listOfMarkers.add(busMarkerPos)

                }
            }
        }.join()

        return listOfMarkers
    }

    suspend fun addBusPosition(busPosition: BusMarkerPosition): Long? {

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