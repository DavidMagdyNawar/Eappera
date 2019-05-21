package com.android.example.earppa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.LocationResult
import java.lang.Exception
import java.lang.StringBuilder

class MyLocationService : BroadcastReceiver() {
    companion object {
        const val ACTION_PROCESS_UPDATE:String = "com.android.example.earppa.UPDATE_LOCATION"

        }

    override fun onReceive(p0: Context?, p1: Intent?) {

        if(p1 != null){
            var action:String = p1.action
            if(action == ACTION_PROCESS_UPDATE )
            {
                var result: LocationResult? = LocationResult.extractResult(p1!!)

                if(result != null)
                {
                    var location = result.lastLocation
                    var locationString = StringBuilder(""+ location.latitude)
                        .append("/")
                        .append(location.longitude)
                        .toString()
                    try {
                        LocationActivity.getInstnace().updateText(locationString)
                    }catch (ex:Exception){
                        Toast.makeText(p0,locationString,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
