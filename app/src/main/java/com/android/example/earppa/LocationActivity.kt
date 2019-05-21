package com.android.example.earppa

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_location.*


class LocationActivity : AppCompatActivity() {
    lateinit var locationRequest: LocationRequest
    var started = true
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    companion object {
        var locationActivity: LocationActivity? = null

        fun getInstnace(): LocationActivity {
            return locationActivity!!
        }
    }


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        locationActivity = this
        Dexter.withActivity(this).withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@LocationActivity,
                        "You should accept the permission to access the app", Toast.LENGTH_LONG
                    ).show()
                }

                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    updateLocation()
                }
            }).check()


        cardview_start.setOnClickListener {
            started = true
        }
        cardview_stop.setOnClickListener {
            fusedLocationClient.removeLocationUpdates(getPendingIntent())
            started = false
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
        super.onBackPressed()
    }

    private fun updateLocation() {
        if (started) {
            buildLocationRequest()
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.requestLocationUpdates(locationRequest, getPendingIntent())
        }
    }

    fun getPendingIntent(): PendingIntent? {

        var intent = Intent(this@LocationActivity, MyLocationService::class.java)
        intent.action = MyLocationService.ACTION_PROCESS_UPDATE
        return PendingIntent.getBroadcast(this@LocationActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    fun updateText(locationString: String) {
        this@LocationActivity.runOnUiThread {
            text_location.text = locationString
        }
    }
}



