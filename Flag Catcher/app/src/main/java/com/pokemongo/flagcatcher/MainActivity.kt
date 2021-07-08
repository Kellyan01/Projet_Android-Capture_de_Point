package com.pokemongo.flagcatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0,1,0,"Map")
        menu?.add(0,2,0,"Inscription")
        menu?.add(0,3,0,"Connexion")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==1){
                val intent=Intent(this, MapsActivity::class.java)
                startActivity(intent)
        }
        else if (item.itemId == 2){
            val intent=Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        else if (item.itemId == 3){
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}


