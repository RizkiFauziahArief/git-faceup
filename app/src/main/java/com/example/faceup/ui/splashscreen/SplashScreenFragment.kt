package com.example.faceup.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.faceup.R
import com.example.faceup.databinding.FragmentSplashScreenBinding
import com.example.faceup.utils.StoreManager
import com.example.faceup.utils.dataStore


class SplashScreenFragment : Fragment() {

    private var _binding:FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var storeManager: StoreManager


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
            cekTokenNavigation()
        }, time)
    }
    private fun cekTokenNavigation (){
        val datastore : DataStore<Preferences> = requireContext().dataStore
        storeManager = StoreManager.getInstance(datastore)
        val token = StoreManager.getTokenSynchronously(datastore)
        if (token != null){
            findNavController().navigate(R.id.action_splashScreenFragment_to_homePage)
        } else {
            findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
        }
    }

//

}