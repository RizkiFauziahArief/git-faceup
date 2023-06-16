package com.example.faceup.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    var tittle: String = "",
    var description: String = "",
    var photo: Int = 0
) : Parcelable
