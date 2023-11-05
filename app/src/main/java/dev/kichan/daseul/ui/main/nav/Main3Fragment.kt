package dev.kichan.daseul.ui.main.nav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.kichan.daseul.BuildConfig
import dev.kichan.daseul.R
import dev.kichan.daseul.base.BaseFragment
import dev.kichan.daseul.databinding.FragmentMain3Binding
import dev.kichan.daseul.model.data.test.data_infoGroup
import dev.kichan.daseul.ui.main.MainActivity
import dev.kichan.daseul.ui.main.group.Group_MainActivity
import dev.kichan.daseul.ui.main.group.MyAdapter
import okhttp3.internal.notifyAll

class Main3Fragment : BaseFragment<FragmentMain3Binding>(R.layout.fragment_main3) {
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