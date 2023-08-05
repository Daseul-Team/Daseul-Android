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
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.model.data.test.TestReq
import dev.kichan.a2023_sunrin_dicon.model.repository.TestRepository
import dev.kichan.a2023_sunrin_dicon.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.round

class LocationService : LifecycleService() {
    private val testRepository = TestRepository()

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val loationRqeust = LocationRequest.create().apply {
        interval = 5 * 1000L
        fastestInterval = 5 * 1000L
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            if (isStart.value!!) {
                for (location in locationResult.locations) {
                    currentLocation.value = location
                    pointList.add(location)

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            testRepository.test(TestReq(lat = location.latitude, lng = location.longitude))
                        }
                        catch (e : InternalError) {
                            Log.e("test", e.toString())
                        }
                    }

                    Toast.makeText(
                        this@LocationService,
                        "위치 업데이트 (${round(location.latitude * 1000) / 1000}, ${round(location.longitude * 1000) / 1000})",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isStart.value!!) {
            startForegroundService()

            val permissionCheck =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.requestLocationUpdates(
                    loationRqeust, locationCallback, Looper.getMainLooper()
                )
            }

            isStart.value = true
        } else {
            stopSelf()
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)

            isStart.value = false
        }

        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun startForegroundService() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
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
        val pointList = mutableListOf<Location>()

        const val NOTIFICATION_CHANNEL_ID = "location_notification"
        const val NOTIFICATION_CHANNEL_NAME = "location_notification"
        const val NOTIFICATION_ID = 100
    }
}