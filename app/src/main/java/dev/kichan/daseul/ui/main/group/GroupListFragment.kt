package dev.kichan.daseul.ui.main.group

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        // 아이콘을 Drawable 리소스에서 가져와서 설정
        val iconDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.icon_make) }

        val widthInPixels = resources.getDimensionPixelSize(R.dimen.icon_width)
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.icon_height)

        iconDrawable?.setBounds(0, 0, widthInPixels, heightInPixels)


        // 왼쪽, 위, 오른쪽, 아래에 아이콘을 추가할 수 있으며 null로 설정하면 아이콘을 추가하지 않습니다.
        binding.makeProject.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null)
        binding.makeProject.gravity = Gravity.CENTER_VERTICAL

        // 텍스트와 아이콘 사이의 간격을 설정 (옵션)
        binding.makeProject.compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.icon_text_padding)
        binding.makeProject.setOnClickListener{
            val intnet = Intent(context, Group_MainActivity::class.java)
            startActivity(intnet)
        }
        return binding.root
    }
}