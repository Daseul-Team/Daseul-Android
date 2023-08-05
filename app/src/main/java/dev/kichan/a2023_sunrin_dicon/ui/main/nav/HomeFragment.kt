package dev.kichan.a2023_sunrin_dicon.ui.main.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.base.BaseFragment
import dev.kichan.a2023_sunrin_dicon.databinding.FragmentHomeBinding
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.setMapTilePersistentCacheEnabled

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private val map: MapView by lazy {
        MapView(requireActivity()).apply {
            isHDMapTileEnabled = true // HD 설정
            setMapTilePersistentCacheEnabled(true) // 맵 타일 캐시 저장
            currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading // 지도가 사용자 위치를 따라가도록 설정
            setDefaultCurrentLocationMarker() //현위치 마커 기본값 사용
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    override fun initView() = binding.run {
        mapHome.addView(map)
    }
}