package com.pokemongo.flagcatcher.model.utils

import android.util.Log
import com.google.gson.Gson
import com.pokemongo.flagcatcher.model.beans.CoordinateBean
import com.google.gson.reflect.TypeToken

// Constantes
const val SERVER = "http://192.168.10.99:8080"
val GSON = Gson()


class WSUtils {



    companion object {

        fun getCoordinate(): ArrayList<CoordinateBean> {

            val url = "$SERVER/getCoordinate"
            Log.w("MY TAG GetCoordinate", "URL")

            val request = OkhttpUtils.sendGetOkHttpRequest(url)
            Log.w("MY TAG GetCoordinate", "REQUEST")

            // https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520#43aa
            val point = object : TypeToken<ArrayList<CoordinateBean>>() { }.type
            Log.w("MY TAG GetCoordinate", "POINT")

            val pointList = GSON.fromJson<ArrayList<CoordinateBean>>(request, point)
            Log.w("MY TAG GetCoordinate", "POINTLIST JSON")


            pointList.forEach{it: CoordinateBean -> Log.w("MY TAG JSON", "JSON = $it")}

            return pointList
        }

        fun setCoordinate(coordinate: CoordinateBean) {

            val url = "$SERVER/setCoordinate"
            val outputJson = GSON.toJson(coordinate)

            println(outputJson)
            OkhttpUtils.sendPostOkHttpRequest(url, outputJson)

        }

        fun getTest(): String{
            val url = "$SERVER/test"
            Log.w("MY TAG", "URL = ${url}")

            val request = OkhttpUtils.sendGetOkHttpRequest(url)
            //var response: String = "OK ca REPOND"
            println(request)
            return request.toString()
            throw Exception("T'as Merdé Mec !!!")
        }

    }
}
