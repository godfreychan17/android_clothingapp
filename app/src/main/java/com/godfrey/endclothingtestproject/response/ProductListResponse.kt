package com.godfrey.endclothingtestproject.response

import com.google.gson.annotations.SerializedName

data class ProductListResponse(

    @SerializedName("products")
    val products: ArrayList<ProductResponse> = arrayListOf(),

    @SerializedName("title")
    val title: String = "",

    @SerializedName("product_count")
    val product_count: Int = 0
)
