package com.example.faceup.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.faceup.R
import com.example.faceup.databinding.FragmentBottomSheetProductBinding
import com.example.faceup.databinding.FragmentDetailArticleBinding
import com.example.faceup.utils.Article

class DetailArticleFragment : Fragment() {
    private var _binding  : FragmentDetailArticleBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var artikel: Article? = arguments?.getParcelable("artikel")

        binding.imageView.setImageResource(artikel?.photo!!)
        binding.textViewTitle.text = artikel.tittle
        binding.textViewDescription.text = artikel.description


    }

}