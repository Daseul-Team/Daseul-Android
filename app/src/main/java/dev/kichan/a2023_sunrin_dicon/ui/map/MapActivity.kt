package dev.kichan.a2023_sunrin_dicon.ui.map

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.base.BaseActivity
import dev.kichan.a2023_sunrin_dicon.databinding.ActivityMapBinding
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView

class MapActivity : BaseActivity<ActivityMapBinding>(R.layout.activity_map) {
    private val map: MapView by lazy {
        MapView(this).apply {
            isHDMapTileEnabled = true // HD 설정
            MapView.setMapTilePersistentCacheEnabled(true) // 맵 타일 캐시 저장
            currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading // 지도가 사용자 위치를 따라가도록 설정
            setDefaultCurrentLocationMarker() //현위치 마커 기본값 사용

            addPolyline(MapPolyline().apply {
                lineColor = Color.argb(128, 255, 0, 0)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() = binding.run {
        mapMap.addView(map)
    }
}