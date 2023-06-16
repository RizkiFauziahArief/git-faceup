package com.example.faceup.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.faceup.R
import com.example.faceup.ui.detail.DetailFragment
import com.example.faceup.ui.homepage.HomePage

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = DetailFragment() // Ganti dengan Fragment yang ingin ditampilkan
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()

    }
}