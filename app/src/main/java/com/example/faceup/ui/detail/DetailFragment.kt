package com.example.faceup.ui.detail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.faceup.R
import com.example.faceup.databinding.FragmentDetailBinding
import com.example.faceup.utils.*
import com.example.faceup.utils.wrapper.Resource
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val navArgs : DetailFragmentArgs by navArgs()
    private val CAMERA_PERMISSION_CODE = 100
    private var getFile: File? = null
    private lateinit var pathPhoto: String
    private val detailViewModel : DetailViewModel by viewModels {
        ViewModelFactory(requireContext())
    }


    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val myFile = File(pathPhoto)
            getFile = myFile


            val result = BitmapFactory.decodeFile(getFile?.path)
            if (result==null){
                Log.e("Camera" , "Tidak ada file camera")
            }
            with(binding.layoutDetailContent){
                imgPpDetail.setImageBitmap(result)
            }
            uploadImage()

        }
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            getFile = myFile

            with(binding.layoutDetailContent){
                imgPpDetail.setImageURI(selectedImg)
            }

            uploadImage()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomSheet()
        chosePicture()
        showLoading(true)

    }



    private fun uploadImage (){
            val gambar = getFile?.let { it1 -> imageMultipart(it1) }
            if (gambar != null) {
                detailViewModel.postPredictDetail(gambar)
                observeDataDetail()
            } else {
                Toast.makeText(requireContext(),"Gambar null" ,Toast.LENGTH_SHORT).show()
            }
    }

    private fun showLoading(boolean : Boolean){
        binding.layoutDetailContentShimmer.isVisible = boolean
        binding.layoutDetailContent.constarintLayout.isVisible = !boolean

    }

    private fun observeDataDetail(){
        binding.apply {
            detailViewModel.detail.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                           showLoading(true)
                            Log.e("Loading" ,"on Resource Loading")

                        }
                        is Resource.Error -> {
                            showLoading(false)
                            Log.e("Error" ,it.message.toString())
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            Log.i("Resource Succses" ,it.message.toString())
                           with(binding.layoutDetailContent){
                               val data = it.data
                               when (data?.predictedClass) {
                                   // 0. Blackhead
                                   0 -> {
                                       tvTittleJenisJerawat.text = data.classJerawat.toString()
                                       tvDes.text = getString(R.string.desc_blackheads)
                                       tvPenanganan1.text = getString(R.string.pen_blackheads_1)
                                       tvPenanganan2.text = getString(R.string.pen_blackheads_2)
                                       tvPenanganan3.text = getString(R.string.pen_blackheads_3)
                                       tvPenanganan4.text = getString(R.string.pen_blackheads_4)

                                   }
                                   // 1. Nodules
                                   1 -> {
                                       tvTittleJenisJerawat.text = data.classJerawat.toString()
                                       tvDes.text = getString(R.string.desc_nodules)
                                       tvPenanganan1.text = getString(R.string.pen_nodules_1)
                                       tvPenanganan2.text = getString(R.string.pen_nodules_2)
                                       tvPenanganan3.text = getString(R.string.pen_nodules_3)
                                       tvPenanganan4.text = getString(R.string.pen_nodules_4)

                                   }
                                   //    2. Papule
                                   2 -> {
                                       tvTittleJenisJerawat.text = data.classJerawat.toString()
                                       tvDes.text = getString(R.string.desc_papule)
                                       tvPenanganan1.text = getString(R.string.pen_papule_1)
                                       tvPenanganan2.text = getString(R.string.pen_papule_2)
                                       tvPenanganan3.text = getString(R.string.pen_papule_3)
                                       tvPenanganan4.text = getString(R.string.pen_papule_4)

                                   }
                                   //    3. Pustules
                                   3 -> {
                                       tvTittleJenisJerawat.text = data.classJerawat.toString()
                                       tvDes.text = getString(R.string.desc_pustules)
                                       tvPenanganan1.text = getString(R.string.pen_pustules_1)
                                       tvPenanganan2.text = getString(R.string.pen_pustules_2)
                                       tvPenanganan3.text = getString(R.string.pen_pustules_3)
                                       tvPenanganan4.text = getString(R.string.pen_pustules_4)

                                   }
                                   //    4. Whitehead
                                   4 -> {
                                       tvTittleJenisJerawat.text = data.classJerawat.toString()
                                       tvDes.text = getString(R.string.desc_whitehead)
                                       tvPenanganan1.text = getString(R.string.pen_whitehead_1)
                                       tvPenanganan2.text = getString(R.string.pen_whitehead_2)
                                       tvPenanganan3.text = getString(R.string.pen_whitehead_3)
                                       tvPenanganan4.text = getString(R.string.pen_whitehead_4)

                                   }
                               }
                           }

                        }
                    }
                }
            }
        }
    }

    private fun chosePicture(){
        var flag = navArgs.flag
        when(flag){
            1 -> {
                checkCameraPermission()

            }
            2 ->{
                startGallery()

            }
            else -> {
            }
        }

    }



    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Jika izin tidak diberikan, minta izin
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            // Izin telah diberikan, lanjutkan dengan aksi yang diperlukan
            launchCamera()
        }
    }


    private fun launchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                Constants.MY_PACKAGE,
                it
            )
            pathPhoto = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun showBottomSheet(){
        with(binding.layoutDetailContent){
            extendedFabBuyRecomenProdcut.setOnClickListener {
                findNavController().navigate(R.id.bottomSheetProductFragment)
            }
        }
    }


}