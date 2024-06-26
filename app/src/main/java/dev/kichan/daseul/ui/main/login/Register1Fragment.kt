package dev.kichan.daseul.ui.main.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.FragmentRegister1Binding
import dev.kichan.daseul.ui.main.MainActivity


class Register1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRegister1Binding.inflate(inflater, container, false)

        binding.btNext.setOnClickListener {
            (activity as RegisterActivity).replaceFragment(Register2Fragment())
            (activity as RegisterActivity).savename(binding.etName.text.toString())
        }
        return binding.root
    }
}