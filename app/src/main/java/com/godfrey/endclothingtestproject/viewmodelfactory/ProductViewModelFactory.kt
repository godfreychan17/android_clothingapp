package com.godfrey.endclothingtestproject.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godfrey.endclothingtestproject.productlistpage.viewmodel.ProductListViewModel
import java.lang.IllegalArgumentException

class ProductViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            return ProductListViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}