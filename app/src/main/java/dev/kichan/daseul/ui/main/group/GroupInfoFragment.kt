package dev.kichan.daseul.ui.main.group

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import dev.kichan.daseul.BuildConfig
import dev.kichan.daseul.R
import dev.kichan.daseul.ui.main.MainActivity
import org.w3c.dom.Text

class GroupInfoFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_info, container, false)
        val dataList = arguments?.getStringArrayList("deep_data")
        Log.d("fortest","딥링크 받아온 값 = "+dataList)
        val token = arguments?.getString("key_Token")
        val imageUrl = "${BuildConfig.BASE_URL}"+"file/download/"+ dataList!![1]
        view.findViewById<TextView>(R.id.groupTitle).text = dataList[0]+"\n프로젝트에 초대되었습니다."
        view.findViewById<TextView>(R.id.by_who).text = dataList[2]+"이(가) 초대했어요."
        val headers = LazyHeaders.Builder()
            .addHeader("Authorization", token.toString())
            .build()

        val glideUrl = GlideUrl(imageUrl, headers)

        Glide.with(view.context)
            .load(glideUrl)
            .override(200, 200)
            .transform(CenterCrop(), CircleCrop())
            .into(view.findViewById<ImageView>(R.id.Group_image))
        view.findViewById<Button>(R.id.next_btn).setOnClickListener{
            (activity as Group_MainActivity).JoinGroup(dataList[3])
        }
        return view
    }
}