package dev.kichan.daseul.ui.main.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.FragmentRegister1Binding
import dev.kichan.daseul.databinding.FragmentRegister2Binding

class Register2Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRegister2Binding.inflate(inflater, container, false)

        binding.btNext.setOnClickListener {
            (activity as RegisterActivity).replaceFragment(RegisterCompleteFragment())
            (activity as RegisterActivity).savenumber(binding.etNumber.text.toString())
            (activity as RegisterActivity).registerpost()
        }

        return binding.root
    }
}