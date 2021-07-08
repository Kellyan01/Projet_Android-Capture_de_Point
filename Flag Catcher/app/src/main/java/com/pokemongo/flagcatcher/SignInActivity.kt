package com.pokemongo.flagcatcher

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
        val userInfo = users(name = etName?.text,
            password = etPassword?.text,
            mail = etEmail?.text)
        test?.setText("Nom = " + etName?.text + " Password = " + etPassword?.text + " Email = " + etEmail?.text)
    }
}