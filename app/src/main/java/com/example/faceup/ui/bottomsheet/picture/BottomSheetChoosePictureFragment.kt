package com.example.faceup.ui.bottomsheet.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.faceup.databinding.FragmentBottomSheetChoosePictureBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetChoosePictureFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentBottomSheetChoosePictureBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetChoosePictureBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFlagToDetail()
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val action = BottomSheetChoosePictureFragmentDirections.actionBottomSheetChoosePictureFragmentToDetailFragment2(1)
            findNavController().navigate(action)

        } else {
            // Izin kamera ditolak, berikan penanganan yang sesuai
            Toast.makeText(requireContext(), "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private val galleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val action = BottomSheetChoosePictureFragmentDirections.actionBottomSheetChoosePictureFragmentToDetailFragment2(2)
            findNavController().navigate(action)


        } else {
            // Izin galeri ditolak, berikan penanganan yang sesuai
            Toast.makeText(requireContext(), "Izin galeri ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk meminta izin membuka kamera
    private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    // Fungsi untuk meminta izin membuka galeri
    private fun requestGalleryPermission() {
        galleryPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }


    private fun setFlagToDetail(){
        binding.apply {
            tvCamera.setOnClickListener {
                requestCameraPermission()

            }
            tvGallery.setOnClickListener {
                requestGalleryPermission()
            }
        }
    }
}