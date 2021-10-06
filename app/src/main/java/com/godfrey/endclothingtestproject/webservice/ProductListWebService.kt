package com.godfrey.endclothingtestproject.webservice

import com.godfrey.endclothingtestproject.response.ProductListResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ProductListWebService {

    @GET("example.json")
    fun getProductList(): Single<ProductListResponse>

    @GET("android_test_example.json")
    fun getTestProductList(): Single<ProductListResponse>

}

