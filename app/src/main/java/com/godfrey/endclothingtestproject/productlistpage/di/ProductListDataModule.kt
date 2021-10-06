package com.godfrey.endclothingtestproject.productlistpage.di

import com.godfrey.endclothingtestproject.webservice.ProductListDataClient
import com.godfrey.endclothingtestproject.webservice.ProductListWebClient
import dagger.Module
import dagger.Provides

@Module
class ProductListDataModule {

    @Provides
    fun provideProductListDataClient(): ProductListDataClient {
        return ProductListWebClient()
    }
}