package com.pokemongo.flagcatcher.model.utils


import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

object OkhttpUtils {
    @Throws(Exception::class)
    fun sendGetOkHttpRequest(url: String): String {
        Log.w("tag", "url : $url")
        val client = OkHttpClient()

        //Création de la requete
        val request= Request.Builder().url(url).build()

        //Execution de la requête
        val response = client.newCall(request).execute()

        //Analyse du code retour
        return if (response.code < 200 || response.code >= 300) {
            throw Exception("Réponse du serveur incorrect : " + response.code)
        } else {

            //Résultat de la requete.
            response.body!!.string()
        }
    }
    @Throws(Exception::class)
    fun sendPostOkHttpRequest(url: String, paramJson: String?): String {
        Log.w("tag", "url: $url")
        OkHttpClientclient = newOkHttpClient()
        MediaTypeJSON = MediaType.parse("application/json; charset=utf-8")

        //Corps de la requête
        RequestBodybody = RequestBody.create(JSON, paramJson)

        //Création de la requete
        val request: Request = newRequest.Builder().url(url).post(body).build()

        //Executionde la requête
        Responseresponse= client.newCall(request).execute();

        //Analyse du code retour
        if (response.code() < 200 || response.code() >= 300) {
            thrownewException("Réponse du serveur incorrect : " + response.code())
        }

        else {

            //Résultat de la requete.
            return response.body().string()
        }
    }
}
