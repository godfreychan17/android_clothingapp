package com.godfrey.endclothingtestproject.productlistpage.di

import com.godfrey.endclothingtestproject.repository.ProductsRepositoryImpl
import dagger.Component

@Component(modules = [ProductListDataModule::class])
interface ProductRepositoryComponent {
    fun inject(productsRepository: ProductsRepositoryImpl)
}