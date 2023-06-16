package com.example.faceup.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.faceup.R
import com.example.faceup.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private var _binding:FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val time = 2000L
        Handler().postDelayed({
          findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
        }, time)
    }
}