package dev.kichan.daseul.ui.main.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.FragmentRegister1Binding
import dev.kichan.daseul.databinding.FragmentRegisterCompleteBinding


class RegisterCompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val binding = FragmentRegisterCompleteBinding.inflate(inflater, container, false)

        val name = (activity as RegisterActivity).Username

        binding.hello.text = "오신 것을 환영해요\n${name}님"

        return binding.root
    }
}