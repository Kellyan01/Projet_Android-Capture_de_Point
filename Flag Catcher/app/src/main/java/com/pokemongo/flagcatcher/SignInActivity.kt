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
import com.pokemongo.flagcatcher.model.beans.UserBean
import com.pokemongo.flagcatcher.model.utils.GSON
import com.pokemongo.flagcatcher.model.utils.OkhttpUtils.sendPostOkHttpRequest
import java.lang.Exception
import kotlin.concurrent.thread

class SignInActivity : AppCompatActivity() {

    private var etName: EditText? = null
    private var etPassword: EditText? = null
    private var etEmail: EditText? = null
    private var test: TextView? = null
    private var signIn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        etEmail = findViewById(R.id.etEmail)
        test = findViewById(R.id.test)
        signIn = findViewById(R.id.signIn)
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

    fun onSignInClick(view: View) {

        thread { try {
            val User = UserBean(0, etName?.text.toString(), etPassword?.text.toString(), etEmail?.text.toString())
            val outputJson = GSON.toJson(User)
            sendPostOkHttpRequest("http://192.168.10.99:8080/register", outputJson)
        }
        catch (e:Exception){
            e.printStackTrace()
            Log.w("MY TAG", "ERROR 2!!!")
        }}

        test?.setText("${etName?.text}, ${etPassword?.text}, ${etEmail?.text}")
    }
}