package com.godfrey.endclothingtestproject.productlistpage.intent

import com.godfrey.endclothingtestproject.resource.Resources

sealed class ProductListIntent : UIIntent {

    object RefreshProduct : ProductListIntent()
    data class FilterProduct(val sorting: Resources.Sorting) : ProductListIntent()
    data class ChangeLayout(val layoutArrange: Int) : ProductListIntent()

}
