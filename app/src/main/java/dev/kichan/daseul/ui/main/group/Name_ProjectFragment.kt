package dev.kichan.daseul.ui.main.group

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import dev.kichan.daseul.R
class Name_ProjectFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_name__project, container, false)
        var name_edit = view.findViewById<EditText>(R.id.input_name)
        var btn_next = view.findViewById<Button>(R.id.next_btn)

        btn_next.setOnClickListener {
            if (name_edit.text.isNullOrEmpty()) {
                Toast.makeText(context, "이름을 입력하세요", Toast.LENGTH_SHORT).show()
            } else{
//                val bundle = Bundle()
//                bundle.putString("key_name", name_edit.text.toString())
//                val myActivity = activity as Group_MainActivity
//                myActivity.get_GroupName(bundle)
                val bundle = Bundle()
                bundle.putString("key_name",name_edit.text.toString())
                Log.d("fortest","이름:"+name_edit.text.toString())
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                val newFragment = Project_ImgFragment()
                newFragment.arguments = bundle
                transaction.replace(R.id.group_frame, newFragment)
                transaction.commit()
            }
        }
        return view
    }
}