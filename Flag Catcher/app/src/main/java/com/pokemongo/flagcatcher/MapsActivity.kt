package com.pokemongo.flagcatcher

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.pokemongo.flagcatcher.databinding.ActivityMapsBinding
import com.pokemongo.flagcatcher.model.beans.CoordinateBean
import com.pokemongo.flagcatcher.model.utils.WSUtils
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.InfoWindowAdapter,
    GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var tv: TextView? = null
    private var progressBar: ProgressBar? = null
    private lateinit var ivMark:ImageView
    private lateinit var tvMark:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        /*tv = findViewById(R.id.tv)
        progressBar = findViewById(R.id.progressBar)*/

        thread {
            //Affichage simple d'un objet toulouse de type CoordinateBean de coordonnée 43,3512 - 1,2938
            //Création de l'objet toulouse
            val toulouse = CoordinateBean(1, 1.2938, 43.3512)
            Log.w("MY TAG toulouse", "CREATION DE TOULOUSE")

            //Transformation de Toulouse en objet LatLng
            val toulouseLatLng = LatLng(toulouse.lat_coordinate, toulouse.long_coordinate)
            Log.w("MY TAG toulouse", "CREATION DE TOULOUSE LATLANG")

            //Modification de la partie graphique
            runOnUiThread {
                //On efface les point existant de la MAP
                mMap.clear()
                Log.w("MY TAG MAP", "Effacement des points")

                //Centrage de la caméra sur les coordonnées de Toulouse avec zoom x5
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(toulouseLatLng, 5f)
                )
                Log.w("MY TAG MAP", "Mouvement CAMERA")

                //Création et Affichage du Marker
                var markerToulouse = MarkerOptions()
                markerToulouse.position(
                    LatLng(toulouse.lat_coordinate, toulouse.long_coordinate)
                )
                Log.w("MY TAG MAKER", "CREATION MARKER")
                markerToulouse.icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
                Log.w("MY TAG MAKER", "ICON MARKER")
                mMap.addMarker(markerToulouse).tag = toulouse
                Log.w("MY TAG MAKER", "AFFICHAGE MARKER")
            }
        }


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
        //On indique qu’on souhaite customiser
        //les popups des markers
        //Génère getInfoWindow et getInfoContents
        mMap.setInfoWindowAdapter(this)
    }



    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////               Localisation                      //////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////



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
                val coord = WSUtils.getCoordinate()
                //Mettre à jour l'IHM
                showCoordinateBeanOnUIThread(coord)
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
        runOnUiThread {
            //tv?.setText(coordBrean.latitude  coordBrean.longitude)
            tv?.text= coordBean.latitude.toString() +  " - " + coordBean.longitude.toString()
        }
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

    override fun getInfoWindow(p0: Marker?): View? {
      return null
    }

    override fun getInfoContents(p0: Marker): View {
        //Instancie le xml dans layout represantant l'infowindow
        val view = LayoutInflater.from(this).inflate(R.layout.marker_coordinate, null)

        var ivMark = view.findViewById<ImageView>(R.id.ivMark)
        var tvMark = view.findViewById<TextView>(R.id.tvMark)
        //Dans le marker on a associé la donnée dans l'attribut tag
        //Obligé de la caster pour la retrouver car un tag est générique (type Any)
        val maVille = p0.tag as CoordinateBean
        tvMark.text = "Latitude : "+maVille.lat_coordinate.toString() + " - Longitude :"+maVille.long_coordinate.toString()
        return view
    }



    override fun onInfoWindowClick(p0: Marker?) {
        val ville: CoordinateBean? = p0?.tag as CoordinateBean?
        //Ferme la fenêtre
        p0?.hideInfoWindow()
    }


}