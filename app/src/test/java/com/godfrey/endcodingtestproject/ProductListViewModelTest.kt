package com.godfrey.endcodingtestproject


import com.godfrey.endclothingtestproject.productlistpage.viewmodel.ProductListViewModel
import com.godfrey.endclothingtestproject.resource.Resources
import com.godfrey.endclothingtestproject.response.ProductResponse
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.lang.reflect.Method

class ProductListViewModelTest {

    var productListViewModel = ProductListViewModel()
    val numOfProductInsert = 100
    val productList = arrayListOf<ProductResponse>()

    @Before
    fun init(){

        for( i in 0 until numOfProductInsert){
            var product = ProductResponse("$i", "Product $i", "£$i", "")
            productList.add(product)
        }
        productListViewModel.getModel().productList.addAll(productList)
    }

    @Test
    fun testSorting(){
        productListViewModel.sortProductListBy(productList, Resources.Sorting.PriceLow)
        assertEquals("0", productList[0].id)

        productListViewModel.sortProductListBy(productList, Resources.Sorting.PriceHigh)
        val largestProductID = numOfProductInsert - 1
        assertEquals("$largestProductID",  productList[0].id)
    }

}