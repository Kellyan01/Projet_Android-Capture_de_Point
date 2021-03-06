package com.pokemongo.flagcatcher.model.utils

import android.util.Log
import com.google.gson.Gson
import com.pokemongo.flagcatcher.model.beans.CoordinateBean
import com.google.gson.reflect.TypeToken
import com.pokemongo.flagcatcher.model.beans.LoginBean
import com.pokemongo.flagcatcher.model.beans.UserBean

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

        fun setUsers(user: UserBean) {

            val url = "$SERVER/register"
            val outputJson = GSON.toJson(user)

            println(outputJson)
            OkhttpUtils.sendPostOkHttpRequest(url, outputJson)

        }

        fun getUsers():UserBean {
            val url = "$SERVER/getUsers"
            Log.w("MY TAG GetUsers", "URL")

            val request = OkhttpUtils.sendGetOkHttpRequest(url)
            Log.w("MY TAG GetUsers", "REQUEST")


            val user = GSON.fromJson(request,UserBean::class.java)
            Log.w("MY TAG GetCUser", "$user")

            return user
        }

        fun loginUsers(user:LoginBean) {

            val url = "$SERVER/login"
            val outputJson = GSON.toJson(user)
            Log.w("MY TAG", outputJson.toString())
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
            throw Exception("T'as Merd?? Mec !!!")
        }

    }
}
