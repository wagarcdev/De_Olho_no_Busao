package com.wagarcdev.deolhonobusao.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wagarcdev.deolhonobusao.data.remote.responses.ResponseObj

class Converters {

    private val lineAdapter by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listMyData = Types.newParameterizedType(List::class.java, ResponseObj.Line::class.java)
        return@lazy moshi.adapter<List<ResponseObj.Line>>(listMyData)
    }

    private val vehicleAdapter by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listMyData = Types.newParameterizedType(List::class.java, ResponseObj.Line.Vehicle::class.java)
        return@lazy moshi.adapter<List<ResponseObj.Line.Vehicle>>(listMyData)
    }

    @TypeConverter
    fun lineToJson(line: List<ResponseObj.Line>): String {
      return lineAdapter.toJson(line)
    }

    @TypeConverter
    fun lineFromJson(json: String): List<ResponseObj.Line>? {
        return lineAdapter.fromJson(json)
    }

    @TypeConverter
    fun vehicleToJson(vehicle: List<ResponseObj.Line.Vehicle>): String {
        return vehicleAdapter.toJson(vehicle)
    }
    @TypeConverter
    fun vehicleFromJson(json: String): List<ResponseObj.Line.Vehicle>? {
        return vehicleAdapter.fromJson(json)
    }
}