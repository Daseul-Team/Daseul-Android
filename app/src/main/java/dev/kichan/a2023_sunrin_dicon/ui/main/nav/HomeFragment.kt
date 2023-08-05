package dev.kichan.a2023_sunrin_dicon.ui.main.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.kichan.a2023_sunrin_dicon.R
import dev.kichan.a2023_sunrin_dicon.base.BaseFragment
import dev.kichan.a2023_sunrin_dicon.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }
}