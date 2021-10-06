package com.godfrey.endclothingtestproject.productlistpage.model

import com.godfrey.endclothingtestproject.R
import com.godfrey.endclothingtestproject.response.ProductResponse

data class ProductListModel(

    var productList : ArrayList<ProductResponse>,
    var numOfProduct : Int,
    var productTitle : String = "",
    var errorMsgResID : Int = R.string.Error_Message
)
