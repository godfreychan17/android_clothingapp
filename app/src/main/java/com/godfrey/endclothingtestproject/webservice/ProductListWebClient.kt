package com.godfrey.endclothingtestproject.webservice

import com.godfrey.endclothingtestproject.response.ProductListResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProductListWebClient : ProductListDataClient {

    var webservice: ProductListWebService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(WebServiceAPI.WEB_SERVICE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        webservice = retrofit.create(ProductListWebService::class.java)
    }

    override fun getProductList(): Single<ProductListResponse> {
        return webservice.getProductList()
    }

    override fun getTestProductList(): Single<ProductListResponse> {
        return webservice.getTestProductList()
    }
}