package com.godfrey.endclothingtestproject.productlistpage.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.godfrey.endclothingtestproject.productlistpage.di.DaggerProductListViewModelComponent
import com.godfrey.endclothingtestproject.productlistpage.di.ProductRepositoryModule
//import com.godfrey.endclothingtestproject.productlistpage.di.DaggerProductListViewModelComponent
//import com.godfrey.endclothingtestproject.productlistpage.di.ProductRepositoryModule
import com.godfrey.endclothingtestproject.productlistpage.intent.ProductListIntent
import com.godfrey.endclothingtestproject.productlistpage.model.ProductListModel
import com.godfrey.endclothingtestproject.repository.ProductsRepository
import com.godfrey.endclothingtestproject.productlistpage.state.ProductViewState
import com.godfrey.endclothingtestproject.resource.Resources
import com.godfrey.endclothingtestproject.response.ProductListResponse
import com.godfrey.endclothingtestproject.response.ProductResponse
import com.godfrey.endclothingtestproject.util.NetworkUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


//class ProductListViewModel(private val productResponse: ProductsRepository) : ViewModel() {
class ProductListViewModel() : ViewModel() {

    @Inject
    lateinit var productResponse: ProductsRepository

    private val viewStateSender = MutableLiveData<ProductViewState>()
    private val compositeDisposable = CompositeDisposable()

    private val productListModel = ProductListModel(arrayListOf(), 0)
    private var mContext: Context? = null

    init {
        DaggerProductListViewModelComponent.builder()
            .productRepositoryModule(ProductRepositoryModule())
            .build()
            .inject(this)
    }

    fun attachView(context: Context) {
        mContext = context
        fetchData()
    }

    fun detachView() {
        compositeDisposable.dispose()
    }

    fun getStateSender(): MutableLiveData<ProductViewState> {
        return viewStateSender
    }

    fun intentToAction(intent: ProductListIntent) {
        when (intent) {
            is ProductListIntent.FilterProduct -> {
                sortingData(intent.sorting)
            }

            is ProductListIntent.ChangeLayout -> {
                changeLayoutSetting(intent.layoutArrange)
            }

            is ProductListIntent.RefreshProduct -> {
                fetchData()
            }
        }
    }

    private fun changeLayoutSetting(arrangement: Int) {
        var numOfCol = 2
        if (arrangement and Resources.ViewSetting.Large.bitValue > 0) {
            numOfCol = 1
        }

        var imageType = Resources.ImageType.Product
        if (arrangement and Resources.ViewSetting.Outfit.bitValue > 0) {
            imageType = Resources.ImageType.Outfit
        }

        val sortingSingle = Single.just(ProductViewState.RenderViewLayout(numOfCol, imageType))
        sortingSingle.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewStateSender.postValue(it)
            },
                {
                    viewStateSender.postValue(ProductViewState.Error(productListModel.errorMsgResID))
                })
    }

    private fun sortingData(sort: Resources.Sorting) {
        //Generally need to fire api to do the sorting (as client side will not have the full list)
        val sortingSingle = Single.just(productListModel.productList)
        sortingSingle.subscribeOn(Schedulers.io())
            .doOnSubscribe {
                viewStateSender.postValue(ProductViewState.Loading)
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sortProductListBy(it, sort)
                viewStateSender.postValue(ProductViewState.FetchData(ProductListResponse(
                    productListModel.productList,
                    productListModel.productTitle,
                    productListModel.numOfProduct)))

            }, {
                it.printStackTrace()
                viewStateSender.postValue(ProductViewState.Error(productListModel.errorMsgResID))
            })
    }

    fun sortProductListBy(productList: ArrayList<ProductResponse>, sort: Resources.Sorting) {
        when (sort) {
            Resources.Sorting.PriceHigh -> productList.sortByDescending { product -> product.price }
            Resources.Sorting.PriceLow -> productList.sortBy { product -> product.price }
        }
    }

    private fun fetchData() {
        if (mContext != null && NetworkUtil.getIsNetworkAvailable(mContext!!)) {
            productListModel.productList.clear()
            productListModel.numOfProduct = 0
            var serviceCount = 0

            compositeDisposable.add(
                Single.mergeDelayError(
                    productResponse.getProductList(),
                    productResponse.getTestProductList()
                )
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        viewStateSender.postValue(ProductViewState.Loading)
                    }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("ProductListViewModel", "product finish ")
                        productListModel.productList.addAll(it.products)
                        productListModel.numOfProduct += it.product_count

                        serviceCount++
                        if (serviceCount == 2) {

                            productListModel.productList.sortByDescending { product -> product.id }

                            viewStateSender.postValue(ProductViewState.FetchData(ProductListResponse(
                                productListModel.productList,
                                it.title,
                                productListModel.numOfProduct)))
                        }
                    }, {
                        it.printStackTrace()
                        viewStateSender.postValue(ProductViewState.Error(productListModel.errorMsgResID))
                    })
            )
        } else {
            val sortingSingle = Single.just(ProductViewState.Error(productListModel.errorMsgResID))
            sortingSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewStateSender.postValue(it)
                },
                    {
                        viewStateSender.postValue(ProductViewState.Error(productListModel.errorMsgResID))
                    })
        }
    }

    fun getModel(): ProductListModel {
        return productListModel
    }
}