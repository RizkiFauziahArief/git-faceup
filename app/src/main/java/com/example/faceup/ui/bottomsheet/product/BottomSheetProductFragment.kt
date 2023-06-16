package com.example.faceup.ui.bottomsheet.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.faceup.R
import com.example.faceup.databinding.FragmentBottomSheetProductBinding
import com.example.faceup.ui.bottomsheet.product.adapter.ProductAdapter
import com.example.faceup.utils.Product
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetProductFragment : BottomSheetDialogFragment() {
    private var _binding  : FragmentBottomSheetProductBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvProduct()
    }

    private fun getProductItem() : ArrayList<Product>{
        val namaProduct = "SageCare"
        val description = "ini adalah produk yang aman untuk luka jerawat anda"
        val gambar = R.drawable.makeup_pouch
        val listProduct = ArrayList<Product>()

        for (i in 0..10){
            val product = Product(namaProduct,description,gambar)
            listProduct.add(product)
        }

        return listProduct
    }


    private fun setRvProduct (){
        list.addAll(getProductItem())
        binding.rvProductRekomendasi.layoutManager = LinearLayoutManager(requireContext())
        val adapter= ProductAdapter(list)
        binding.rvProductRekomendasi.adapter = adapter
    }


}