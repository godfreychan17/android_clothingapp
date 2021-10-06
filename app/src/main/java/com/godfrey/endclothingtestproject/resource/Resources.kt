package com.godfrey.endclothingtestproject.resource

import com.godfrey.endclothingtestproject.R

object Resources {

    enum class Sorting(val bitValue: Int, val strResID: Int) {
        Latest(1, R.string.SpinnerSort_Latest),
        PriceHigh(2, R.string.SpinnerSort_PriceHigh),
        PriceLow(4, R.string.SpinnerSort_PriceLow)
    }

    enum class ViewSetting(val bitValue: Int, val strResID: Int) {
        Product(1, R.string.SpinnerView_Product),
        Outfit(2, R.string.SpinnerView_Outfit),
        Large(4, R.string.SpinnerView_Large),
        Small(8, R.string.SpinnerView_Small)
    }

    enum class ImageType{
        Product,
        Outfit
    }
}