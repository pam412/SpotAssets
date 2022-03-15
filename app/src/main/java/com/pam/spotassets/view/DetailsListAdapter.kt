package com.pam.spotassets.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.pam.spotassets.R

class DetailsListAdapter internal constructor(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any? {
        return this.dataList[this.titleList[listPosition]]?.get(expandedListPosition)
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertChildView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertChildView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertChildView = layoutInflater.inflate(R.layout.fragment_details, parent,false)
        }
        val expandedListTextView = convertChildView?.findViewById<TextView>(R.id.content)
        expandedListTextView?.text = expandedListText
        return convertChildView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]?.size ?: 0
    }
    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }
    override fun getGroupCount(): Int {
        return this.titleList.size
    }
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertGroupView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertGroupView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertGroupView = layoutInflater.inflate(R.layout.layout_group_details_list, parent,false)
        }
        val listTitleTextView = convertGroupView?.findViewById<TextView>(R.id.detailsHeader)
        listTitleTextView?.text = listTitle.replace("_"," ").uppercase()
        return convertGroupView
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}