package dev.kichan.daseul.ui.main.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.kichan.daseul.R
import dev.kichan.daseul.base.BaseFragment
import dev.kichan.daseul.databinding.FragmentMain2Binding

class Main2Fragment : BaseFragment<FragmentMain2Binding>(R.layout.fragment_main2) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    override fun initView() = binding.run {

    }
}