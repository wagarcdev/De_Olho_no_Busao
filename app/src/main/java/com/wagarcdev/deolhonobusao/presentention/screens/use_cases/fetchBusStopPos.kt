package com.wagarcdev.deolhonobusao.presentention.screens.use_cases

import com.wagarcdev.deolhonobusao.data.remote.responses.BusStop
import com.wagarcdev.deolhonobusao.domain.repository.AppRepository
import com.wagarcdev.deolhonobusao.presentention.screens.MapViewModel
import com.wagarcdev.deolhonobusao.util.Resource
import javax.inject.Inject

class fetchBusStopPos @Inject constructor(
    private val repository: AppRepository,
    private val mapViewModel: MapViewModel
) {

    suspend operator fun invoke(): List<BusStop> {
        return emptyList() // TODO
    }
}