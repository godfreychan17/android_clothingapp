package com.godfrey.endclothingtestproject.productlistpage.view


import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.endclothingtestproject.R
import com.godfrey.endclothingtestproject.databinding.ActivityProductlistBinding
import com.godfrey.endclothingtestproject.productlistpage.adapter.ProductListAdapter
import com.godfrey.endclothingtestproject.productlistpage.adapter.ProductListHeaderAdapter
import com.godfrey.endclothingtestproject.productlistpage.adapter.SortSpinnerArrayAdapter
import com.godfrey.endclothingtestproject.productlistpage.adapter.ViewSpinnerArrayAdapter
import com.godfrey.endclothingtestproject.productlistpage.intent.ProductListIntent
import com.godfrey.endclothingtestproject.productlistpage.state.ProductViewState
import com.godfrey.endclothingtestproject.productlistpage.viewmodel.ProductListViewModel
import com.godfrey.endclothingtestproject.resource.Resources
import com.godfrey.endclothingtestproject.response.ProductListResponse
import com.godfrey.endclothingtestproject.viewmodelfactory.ProductViewModelFactory

class ProductListActivity : AppCompatActivity() {

    private lateinit var productListViewModel: ProductListViewModel

    private lateinit var binding: ActivityProductlistBinding
    private lateinit var productListRecyclerView: RecyclerView

    private val productListAdapter = ProductListAdapter(arrayListOf())
    private val productListHeaderAdapter = ProductListHeaderAdapter(ProductListResponse())

    private lateinit var sortSpinner: Spinner
    private lateinit var sortSpinnerArrayAdapter: SortSpinnerArrayAdapter

    private lateinit var viewSettingSpinner: Spinner
    private lateinit var viewSettingSpinnerArrayAdapter: ViewSpinnerArrayAdapter

    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        productListViewModel.attachView(this)
    }

    fun getViewSpinnerArrayAdapter() : ViewSpinnerArrayAdapter {
        return viewSettingSpinnerArrayAdapter
    }

    fun getSortSpinnerArrayAdapter() : SortSpinnerArrayAdapter {
        return sortSpinnerArrayAdapter
    }

    fun getGridLayoutManager() : GridLayoutManager {
        return gridLayoutManager
    }

    override fun onPause() {
        super.onPause()
        productListViewModel.detachView()
    }

    private fun setupUI() {
        binding = ActivityProductlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init recycler view
        productListRecyclerView = binding.ProductListRecyclerView
        gridLayoutManager = createGridLayoutManager()
        productListRecyclerView.layoutManager = gridLayoutManager

        val concatAdapter = ConcatAdapter(productListHeaderAdapter, productListAdapter)
        productListRecyclerView.adapter = concatAdapter

        initSpinner()

        binding.ProductListFilterButton.setOnClickListener {
            binding.root.openDrawer(Gravity.RIGHT)
        }
    }

    private fun initSpinner() {
        //Init Sort spinner
        sortSpinner = binding.ProductListSortSpinner

        val sortVto: ViewTreeObserver = sortSpinner.viewTreeObserver
        sortVto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    sortSpinner.viewTreeObserver.removeOnGlobalLayoutListener { }
                } else {
                    sortSpinner.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                sortSpinner.dropDownWidth = sortSpinner.measuredWidth
            }
        })

        sortSpinnerArrayAdapter =
            SortSpinnerArrayAdapter(this, R.layout.spinneritem_sort, enumValues())
        sortSpinner.adapter = sortSpinnerArrayAdapter

        sortSpinner.setSelection(0, false)
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val sorting = sortSpinnerArrayAdapter.didSelectItem(position)
                productListViewModel.intentToAction(ProductListIntent.FilterProduct(sorting))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        viewSettingSpinner = binding.ProductListViewSpinner

        val viewVto: ViewTreeObserver = viewSettingSpinner.viewTreeObserver
        viewVto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    viewSettingSpinner.viewTreeObserver.removeOnGlobalLayoutListener { }
                } else {
                    viewSettingSpinner.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                viewSettingSpinner.dropDownWidth = viewSettingSpinner.measuredWidth
            }
        })

        viewSettingSpinnerArrayAdapter =
            ViewSpinnerArrayAdapter(this, R.layout.spinneritem_viewsetting, enumValues())
        viewSettingSpinner.adapter = viewSettingSpinnerArrayAdapter

        viewSettingSpinner.setSelection(0, false)
        viewSettingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val viewSetting = viewSettingSpinnerArrayAdapter.didSelectItem(position)
                productListViewModel.intentToAction(ProductListIntent.ChangeLayout(viewSetting))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun createGridLayoutManager(): GridLayoutManager {
        val mLayoutManager = GridLayoutManager(this, 2)

        mLayoutManager.spanSizeLookup = (object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> mLayoutManager.spanCount
                    else -> 1
                }
            }
        })
        return mLayoutManager
    }

    private fun setupViewModel() {
        productListViewModel =
            ViewModelProvider(this, ProductViewModelFactory()).get(ProductListViewModel::class.java)
    }

    private fun setupObserver() {
        productListViewModel.getStateSender().observe(this,
            {
                when (it) {
                    is ProductViewState.Loading -> {
                        showLoading()
                    }

                    is ProductViewState.FetchData -> {
                        fetchProductData(it.productList)
                    }

                    is ProductViewState.RenderViewLayout -> {
                        renderView(it.numOfCol, it.imageType)
                    }

                    is ProductViewState.Error -> {
                        showErrorDialog(it.errorMsgResID)
                    }

                    is ProductViewState.NoNetworkConnection -> {
                        showErrorDialog(R.string.No_Network_Message)
                    }
                }
            }
        )
    }

    private fun showErrorDialog(resID: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(getString(resID))
        alertDialog.setNeutralButton(R.string.Dialog_Retry) { dialog, which ->
            productListViewModel.intentToAction(ProductListIntent.RefreshProduct)
        }
        alertDialog.show()
    }

    private fun showLoading() {
        binding.ProductListLoadingView.visibility = View.VISIBLE
    }

    private fun fetchProductData(productInfo: ProductListResponse) {
        binding.ProductListLoadingView.visibility = View.GONE
        gridLayoutManager.scrollToPositionWithOffset(0, 0)

        productListHeaderAdapter.updateInfo(productInfo)
        productListAdapter.updateData(productInfo.products)

        productListAdapter.notifyDataSetChanged()
    }

    private fun renderView(numOfCol: Int, imageType: Resources.ImageType) {
        gridLayoutManager.spanCount = numOfCol

        productListAdapter.updateItemImageType(imageType)
        productListAdapter.notifyDataSetChanged()
        gridLayoutManager.scrollToPositionWithOffset(0, 0)
    }
}