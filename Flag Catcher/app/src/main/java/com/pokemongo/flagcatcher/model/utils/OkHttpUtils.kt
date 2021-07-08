package com.pokemongo.flagcatcher.model.utils


import android.util.Log
import com.google.gson.Gson
import com.pokemongo.flagcatcher.model.beans.ErrorBean
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()

object OkhttpUtils {
    val client = OkHttpClient()

    fun sendGetOkHttpRequest(url: String): String {
        println("url : $url")

        //Création de la requete
        val request = Request.Builder().url(url).build()

        //Execution de la requête
        val response = client.newCall(request).execute()

        //Analyse du code retour
        return            if(response.code == 518) {
                val error =  Gson().fromJson(response.body?.string(), ErrorBean::class.java)
                throw Exception(error.message)
            }
            else if (response.code !in 200..299) {
                throw Exception("Réponse du serveur incorrect : ${response.code}")
            } else {
                //Résultat de la requete.
                response.body?.string() ?: ""

            }


    }

    fun sendPostOkHttpRequest(url: String, paramJson: String): String {
        println("url : $url")

        //Corps de la requête
        val body = paramJson.toRequestBody(MEDIA_TYPE_JSON)

        //Création de la requete
        val request = Request.Builder().url(url).post(body).build()

        //Execution de la requête
        val response = client.newCall(request).execute()

        //Analyse du code retour
        return if (response.code !in 200..299) {
            throw Exception("Réponse du serveur incorrect : ${response.code}")
        } else {
            //Résultat de la requete.
            // ATTENTION .string ne peut être appelée qu’une seule fois.
            response.body?.string() ?: ""
        }

    }
}