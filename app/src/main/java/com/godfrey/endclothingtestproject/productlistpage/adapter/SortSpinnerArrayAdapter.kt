package com.godfrey.endclothingtestproject.productlistpage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.godfrey.endclothingtestproject.R

import com.godfrey.endclothingtestproject.resource.Resources


class SortSpinnerArrayAdapter(
    context: Context,
    private val resourceID: Int,
    private val items: Array<Resources.Sorting>,
) :
    ArrayAdapter<Resources.Sorting>(context, resourceID, R.id.SpinnerItem_Title, items) {

    private var selectedItems = Resources.Sorting.Latest.bitValue

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(resourceID, parent, false)

        val item = items[position]

        view.findViewById<TextView>(R.id.SpinnerItem_Title).text = context.getString(item.strResID)

        //Check currently status
        if ((selectedItems and item.bitValue) > 0) {
            //selectedItems = selectedItems and item.bitValue.inv()
            view.setBackgroundResource(R.color.colorSelected)

        } else {
            //selectedItems = selectedItems or item.bitValue
            view.setBackgroundResource(R.color.colorNormal)
        }

        return view
    }

    fun didSelectItem(position: Int): Resources.Sorting {
        selectedItems = items[position].bitValue
        return items[position]
    }

    fun getSelectedItems(): Int {
        return selectedItems
    }
}