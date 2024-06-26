package dev.kichan.daseul.ui.main.nav

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import dev.kichan.daseul.R
import dev.kichan.daseul.base.BaseFragment
import dev.kichan.daseul.databinding.FragmentHomeBinding
import dev.kichan.daseul.service.LocationService
import dev.kichan.daseul.ui.main.MainActivity
import dev.kichan.daseul.ui.main.group.Group_MainActivity
import dev.kichan.daseul.ui.map.MapActivity
import net.daum.mf.map.api.MapPoint

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    val locationList = mutableListOf<MapPoint>()

    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        LocationService.isStart.observe(viewLifecycleOwner, locationStartObserver)

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
}