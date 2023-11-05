package dev.kichan.daseul.ui.main.group

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.kichan.daseul.R
class Project_ImgFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_project__img, container, false)
        var upload_bgn = view.findViewById<ImageView>(R.id.btn_upload)
        var next_btn = view.findViewById<Button>(R.id.next_btn)
        val name_value = arguments?.getString("key_name")
        Log.d("fortest","이미지 프레그먼트로 받아온 이름 = "+name_value)
        upload_bgn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }
        next_btn.setOnClickListener {
            val activity = requireActivity() as Group_MainActivity// 액티비티 형 변환
            activity.return_imgkey(name_value.toString())
        }

        return view
    }
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        //결과 코드 OK , 결가값 null 아니면
        if(it.resultCode == RESULT_OK && it.data != null){
            //값 담기
            val uri  = it.data!!.data
            Log.d("fortest","imgPath = "+uri)
            val bundle = Bundle()
            bundle.putString("key_img", uri.toString())
            if (activity is Group_MainActivity) {
                (activity as Group_MainActivity).receiveDataFromFragment(bundle)
            }

        }
    }
}

