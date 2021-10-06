package com.godfrey.endclothingtestproject.productlistpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.godfrey.endclothingtestproject.databinding.ItemProductBinding
import com.godfrey.endclothingtestproject.resource.Resources
import com.godfrey.endclothingtestproject.response.ProductResponse
import java.util.*

class ProductListAdapter(private val items: ArrayList<ProductResponse>) :
    RecyclerView.Adapter<ProductListAdapter.DataViewHolder>() {

    var showImageType = Resources.ImageType.Product

    class DataViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductResponse) {
            binding.ProductItemTitle.text = item.name
            binding.ProductItemPrice.text = item.price

            Glide.with(binding.root)
                .load(item.image)
                .centerInside()
                .into(binding.ProductItemThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun updateData(list: List<ProductResponse>) {
        items.clear()
        items.addAll(list)
    }

    fun updateItemImageType(imageType: Resources.ImageType){
        showImageType = imageType
    }

}