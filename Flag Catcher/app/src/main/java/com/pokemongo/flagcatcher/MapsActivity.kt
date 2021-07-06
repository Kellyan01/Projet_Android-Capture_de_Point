package com.pokemongo.flagcatcher

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pokemongo.flagcatcher.databinding.ActivityMapsBinding
import com.pokemongo.flagcatcher.model.utils.WSUtils

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var tv: TextView? = null
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        tv = findViewById(R.id.tv)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0,0,0,"Accueil")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent=Intent(this, MainActivity::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }



    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////               Localisation                      //////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    //Click du bouton attribué avec l'attribut onClick dans le XML
    fun onBtRefreshClick(view: View?) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            //On a la permission
            afficherLocalisation()
        } else {
            //Etape 2 : On affiche la fenêtre de demande de permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }
    }


    //Callback de la demande de permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            //On a la permission
            afficherLocalisation()
        } else {
            tv?.setText("Il faut la permission")
        }
    }


    private fun afficherLocalisation() {
        val location = getLastKnownLocation()
        if (location != null) {
            tv!!.text = location.latitude.toString() + " " + location.longitude
        } else {
            tv!!.text = "La localisation est nulle"
        }
        showProgressBar(true)
        Thread {
            try {
                //Chercher la donnée
                val coordi: LatLng = WSUtils.getCoordinate()
                //Mettre à jour l'IHM
                showCoordinateBeanOnUIThread(coordi)
            } catch (e: Exception) {
                //Affiche le detail de l'erreur dans la console
                e.printStackTrace()
                showErrorOnUiThread(e.message)
            }
            showProgressBar(false)
        }.start()
    }

    private fun getLastKnownLocation(): Location? {
        //Contrôle de la permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_DENIED
        ) {
            return null
        }
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        var bestLocation: Location? = null
        //on teste chaque provider(réseau, GPS...) et on garde la localisation avec la meilleurs précision
        for (provider in lm.getProviders(true)) {
            val l = lm.getLastKnownLocation(provider)
            if (l != null && (bestLocation == null || l.accuracy < bestLocation.accuracy)) {
                bestLocation = l
            }
        }
        return bestLocation
    }

    /* -------------------------------- */
    // Mettre à jour l'IHM
    /* -------------------------------- */


    fun showCoordinateBeanOnUIThread(coordBean: LatLng) {
        runOnUiThread { tv?.text = "${coordBean.longitude} - ${coordBean.latitude}" }
    }

    fun showErrorOnUiThread(errorMessage: String?) {
        runOnUiThread { tv!!.text = "Erreur : $errorMessage" }
    }

    fun showProgressBar(show: Boolean) {
        runOnUiThread {
            if (show) {
                progressBar?.setVisibility(View.VISIBLE)
            } else {
                progressBar?.setVisibility(View.GONE)
            }
        }
    }



}
