package com.godfrey.endclothingtestproject.productlistpage.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.endclothingtestproject.R
import com.godfrey.endclothingtestproject.databinding.ItemHeaderofproductBinding
import com.godfrey.endclothingtestproject.response.ProductListResponse

class ProductListHeaderAdapter(private var info: ProductListResponse) :
    RecyclerView.Adapter<ProductListHeaderAdapter.HeaderViewHolder>() {

    class HeaderViewHolder(private val binding: ItemHeaderofproductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind( info: ProductListResponse) {

            val numOfProduct = info.product_count
            val unitString = binding.root.context.getString(R.string.num_of_product_unit)

            binding.ProductHeaderNumOfProduct.text = "$numOfProduct $unitString"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder =
        HeaderViewHolder(
            ItemHeaderofproductBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) =
        holder.bind(info)

    override fun getItemCount(): Int = 1

    fun updateInfo (productInfo:ProductListResponse) {
        info = productInfo
    }
}