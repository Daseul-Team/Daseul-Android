package dev.kichan.daseul.ui.main.group

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import dev.kichan.daseul.R
import dev.kichan.daseul.ui.main.MainActivity
import dev.kichan.daseul.ui.main.nav.Main3Fragment

class GroupCompleteFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_group_complete, container, false)
        val invite_id = arguments?.getString("key_invite")
        Log.d("fortest","컴플리트 받은 값 = "+invite_id)
        view.findViewById<TextView>(R.id.txt_invite).text = invite_id
        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener{
            var intent = Intent(context, MainActivity::class.java)
            intent.putExtra("return_value", 1)
            startActivity(intent)
        }
        return view

    }
}