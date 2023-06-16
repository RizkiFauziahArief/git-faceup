package com.example.faceup.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.faceup.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playAnimation ()
    }

    private fun playAnimation (){

        val register = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(250)
        val registerDescription = ObjectAnimator.ofFloat(binding.tvLoginDescription, View.ALPHA, 1f).setDuration(250)
        val tvnama = ObjectAnimator.ofFloat(binding.tvName1, View.ALPHA, 1f).setDuration(250)
        val ednama = ObjectAnimator.ofFloat(binding.textInputLayoutName, View.ALPHA, 1f).setDuration(250)
        val numberphone = ObjectAnimator.ofFloat(binding.tvNumberphone1, View.ALPHA, 1f).setDuration(250)
        val ednumberphone = ObjectAnimator.ofFloat(binding.textInputLayoutNumberPhone, View.ALPHA, 1f).setDuration(250)
        val emailText  = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(250)
        val emailEd = ObjectAnimator.ofFloat(binding.textInputLayoutEmail, View.ALPHA, 1f).setDuration(250)
        val passText = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(250)
        val passEd = ObjectAnimator.ofFloat(binding.textInputLayoutPassword, View.ALPHA, 1f).setDuration(250)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(250)
//        val dontHvAkunText = ObjectAnimator.ofFloat(binding.tvDontHa, View.ALPHA, 1f).setDuration(250)
//        val regisText = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(250)

        AnimatorSet().apply {
            playSequentially(register,registerDescription, tvnama,ednama,numberphone,ednumberphone, emailText, emailEd, passText,passEd, btnLogin)
            start()
        }
    }
}