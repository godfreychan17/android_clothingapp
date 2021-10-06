package com.godfrey.endclothingtestproject.repository

import com.godfrey.endclothingtestproject.productlistpage.di.DaggerProductRepositoryComponent
import com.godfrey.endclothingtestproject.productlistpage.di.ProductListDataModule
import com.godfrey.endclothingtestproject.response.ProductListResponse
import com.godfrey.endclothingtestproject.webservice.ProductListDataClient
import io.reactivex.Single
import javax.inject.Inject

class ProductsRepositoryImpl : ProductsRepository{

    @Inject
    lateinit var productListDataClient: ProductListDataClient

    init {
        DaggerProductRepositoryComponent.builder()
            .productListDataModule(ProductListDataModule())
            .build()
            .inject(this)
    }

    override fun getProductList(): Single<ProductListResponse> {
        return productListDataClient.getProductList()
    }

    override fun getTestProductList(): Single<ProductListResponse> {
        return productListDataClient.getTestProductList()
    }

}