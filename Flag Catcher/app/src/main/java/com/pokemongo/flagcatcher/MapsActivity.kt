package com.pokemongo.flagcatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pokemongo.flagcatcher.databinding.ActivityMapsBinding
import android.content.Intent
import android.util.Log
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.pokemongo.flagcatcher.model.beans.CoordinateBean
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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
                mMap.addMarker(markerToulouse)
                Log.w("MY TAG MAKER", "AFFICHAGE MARKER")
            }
        }
    }

    /////////////////////////////////////////////////////
    //////////////         MENU         /////////////////
    /////////////////////////////////////////////////////
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
}