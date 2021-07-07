package com.pokemongo.flagcatcher.model.utils

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pokemongo.flagcatcher.model.beans.CoordinateBean

// Constantes
const val SERVER = "http://192.168.10.99:8080"
val GSON = Gson()

class WSUtils {

    companion object {

        fun getCoordinate(): ArrayList<CoordinateBean> {

            val url = "$SERVER/getCoordinate"

            val request = OkhttpUtils.sendGetOkHttpRequest(url)

            // https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520#43aa
            val point = object : TypeToken<ArrayList<CoordinateBean>>() { }.type

            val pointList = GSON.fromJson<ArrayList<CoordinateBean>>(request, point)

            pointList.forEach{it: CoordinateBean -> Log.w("MY TAG JSON", "JSON = $it")}

            return pointList
        }

        fun getTest(): String{
            val url = "$SERVER/test"
            Log.w("MY TAG", "URL = ${url}")

            val request = OkhttpUtils.sendGetOkHttpRequest(url)
            //var response: String = "OK ca REPOND"
            println(request)
            return request
            throw Exception("T'as Merdé Mec !!!")
        }

    }
}
