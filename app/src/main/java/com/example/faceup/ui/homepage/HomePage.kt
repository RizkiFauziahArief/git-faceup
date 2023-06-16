package com.example.faceup.ui.homepage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.faceup.R
import com.example.faceup.databinding.FragmentHomePageBinding
import com.example.faceup.ui.bottomsheet.product.adapter.ProductAdapter
import com.example.faceup.ui.homepage.adapter.HomeAdapter
import com.example.faceup.ui.profile.ProfileFragment
import com.example.faceup.utils.Product
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomePage : Fragment() {

    private var _binding : FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private val navArgs : HomePageArgs by navArgs()
    private val list = ArrayList<Product>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nama = navArgs.name.toString()
        binding.tvName.text = nama
        onBackPressed()
        moveToDetail()
        setRvProduct()
        futureWork()

    }

    private fun getProductItem() : ArrayList<Product>{
        val namaProduct = "Acne"
        val description = "this type acne commonly happend to of us because cannon event"
        val gambar = R.drawable.acne_example
        val listProduct = ArrayList<Product>()

        for (i in 0..10){
            val product = Product(namaProduct,description,gambar)
            listProduct.add(product)
        }

        return listProduct
    }


    private fun setRvProduct (){
        list.addAll(getProductItem())
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter= HomeAdapter(list)
        binding.rvArticle.adapter = adapter
    }

    private fun futureWork(){
        binding.cvKlinik.setOnClickListener() {
            findNavController().navigate(R.id.action_homePage_to_klinikFragment)
        }
        binding.cvKonsul.setOnClickListener(){
            findNavController().navigate(R.id.action_homePage_to_konsulFragment)
        }
    }

    private fun customDialog(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)

        val btnOk = dialog.findViewById<Button>(R.id.buttonOke)
        btnOk.setOnClickListener{
            dialog.dismiss()
            findNavController().navigate(R.id.bottomSheetChoosePictureFragment)
        }
        dialog.show()
    }

    private fun moveToDetail (){
        binding.buttonFloatCam.setOnClickListener {
            customDialog()
        }
    }

    private fun onBackPressed() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

}