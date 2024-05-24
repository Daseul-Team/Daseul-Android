package dev.kichan.daseul.ui.main.group

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.FragmentGroupListBinding
import dev.kichan.daseul.model.data.test.data_infoGroup
import dev.kichan.daseul.ui.main.MainActivity

class GroupListFragment(private val itemList: MutableList<data_infoGroup>, val Token : String): BottomSheetDialogFragment() {
    private var _binding : FragmentGroupListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupListBinding.inflate(inflater, container, false)
        binding.recyclerViewGroupList.layoutManager = LinearLayoutManager(context)
        adapter = MyAdapter(itemList, Token, object : OnItemClickListener {
            override fun onItemClick(item: data_infoGroup) {
                (activity as MainActivity).updateview(item)
            }
        })


        binding.recyclerViewGroupList.adapter = adapter
        adapter.setData(itemList)


        val iconDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.icon_make) }

        val widthInPixels = resources.getDimensionPixelSize(R.dimen.icon_width)
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.icon_height)

        iconDrawable?.setBounds(0, 0, widthInPixels, heightInPixels)


        binding.makeProject.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null)
        binding.makeProject.gravity = Gravity.CENTER_VERTICAL

        binding.makeProject.compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.icon_text_padding)
        binding.makeProject.setOnClickListener{
            val intnet = Intent(context, Group_MainActivity::class.java)
            startActivity(intnet)
        }
        return binding.root
    }
}