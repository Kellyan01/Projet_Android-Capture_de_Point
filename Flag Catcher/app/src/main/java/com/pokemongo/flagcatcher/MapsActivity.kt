package com .pokemongo.flagcatcher

import android.Manifest
import android.app.AlertDialog
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.pokemongo.flagcatcher.databinding.ActivityMapsBinding
import com.pokemongo.flagcatcher.model.beans.CoordinateBean
import com.pokemongo.flagcatcher.model.utils.WSUtils
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.InfoWindowAdapter,
    GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var tv: TextView
    private lateinit var tvError:TextView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        thread {
            try {
                runOnUiThread {
                    progressBar.isVisible = true
                }
                //R??cup??ration de la liste de point au format ArrayList<CoordinateBean>
                val pointList = WSUtils.getCoordinate()
                Log.w("MY TAG PointList", pointList.toString())

                if(pointList.isNullOrEmpty()) {
                    Log.w("MY TAG ERROR ","Aucun Point ?? afficher" )
                    tvError.text = "ERROR : Aucun Point ?? afficher !"
                    tvError.isVisible = true
                    progressBar.isVisible = false
                }
                else {
                    refreshMap(pointList)
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                Log.w("MY TAG", "ERROR!!!")
                runOnUiThread {
                    progressBar.isVisible = false
                }
            }
        }

        tv = findViewById(R.id.tv)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
    }

    ////////////////////////////////////
    ///////////// MENU /////////////////
    ////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 0, 0, "Accueil")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==0){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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
        //On indique qu???on souhaite customiser
        //les popups des markers
        //G??n??re getInfoWindow et getInfoContents
        mMap.setInfoWindowAdapter(this)

        //Affichage du users et centrage cam??ra sur lui
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED        )
            //On a la permission
            {
            mMap.isMyLocationEnabled = true

            //R??cup??ration des coordonn??es users
            var lat = getLastKnownLocation()!!.latitude
            var long = getLastKnownLocation()!!.longitude
            var usersPositionLatLang = LatLng(lat,long)

            //Centrage de la cam??ra sur les coordonn??es de users avec zoom x9
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(usersPositionLatLang, 9f)
            )
            //Affichage Coordonn??s user dans le TextView
            tv?.text ="Vos Coordonn??es : Latitude : $lat - Longitude : $long"
        }
        else {//On demande la Permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////         PERMISSION + LOCALISATION               //////////////////////
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
            mMap.isMyLocationEnabled = true
            //R??cup??ration des coordonn??es users
            var lat = getLastKnownLocation()!!.latitude
            var long = getLastKnownLocation()!!.longitude
            var usersPositionLatLang = LatLng(lat,long)

            //Centrage de la cam??ra sur les coordonn??es de users avec zoom x9
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(usersPositionLatLang, 9f)
            )
            //Affichage Coordonn??s user dans le TextView
            tv?.text ="Vos Coordonn??es : Latitude : $lat - Longitude : $long"

        } else {
            tvError.text="Il faut la permission"
        }
    }

    //R??cup??re les coordonn??es de users pour centrer la cam??ra sur lui dans le onMapReady
    fun getLastKnownLocation(): Location? {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED
        ) {
            return null
        }
        var location = getSystemService(LOCATION_SERVICE) as LocationManager
        return location.getProviders(true).map { location.getLastKnownLocation(it) }
            .minByOrNull { it?.accuracy ?: Float.MAX_VALUE }
    }

    //OBSOLETE REMPLACE PAR mMap.isMyLocationEnabled = true DANS OnMapReady//
    /* private fun afficherLocalisation() {
        val location = getLastKnownLocation()
        if (location != null) {

            Thread {
                try {
                    runOnUiThread {
                        //Cr??ation et Affichage du Marker
                        var markerUser = MarkerOptions()
                        markerUser.position(
                            LatLng(location.latitude, location.longitude)
                        )
                        Log.w("MY TAG MAKER", "CREATION MARKER")
                        markerUser.icon(
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                        )
                        Log.w("MY TAG MAKER", "ICON MARKER")
                        mMap.addMarker(markerUser).tag = location
                        Log.w("MY TAG MAKER", "AFFICHAGE MARKER")
                    }

                    //Mettre ?? jour l'IHM
                    //showCoordinateBeanOnUIThread(coordi)
                } catch (e: Exception) {
                    //Affiche le detail de l'erreur dans la console
                    e.printStackTrace()
                    setError("Error = " + e.message)
                }
                showProgressBar(false)
            }.start()
           // tv!!.text = location.latitude.toString() + " " + location.longitude
        } else {
            tv?.setText("Impossible de trouver la localisation")
        }
        showProgressBar(true)
        setError(null)

    }

    private fun getLastKnownLocation(): Location? {
        //Contr??le de la permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_DENIED
        ) {
            return null
        }
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        var bestLocation: Location? = null
        //on teste chaque provider(r??seau, GPS...) et on garde la localisation avec la meilleurs pr??cision
        for (provider in lm.getProviders(true)) {
            val l = lm.getLastKnownLocation(provider)
            if (l != null && (bestLocation == null || l.accuracy < bestLocation.accuracy)) {
                bestLocation = l
            }
        }
        return bestLocation
    }*/

    /* -------------------------------- */
    // Mettre ?? jour l'IHM
    /* -------------------------------- */

    fun refreshMap(coordList:ArrayList<CoordinateBean>){
        //Modification de la partie graphique
        runOnUiThread {
            //On efface les point existant de la MAP
            mMap.clear()
            Log.w("MY TAG MAP", "Effacement des points")

            //Cr??ation et Affichage du MArker pour chaque point
            var j = 0
            for(i in coordList) {
                var markerPoint = MarkerOptions()
                markerPoint.position(LatLng(coordList[j].lat_coordinate, coordList[j].long_coordinate))
                markerPoint.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                mMap.addMarker(markerPoint).tag=coordList[j]
                j++
                progressBar.isVisible = false
            }
        }
    }

    ////////////////////////////////////////////////////////
    ///////////// CUSTOMISATION DES MARKERS ////////////////
    ////////////////////////////////////////////////////////
    
        override fun getInfoWindow(p0: Marker?): View? {
            return null
        }

        override fun getInfoContents(p0: Marker): View {
            //Instancie le xml dans layout represantant l'infowindow
            val view = LayoutInflater.from(this).inflate(R.layout.marker_coordinate, null)

            var ivMark = view.findViewById<ImageView>(R.id.ivMark)
            var tvMark = view.findViewById<TextView>(R.id.tvMark)
            //Dans le marker on a associ?? la donn??e dans l'attribut tag
            //Oblig?? de la caster pour la retrouver car un tag est g??n??rique (type Any)
            val maVille = p0.tag as CoordinateBean
            tvMark.text =
                "Latitude : " + maVille.lat_coordinate.toString() + " - Longitude :" + maVille.long_coordinate.toString()
            return view
        }

        override fun onInfoWindowClick(p0: Marker?) {
            val ville: CoordinateBean? = p0?.tag as CoordinateBean?
            //Ferme la fen??tre
            p0?.hideInfoWindow()
        }
    }
