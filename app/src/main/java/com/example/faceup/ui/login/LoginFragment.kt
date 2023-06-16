package com.example.faceup.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.findNavController
import com.example.faceup.R

import com.example.faceup.databinding.FragmentLoginBinding
import com.example.faceup.network.models.login.LoginBody
import com.example.faceup.utils.StoreManager
import com.example.faceup.utils.ViewModelFactory
import com.example.faceup.utils.dataStore
import com.example.faceup.utils.wrapper.Resource
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var storeManager: StoreManager
    private val loginViewModel : LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        emailFocusListener()
        passwordValidation()
        playAnimation()
        toregister()
        observeData()
    }

    private fun playAnimation (){
        ObjectAnimator.ofFloat(binding.imgLogin, View.TRANSLATION_X , -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(250)
        val loginDescription = ObjectAnimator.ofFloat(binding.tvLoginDescription, View.ALPHA, 1f).setDuration(250)
        val emailText  = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(250)
        val emailEd = ObjectAnimator.ofFloat(binding.textInputLayoutEmail, View.ALPHA, 1f).setDuration(250)
        val passText = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(250)
        val passEd = ObjectAnimator.ofFloat(binding.textInputLayoutPassword, View.ALPHA, 1f).setDuration(250)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(250)
        val dontHvAkunText = ObjectAnimator.ofFloat(binding.tvDontHa, View.ALPHA, 1f).setDuration(250)
        val regisText = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(250)

        AnimatorSet().apply {
            playSequentially(login,loginDescription, emailText, emailEd, passText,passEd, btnLogin, dontHvAkunText, regisText)
            start()
        }
//        setBottomNav()
    }
    private fun passwordValidation(){
        binding.tiPassword.doOnTextChanged { text, start, before, count ->
            if(text!!.length < 8){
                binding.textInputLayoutPassword.error = "Passoword must be more 8 Characters"
            } else if(text.length >= 8) {
                binding.textInputLayoutPassword.error = null
                binding.textInputLayoutPassword.helperText = null
            }
        }
    }

    private fun emailFocusListener(){
        binding.tiEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                binding.textInputLayoutEmail.helperText =validemail()
            }
        }
    }

    private fun validemail() : String?{
        val emailText = binding.tiEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return  "Invalid Email Address"
        }
        return null
    }

    private fun toregister(){
        binding.tvRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeData (){
        binding.apply {
            btnLogin.setOnClickListener { btn ->
                val email = tiEmail.text.toString().trim()
                val password = tiPassword.text.toString().trim()
                val dataStore : DataStore<Preferences> = requireContext().dataStore
                storeManager = StoreManager.getInstance(dataStore)
                loginViewModel.Postlogin(LoginBody(email,password))
                progrebarrLogin.isVisible = true
                loginViewModel.login.observe(viewLifecycleOwner){
                    if (it != null){
                        when(it){
                            is Resource.Loading -> {

                            }
                            is Resource.Error ->{
                                progrebarrLogin.isVisible = false
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            }
                            is Resource.Success -> {
                                Log.e("ProgresBar LOADING" , "MASUUUKKKKK SUCCSESS")
                                progrebarrLogin.isVisible = true
                                val data = it.data
                                if (data?.error == true) {
                                    Toast.makeText(requireContext(), data?.message.toString(), Toast.LENGTH_LONG).show()
                                }
                                else{
                                    data?.token?.let {tokenLogin ->
                                        lifecycleScope.launch{
                                            Log.i("LoginFragment", "token: $tokenLogin")
                                            storeManager.saveToken(tokenLogin)
                                        }
                                    }
                                    Toast.makeText(requireContext(), "Succsess Login", Toast.LENGTH_LONG).show()
                                    val action  = LoginFragmentDirections.actionLoginFragmentToHomePage(data?.nama.toString(),data?.email.toString())
                                    btn.findNavController().navigate(action)

                                }


                            }

                        }
                    }
                }
            }
        }
    }

    private fun onBackPressed() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}