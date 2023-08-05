package dev.kichan.a2023_sunrin_dicon.ui.main.nav

import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.base.BaseFragment
import dev.kichan.a2023_sunrin_dicon.databinding.FragmentHomeBinding
import dev.kichan.a2023_sunrin_dicon.service.LocationService
import dev.kichan.a2023_sunrin_dicon.ui.map.MapActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.setMapTilePersistentCacheEnabled
import kotlin.math.round

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    val locationList = mutableListOf<MapPoint>()

    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        LocationService.isStart.observe(viewLifecycleOwner, locationStartObserver)
//        LocationService.currentLocation.observe(viewLifecycleOwner, locationObserver)

        return binding.root
    }

    override fun initView() = binding.run {
        btnHomeStart.setOnClickListener {
            val intent = Intent(requireContext(), LocationService::class.java)
            requireContext().startService(intent)
        }

        btnHomeGotoMap.setOnClickListener {
            val intent = Intent(requireContext(), MapActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
//        binding.mapHome.removeView(map)
    }

    private val locationStartObserver : (Boolean) -> Unit = {
        if(it) {
            binding.btnHomeStart.text = "위치추적 정지"
        }
        else {
            binding.btnHomeStart.text = "위치추적 시작"
        }
    }

    private val locationObserver : (Location?) -> Unit = {
        if(it != null){
            val mapPoint = MapPoint.mapPointWithGeoCoord(it.latitude, it.longitude)
            val marker = MapPOIItem().apply {
                setMapPoint(mapPoint)
                itemName = "${count}번째 위치"
                tag = 0
                markerType = MapPOIItem.MarkerType.BluePin;
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }

            (binding.mapHome[0] as MapView).addPOIItem(marker)

            Log.d("test", it.toString())
        }
    }
}