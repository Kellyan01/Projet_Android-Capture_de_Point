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
}
