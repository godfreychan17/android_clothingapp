package com.godfrey.endclothingtestproject.productlistpage.di


import com.godfrey.endclothingtestproject.repository.ProductsRepository
import com.godfrey.endclothingtestproject.repository.ProductsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ProductRepositoryModule {
    @Provides
    fun provideProductRepositoryModule(): ProductsRepository{
        return ProductsRepositoryImpl()
    }
}