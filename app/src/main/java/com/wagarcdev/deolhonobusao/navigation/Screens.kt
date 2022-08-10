package com.wagarcdev.deolhonobusao.navigation

enum class Screens {

    MapScreen;

    companion object {
        fun fromRoute(route: String?): Screens
        = when (route?.substringBefore("/")) {
            MapScreen.name -> MapScreen
            null -> MapScreen
            else -> throw IllegalArgumentException("Route $route was not recognized")
        }
    }


}