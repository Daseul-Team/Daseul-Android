package dev.kichan.daseul.ui.main.group

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import dev.kichan.daseul.R
import dev.kichan.daseul.ui.main.MainActivity

class InviteCompleteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_invite_complete, container, false)
        view.findViewById<Button>(R.id.next_btn).setOnClickListener{
            var intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}