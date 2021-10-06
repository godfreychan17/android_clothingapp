package com.godfrey.endclothingtestproject.repository


//import com.godfrey.endclothingtestproject.productlistpage.di.DaggerProductRepositoryComponent
//import com.godfrey.endclothingtestproject.productlistpage.di.DaggerProductRepositoryComponent
//import com.godfrey.endclothingtestproject.productlistpage.di.ProductListDataModule
import com.godfrey.endclothingtestproject.response.ProductListResponse
import com.godfrey.endclothingtestproject.webservice.ProductListDataClient
import io.reactivex.Single
import javax.inject.Inject

interface ProductsRepository {

    fun getProductList(): Single<ProductListResponse>
    fun getTestProductList(): Single<ProductListResponse>
}