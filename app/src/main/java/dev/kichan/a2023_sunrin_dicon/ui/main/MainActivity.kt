package dev.kichan.a2023_sunrin_dicon.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.base.BaseActivity
import dev.kichan.a2023_sunrin_dicon.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
//    private val fusedLocationClient : FusedLocationProviderClient by lazy {
//        LocationServices.getFusedLocationProviderClient(this)
//    }
//
//    private val map: MapView by lazy {
//        MapView(this).apply {
//            isHDMapTileEnabled = true // HD 설정
//            setMapTilePersistentCacheEnabled(true) // 맵 타일 캐시 저장
//            currentLocationTrackingMode =
//                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading // 지도가 사용자 위치를 따라가도록 설정
//            setDefaultCurrentLocationMarker() //현위치 마커 기본값 사용
//        }
//    }

    private val navController : NavController by lazy { findNavController(R.id.fragment_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 사용자 위치 권한 허용
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            map.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(1.0, 1.0), false)
        }
    }

    override fun initView() = binding.run {
        bnvMain.setupWithNavController(navController)
    }

    private fun getKakaoKeyHash() {
        try {
            val information =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = information.signingInfo.apkContentsSigners
            for (signature in signatures) {
                val md = MessageDigest.getInstance("SHA").apply {
                    update(signature.toByteArray())
                }
                val HASH_CODE = String(Base64.encode(md.digest(), 0))

                Log.d("kakao sha", "HASH_CODE -> $HASH_CODE")
            }
        } catch (e: Exception) {
            Log.d("kakao sha", "Exception -> $e")
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}