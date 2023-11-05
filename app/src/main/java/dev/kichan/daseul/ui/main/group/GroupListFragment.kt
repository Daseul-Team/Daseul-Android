package dev.kichan.daseul.ui.main.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.FragmentGroupListBinding
import dev.kichan.daseul.model.data.test.data_infoGroup

class GroupListFragment(private val itemList: MutableList<data_infoGroup>, val Token : String): BottomSheetDialogFragment() {
    private var _binding : FragmentGroupListBinding? = null
    private val binding get() = _binding!!
    private val Adapter = MyAdapter(itemList,Token)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupListBinding.inflate(inflater, container, false)
        binding.recyclerViewGroupList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewGroupList.adapter = Adapter

        Adapter.setData(itemList)

        return binding.root
    }
}