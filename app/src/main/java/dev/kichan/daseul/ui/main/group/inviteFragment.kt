package dev.kichan.daseul.ui.main.group

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import dev.kichan.daseul.R

class inviteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_invite, container, false)
        var txt_invite = view.findViewById<EditText>(R.id.txt_invite)
        var btn_next = view.findViewById<ImageView>(R.id.next_btn)
        btn_next.setOnClickListener {
            if(txt_invite.text.isNullOrEmpty()){
                Toast.makeText(context,"참가코드를 입력해주세요",Toast.LENGTH_SHORT).show()
            }else{
                if (activity is Group_MainActivity) {
                    (activity as Group_MainActivity).JoinGroup(txt_invite.text.toString())
                }
            }
        }

        return view
    }
}