package com.godfrey.endclothingtestproject.productlistpage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.godfrey.endclothingtestproject.R
import com.godfrey.endclothingtestproject.resource.Resources

class ViewSpinnerArrayAdapter(
    context: Context,
    private val resourceID: Int,
    private val items: Array<Resources.ViewSetting>,
) :
    ArrayAdapter<Resources.ViewSetting>(context, resourceID, R.id.SpinnerItem_Title, items) {

    private var selectedItems = Resources.ViewSetting.Product.bitValue or Resources.ViewSetting.Small.bitValue

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(resourceID, parent, false)

        val item = items[position]
        view.findViewById<TextView>(R.id.SpinnerItem_Title).text = context.getString(item.strResID)

        if ((selectedItems and item.bitValue) > 0) {
            view.setBackgroundResource(R.color.colorSelected)
            view.findViewById<ImageView>(R.id.SpinnerItem_Tick).visibility = View.VISIBLE

        } else {
            view.setBackgroundResource(R.color.colorNormal)
            view.findViewById<ImageView>(R.id.SpinnerItem_Tick).visibility = View.GONE
        }
        return view
    }

    fun didSelectItem(position: Int): Int {

        selectedItems = selectedItems xor items[position].bitValue

        //uncheck the other option
        selectedItems = when(items[position]){
            Resources.ViewSetting.Product -> selectedItems and Resources.ViewSetting.Outfit.bitValue.inv()
            Resources.ViewSetting.Outfit -> selectedItems and Resources.ViewSetting.Product.bitValue.inv()
            Resources.ViewSetting.Large -> selectedItems and Resources.ViewSetting.Small.bitValue.inv()
            Resources.ViewSetting.Small -> selectedItems and Resources.ViewSetting.Large.bitValue.inv()
        }

        return selectedItems
    }

    fun getSelectedItems() : Int{
        return selectedItems
    }
}