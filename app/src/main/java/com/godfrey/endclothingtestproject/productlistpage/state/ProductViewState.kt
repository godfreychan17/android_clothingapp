package com.godfrey.endclothingtestproject.productlistpage.state

import com.godfrey.endclothingtestproject.resource.Resources
import com.godfrey.endclothingtestproject.response.ProductListResponse

sealed class ProductViewState : UIState {

    object Loading : ProductViewState()

    data class FetchData(val productList: ProductListResponse) : ProductViewState()

    data class RenderViewLayout(val numOfCol: Int, val imageType: Resources.ImageType) : ProductViewState()

    data class Error(val errorMsgResID: Int) : ProductViewState()

    object NoNetworkConnection : ProductViewState()
}

