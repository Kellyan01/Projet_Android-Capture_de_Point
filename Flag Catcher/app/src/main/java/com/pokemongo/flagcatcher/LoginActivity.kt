package com.pokemongo.flagcatcher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pokemongo.flagcatcher.model.beans.ErrorBean
import com.pokemongo.flagcatcher.model.beans.LoginBean
import com.pokemongo.flagcatcher.model.beans.UserBean
import com.pokemongo.flagcatcher.model.utils.OkhttpUtils.sendGetOkHttpRequest
import com.pokemongo.flagcatcher.model.utils.WSUtils
import java.lang.Exception
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    private var etNameLog: EditText? = null
    private var etPasswordLog: EditText? = null
    private var test: TextView? = null
    private var LogIn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etNameLog = findViewById(R.id.etNameLog)
        etPasswordLog = findViewById(R.id.etPasswordLog)
        test = findViewById(R.id.test)
        LogIn = findViewById(R.id.signIn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0,1,0,"Accueil")
        menu?.add(0,2,0,"Map")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==1){
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else if (item.itemId == 2){
            val intent= Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    fun onLogInClick(view: View) {
        if(etNameLog?.text.isNullOrBlank() || etPasswordLog?.text.isNullOrBlank()) {
            val snackbar = Snackbar
                .make(view, "Remplissez tous les champs", Snackbar.LENGTH_LONG)
                .setAction("NOOOOO!!!!!!") { }
            snackbar.show()
        }
        else {
            thread {
                try {
                    val user = LoginBean(
                        etNameLog!!.text.toString(),
                        etPasswordLog!!.text.toString()
                    )
                    WSUtils.loginUsers(user)
                    val snackbar = Snackbar
                        .make(view, "Vous êtes connecté", Snackbar.LENGTH_LONG)
                        .setAction("YES!!!") { }
                    snackbar.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.w("MY TAG", "ERROR CONNEXION!!!")
                }
            }
        }
    }
}
