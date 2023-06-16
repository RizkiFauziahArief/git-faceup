package com.example.faceup.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.faceup.R
import com.example.faceup.databinding.FragmentProfileBinding
import com.example.faceup.ui.bottomsheet.product.adapter.ProductAdapter
import com.example.faceup.utils.Product
import com.example.faceup.utils.StoreManager
import com.example.faceup.utils.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _bindng : FragmentProfileBinding? = null
    private val binding get() = _bindng!!
    private val list = ArrayList<Product>()
    private lateinit var storeManager : StoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindng = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvProduct()
        logout()
    }

    private fun logout () {
        binding.tvLoguut.setOnClickListener { btn ->
            val dataStore : DataStore<Preferences> = requireContext().dataStore
            storeManager = StoreManager.getInstance(dataStore)
            GlobalScope.launch {
                storeManager.deleteToken()

            }
            btn.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }


    private fun getProductItem() : ArrayList<Product>{
        val namaProduct = "Acne"
        val description = "this type acne commonly happend to of us because cannon event"
        val gambar = R.drawable.crew
        val listProduct = ArrayList<Product>()

        for (i in 0..10){
            val product = Product(namaProduct,description,gambar)
            listProduct.add(product)
        }

        return listProduct
    }


    private fun setRvProduct (){
        list.addAll(getProductItem())
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        val adapter= ProductAdapter(list)
        binding.rvHistory.adapter = adapter
    }

}