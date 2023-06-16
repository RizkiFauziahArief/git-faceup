package com.example.faceup.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.faceup.R
import com.example.faceup.databinding.FragmentRegisterBinding
import com.example.faceup.network.models.register.RegisterBody
import com.example.faceup.utils.ViewModelFactory
import com.example.faceup.utils.wrapper.Resource
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel : RegisterViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

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
        observeDataRegister()
    }



    private fun playAnimation (){

        val register = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(250)
        val registerDescription = ObjectAnimator.ofFloat(binding.tvLoginDescription, View.ALPHA, 1f).setDuration(250)
        val tvnama = ObjectAnimator.ofFloat(binding.tvName1, View.ALPHA, 1f).setDuration(250)
        val ednama = ObjectAnimator.ofFloat(binding.textInputLayoutName, View.ALPHA, 1f).setDuration(250)
        val emailText  = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(250)
        val emailEd = ObjectAnimator.ofFloat(binding.textInputLayoutEmail, View.ALPHA, 1f).setDuration(250)
        val passText = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(250)
        val passEd = ObjectAnimator.ofFloat(binding.textInputLayoutPassword, View.ALPHA, 1f).setDuration(250)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(250)


        AnimatorSet().apply {
            playSequentially(register,registerDescription, tvnama,ednama, emailText, emailEd, passText,passEd, btnLogin)
            start()
        }
//        setBottomNav()
    }

    private fun observeDataRegister (){
        binding.apply {
            btnRegister.setOnClickListener { btn ->
                val nama = tiName.text.toString().trim()
                val email = tiEmail.text.toString().trim()
                val password = tiPassword.text.toString().trim()
                registerViewModel.postRegist(RegisterBody(nama, email, password))
                progrebarrRegister.isVisible= true
                registerViewModel.regist.observe(viewLifecycleOwner){
                    if (it != null){
                        when(it){
                            is Resource.Error -> {
                                Toast.makeText(requireContext(), it.message.toString() , Toast.LENGTH_LONG).show()
                                progrebarrRegister.isVisible = false
                            }
                            is Resource.Loading -> {
                                Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG).show()
                                progrebarrRegister.isVisible= true
                            }

                            is Resource.Success -> {
                                progrebarrRegister.isVisible = false
                                val data = it.data
                                if (data != null){
                                    if (data?.error == true){
                                        Toast.makeText(requireContext(), "Gagal Register", Toast.LENGTH_LONG).show()
                                    } else{
                                        Toast.makeText(requireContext(), "Sign Up berhasil, silahkan login!", Toast.LENGTH_LONG).show()
                                        btn.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                    }
                                }

                            }
                        }
                    }

                }
            }
        }
    }


}