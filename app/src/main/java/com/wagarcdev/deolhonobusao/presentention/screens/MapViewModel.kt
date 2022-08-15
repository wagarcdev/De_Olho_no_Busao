package com.wagarcdev.deolhonobusao.presentention.screens

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.wagarcdev.deolhonobusao.data.remote.responses.ResponseObj
import com.wagarcdev.deolhonobusao.data.remote.responses.BusLine
import com.wagarcdev.deolhonobusao.data.remote.responses.BusPositionsFromLine
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapEvent
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapState
import com.wagarcdev.deolhonobusao.presentention.screens.screen_map.MapStyle
import com.wagarcdev.deolhonobusao.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import javax.inject.Inject

@OptIn(ExperimentalMaterialApi::class)
@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _state = mutableStateOf<MapState>(MapState())
    val state: State<MapState> = _state

    private var _responseObj = MutableStateFlow<List<ResponseObj?>>(emptyList())
    val responseObj: StateFlow<List<ResponseObj?>> = _responseObj.asStateFlow()


    private var _busLines = MutableStateFlow<List<BusLine?>>(emptyList())
    val busLines: StateFlow<List<BusLine?>> = _busLines.asStateFlow()

    private var _busPosFromLine = MutableStateFlow<BusPositionsFromLine?>(null)
    val busPosFromLine: StateFlow<BusPositionsFromLine?> = _busPosFromLine.asStateFlow()

    var busPrimaryDisplay: String = ""
    var busSecondaryDisplay: String = ""

    var busPrimaryWayCode: Int? = null
    var busSecondaryWayCode: Int? = null




//
//    private var _busPosMarkers = MutableStateFlow<List<BusPositionMarker?>>(emptyList())
//    val busPosMarkers: StateFlow<List<BusPositionMarker?>> = _busPosMarkers.asStateFlow()
//
//    private var _busLines = MutableStateFlow<List<BusLine?>>(emptyList())
//    val busLines: StateFlow<List<BusLine?>> = _busLines.asStateFlow()

    var selectedLine = mutableStateOf<ResponseObj.Line?>(null)

    var cameraPositionState = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(
            LatLng(-23.5475, -46.63611),
            12f
        )
    )

    @OptIn(ExperimentalMaterialApi::class)
    val scaffoldState: BottomSheetScaffoldState = BottomSheetScaffoldState(
        drawerState = DrawerState(initialValue = DrawerValue.Open),
        bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Expanded),
        snackbarHostState = SnackbarHostState()
    )




    init {
        viewModelScope.launch {

            launch(Dispatchers.IO) { requestAuth() }.join()

//          launch(Dispatchers.Default) { refreshBusMarkers() }
//          launch(Dispatchers.Default) { autoUpdateMarkers(300) }

            launch(Dispatchers.Default) { refreshResponse() }


        }
    }

    suspend fun fetchLines(): Resource<List<BusLine>> {
        return repository.fetchLines()
    }

//        viewModelScope.launch(Dispatchers.IO) {
//            when (val resource = repository.fetchLines()) {
//                is Resource.Success -> {
//                    _busLines.value = resource.data!!
//                }
//                is Resource.Error -> {
//                    _state.value = _state.value.copy(
//                        isLoading = false
//                    )
//                    /** TODO Error handling */
//
//                }
//                is Resource.Loading -> {
//                    _state.value = _state.value.copy(
//                        isLoading = true
//                    )
//
//                }
//            }
//
//        }









//    private suspend fun getBusPositionsFromDB() {
//        repository.getBusPositionsOnDB().distinctUntilChanged().collect() { listOfMarkers ->
//            if (listOfMarkers.isEmpty()) {
//                Log.d("GETBUSMARKERSONDB", "EMPTY LIST OF BUS MARKERS ")
//            } else {
//                _busPosMarkers.value = listOfMarkers
//            }
//
//        }
//    }

//    private suspend fun getBusLinesFromDB() {
//        repository.getBusLinesOnDB().distinctUntilChanged().collect() { busLines ->
//            if (busLines.isEmpty()) {
//                Log.d("GETBUSLINESONDB", "EMPTY LIST OF BUS LINES ")
//            } else {
//                _busLines.value = busLines
//            }
//
//        }
//    }


//    private suspend fun fetchBusStopPositions() {
//        repository.getBusStops().distinctUntilChanged().collect() { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    _state.value = _state.value.copy(
//                        isLoading = false
//                    )
//
//                    /**Saves busStops position data on Local DB*/
//                   resource.data?.forEach { busStop ->
//                        addBusStopPosition(
//                            BusStop(
//                                id = busStop.id,
//                                address = busStop.name ,
//                                name = busStop.address ,
//                                lng = busStop.lat,
//                                lat = busStop.lng
//                            )
//                        )
//                    }
//
//
//                }
//
//                is Resource.Error -> {
//                    _state.value = _state.value.copy(
//                        isLoading = false
//                    )
//
//                    /** TODO Error handling */
//
//                }
//                is Resource.Loading -> {
//                    _state.value = _state.value.copy(
//                        isLoading = true
//                    )
//
//                }
//
//            }
//
//
//        }
//    }


    private suspend fun fetchAndStoreResponse() {
        repository.getResponse().distinctUntilChanged().collect() { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    viewModelScope.launch(Dispatchers.Default) {

                        addResponseObjToDB(resource.data)

                        var vehiclesFound = 0

                        resource.data?.lines?.forEach { line ->
                            Log.i("LINE", "LINE :${line.lineCode} - ${line.destinyDisplay} ")
                            Log.i("LINE", "${line.fullDisplay} ")
                            Log.i("LINE", "Vehicles found :${line.vehicles?.size} ")
                            Log.i("LINE", "Vehicles :")
                            line.vehicles?.forEach { vehicle ->
                                Log.i("VEHICLE", "VEHICLE :${vehicle.vehiclePrefix}")
                                Log.i("VEHICLE", " Latitude: ${vehicle.lat} ")
                                Log.i("VEHICLE", " Longitude: ${vehicle.lng} ")
                                vehiclesFound++
                            }
                        }
                        Log.i("LINE", "TOTAL Lines found :${resource.data?.lines?.size} ")
                        Log.i("LINE", "TOTAL Vehicles found :${vehiclesFound} ")
                        Log.i("LINE", "Fetched at :${resource.data?.timestamp} ")

                    }

                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    /** TODO Error handling */

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )

                }

            }


        }
    }

    private suspend fun getResponseStored() {
        viewModelScope.launch {
            repository.getResponseFromDB().distinctUntilChanged().collect() { responseObj ->
                _responseObj.value = responseObj

            }
        }
    }

    private suspend fun addResponseObjToDB(data: ResponseObj?): Long? {

        var responseID: Long? = null

        viewModelScope.launch(Dispatchers.Default) {

            responseID = repository.addResponseObj(data)

        }.join()

        return responseID

    }

//    private suspend fun addBusLine(busLine: BusLine): Long? {
//        var busLineId: Long? = null
//
//        viewModelScope.launch {
//            busLineId = repository.addBusLine(busLine)
//        }.join()
//
//        return busLineId
//
//    }


    private suspend fun autoUpdateResponse(seconds: Int) {

        var updateTime = seconds

        viewModelScope.launch {

            do {

                delay(1000)
                updateTime--

                when (updateTime) {
                     0 -> {
                         refreshResponse()
                         updateTime = seconds
                    }
                }

            } while (true)
        }
    }


    private suspend fun refreshResponse() {
        viewModelScope.launch(Dispatchers.Default) {

            repository.deleteAllResponsesFromDB()
        }.join()

        do {
            viewModelScope.launch(Dispatchers.IO) {

                fetchAndStoreResponse()
            }.join()

            viewModelScope.launch(Dispatchers.Default) {

                getResponseStored()

            }

            delay(120000)
        } while (true)

    }

//    private suspend fun getBusPositions() {
//
//        viewModelScope.launch(Dispatchers.IO) {
//
//            repository.getBusPositionsOnDB().distinctUntilChanged().collect { listOfBusPos ->
//                if (listOfBusPos.isEmpty()) {
//                    Log.d("CATALOG", "EMPTY LIST OF RECIPES ")
//                } else {
//                    _busPosMarkers.value = listOfBusPos
//                }
//            }
//        }
//    }


    private suspend fun requestAuth() {
        repository.postRequestAuthentication()
    }

//    private suspend fun addBusPosition(busPosition: BusPositionMarker): Long? {
//
//        var busPosId: Long? = null
//
//        viewModelScope.launch {
//           busPosId = repository.addBusPosition(busPosition)
//        }.join()
//
//        return busPosId
//    }

//    private suspend fun addBusStopPosition(busStop: BusStop): Long? {
//
//        var busPosId: Long? = null
//
//        viewModelScope.launch {
//            busPosId = repository.addBusStopPosition(busStop)
//        }.join()
//
//        return busPosId
//    }



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

    suspend fun fetchBusPosFromLine(lineCode: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val resource = repository.fetchBusPositionsFromLine(lineCode)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _busPosFromLine.value = resource.data
                    cameraPositionState = CameraPositionState(
                        position = CameraPosition.fromLatLngZoom(
                            LatLng(resource.data!!.vs[0].lat!!, resource.data.vs[0].lng!! ),
                            12f
                        )
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    /** TODO Error handling */

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )

                }

            }

        }

    }

}


