package dev.kichan.daseul.ui.main.nav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kichan.daseul.R
import dev.kichan.daseul.base.BaseFragment
import dev.kichan.daseul.databinding.FragmentMain3Binding
import dev.kichan.daseul.ui.main.group.Group_MainActivity

class Main3Fragment : BaseFragment<FragmentMain3Binding>(R.layout.fragment_main3) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.testBtn.setOnClickListener{
            var intent = Intent(context,Group_MainActivity::class.java)
            startActivity(intent)
}
        return binding.root
    }

    override fun initView() = binding.run {

    }
}