package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class ViewData(
    /**
     * 类型
     * 不同的类型，不同的字段有值
     * 10：ads
     * 20：buttons
     * 30:larges
     * 40:hots
     */
    val style: Int = 10,

//    val ads: List<Ad>? = null,
//    val buttons: List<ButtonViewData>? = null,
//    val larges: List<CropData>? = null,
//    val hots: List<Product>? = null,
    val sheets: List<Sheet>? = null,
    val songs: List<Song>? = null,
)
