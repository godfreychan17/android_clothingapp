package com.godfrey.endclothingtestproject.webservice

import com.godfrey.endclothingtestproject.response.ProductListResponse
import io.reactivex.Single

interface ProductListDataClient{

    fun getProductList(): Single<ProductListResponse>

    fun getTestProductList(): Single<ProductListResponse>
}