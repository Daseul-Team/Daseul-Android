package dev.kichan.daseul.ui.main.group

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import dev.kichan.daseul.R
class GroupCompleteFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_group_complete, container, false)
        var invite_id = Bundle().getString("key_invite")
        view.findViewById<TextView>(R.id.txt_invite).text = invite_id
        return view
        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener{
            var intent = Intent(context, Group_MainActivity::class.java)
            startActivity(intent)
        }
    }
}