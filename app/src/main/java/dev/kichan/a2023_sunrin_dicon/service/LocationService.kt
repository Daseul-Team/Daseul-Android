package dev.kichan.a2023_sunrin_dicon.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.ui.main.MainActivity

class LocationService : LifecycleService() {
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val notificationManager : NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val loationRqeust = LocationRequest.create().apply {
        interval = 5000L
        fastestInterval = 2000L
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult : LocationResult) {
            super.onLocationResult(locationResult)

            if(isStart.value!!){
                for(location in locationResult.locations) {
                    currentLocation.value = location
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isStart.value!!) {
            startForegroundService()

            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.requestLocationUpdates(
                    loationRqeust, locationCallback, Looper.getMainLooper()
                )
            }

            isStart.value = true
        }
        else {
            stopSelf()
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)

            isStart.value = false
        }

        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun startForegroundService() {
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).apply {
            setAutoCancel(false)
            setOngoing(true)
            setSmallIcon(R.drawable.ic_home)
            setContentTitle("잡았다 요놈")
            setContentText("위치추적중이니까 도망갈 생각 말라고 ㅋㅋ")
            setContentIntent(pendingIntent)
        }

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        val isStart = MutableLiveData(false)
        val currentLocation = MutableLiveData<Location?>()

        const val NOTIFICATION_CHANNEL_ID = "location_notification"
        const val NOTIFICATION_CHANNEL_NAME = "location_notification"
        const val NOTIFICATION_ID = 100

//        const val ACTION_START_SERVICE = "action_start_service"
//        const val ACTION_STOP_SERVICE = "action_stop_service"
    }
}