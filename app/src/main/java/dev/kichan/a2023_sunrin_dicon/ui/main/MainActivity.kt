package dev.kichan.a2023_sunrin_dicon.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.base.BaseActivity
import dev.kichan.a2023_sunrin_dicon.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener
import net.daum.mf.map.api.MapView.setMapTilePersistentCacheEnabled
import java.security.MessageDigest

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val map : MapView by lazy { MapView(this).apply {
        isHDMapTileEnabled = true // HD 설정
        setMapTilePersistentCacheEnabled(true) // 맵 타일 캐시 저장
        currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading // 지도가 사용자 위치를 따라가도록 설정
        setDefaultCurrentLocationMarker() //현위치 마커 기본값 사용
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 사용자 위치 권한 허용
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100
            )
        }
    }

    override fun initView() = binding.run {
        mapMain.addView(map)
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
}