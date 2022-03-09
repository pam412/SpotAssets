package com.pam.spotassets.view

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.pam.spotassets.R
import com.pam.spotassets.databinding.FragmentDetailsBinding
import com.pam.spotassets.databinding.FragmentDetailsListBinding
import com.pam.spotassets.databinding.HomeFragmentBinding

import com.pam.spotassets.view.placeholder.PlaceholderContent.PlaceholderItem
import java.util.*
import kotlin.collections.HashMap

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class DetailsListAdapter internal constructor(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
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
    ): View {
        var convertChildView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertChildView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertChildView = layoutInflater.inflate(R.layout.fragment_details, null)
        }
        val expandedListTextView = convertChildView!!.findViewById<TextView>(R.id.content)
        expandedListTextView.text = expandedListText
        return convertChildView
    }
    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
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
    ): View {
        var convertGroupView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertGroupView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertGroupView = layoutInflater.inflate(R.layout.layout_group_details_list, null)
        }
        val listTitleTextView = convertGroupView!!.findViewById<TextView>(R.id.detailsHeader)
        listTitleTextView.text = listTitle
        return convertGroupView
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}