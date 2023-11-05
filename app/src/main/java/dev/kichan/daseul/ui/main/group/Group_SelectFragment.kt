package dev.kichan.daseul.ui.main.group

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import dev.kichan.daseul.R
class Group_SelectFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.fragment_group__select, container, false)
        var btn_make = view.findViewById<ImageView>(R.id.btn_make)
        var btn_participate = view.findViewById<ImageView>(R.id.btn_participate)
        btn_make.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val newFragment = Name_ProjectFragment()
            transaction.replace(R.id.group_frame, newFragment)
            transaction.commit()
        }
        btn_participate.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val newFragment = inviteFragment()
            transaction.replace(R.id.group_frame, newFragment)
            transaction.commit()
        }
        return view
    }
}