package com.pokemongo.flagcatcher.model.utils

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.pokemongo.flagcatcher.model.beans.CoordinateBean

class WSUtils {
    companion object {

        fun getCoordinate(): LatLng {
            var url = "http://192.168.10.99:8080/getCoordinate"
            Log.w("MY TAG", "URL = ${url}")
            var request = OkhttpUtils.sendGetOkHttpRequest(url)
            Log.w("MY TAG REQUEST", "REQUEST FAIT")
            val gson = Gson()
            Log.w("MY TAG GSON", "GSON CREE")
            var point = gson.fromJson(request, CoordinateBean::class.java)
            Log.w("MY TAG JSON", "JSON = ${point}")
            var position = LatLng(point.lat_coordinate, point.long_coordinate)
            return position
            throw Exception("Je ne dirais pas que c'est un échec, je dirais que ca n'a pas marché")
        }
    }

}