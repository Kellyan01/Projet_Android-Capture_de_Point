package com.pokemongo.flagcatcher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pokemongo.flagcatcher.model.beans.ErrorBean
import com.pokemongo.flagcatcher.model.beans.UserBean
import com.pokemongo.flagcatcher.model.utils.WSUtils
import kotlin.concurrent.thread


class SignInActivity : AppCompatActivity() {

    private var etName: EditText? = null
    private var etPassword: EditText? = null
    private var etEmail: EditText? = null
    private var signIn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        etName = findViewById(R.id.etNameLog)
        etPassword = findViewById(R.id.etPasswordLog)
        etEmail = findViewById(R.id.etEmail)
        signIn = findViewById(R.id.signIn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0,1,0,"Accueil")
        menu?.add(0,2,0,"Connexion")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==1){
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else if (item.itemId == 2){
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    
    fun onSignInClick(view: View) {
        if(etName?.text.isNullOrBlank() || etPassword?.text.isNullOrBlank() || etEmail?.text.isNullOrBlank()) {
            val error=ErrorBean("Remplissez tous les champs")
            //SnackBar(Mix de AlertDialog et Toast) de Confirmation d'inscription
            val snackbar = Snackbar
                .make(view, error.message, Snackbar.LENGTH_LONG)
                .setAction("NOOOOO!!!!!!") { }
            snackbar.show()
        }
        else {
            thread {
                try {
                    val user = UserBean(
                        0,
                        etName!!.text.toString(),
                        etPassword!!.text.toString(),
                        etEmail!!.text.toString()
                    )
                    WSUtils.setUsers(user)

                    //SnackBar(Mix de AlertDialog et Toast) de Confirmation d'inscription
                    val snackbar = Snackbar
                        .make(view, "Inscription résussie", Snackbar.LENGTH_LONG)
                        .setAction("OKAY!!!!!!") { }

                    snackbar.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.w("MY TAG", "ERROR 2!!!")
                }
            }
        }
    }
}