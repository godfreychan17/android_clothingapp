package com.godfrey.endclothingtestproject.productlistpage.di

import com.godfrey.endclothingtestproject.productlistpage.viewmodel.ProductListViewModel
import dagger.Component

@Component(modules = [ProductRepositoryModule::class])
interface ProductListViewModelComponent {
    fun inject(productListViewModel: ProductListViewModel)
}