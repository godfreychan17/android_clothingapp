package com.godfrey.endclothingtestproject.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("price")
    val price: String = "",

    @SerializedName("image")
    val image: String = ""
)
