package dev.kichan.a2023_sunrin_dicon.ui.map

import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.base.BaseActivity
import dev.kichan.a2023_sunrin_dicon.databinding.ActivityMapBinding
import dev.kichan.a2023_sunrin_dicon.service.LocationService
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocationService.currentLocation.observe(this) {
            if (it != null) {
                val polyLine = MapPolyline().apply {
                    lineColor = Color.argb(128, 255, 51, 0)

                    for (i in LocationService.pointList) {
                        addPoint(MapPoint.mapPointWithGeoCoord(i.latitude, i.longitude))
                    }
                }

                (binding.mapMap[0] as MapView).addPolyline(polyLine)
            }
        }
    }

    override fun initView() = binding.run {
        mapMap.addView(map)
    }
}