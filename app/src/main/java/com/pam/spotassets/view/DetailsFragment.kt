package com.pam.spotassets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pam.spotassets.R
import com.pam.spotassets.databinding.FragmentDetailsListBinding
import com.pam.spotassets.viewmodel.HomeViewModel

class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsListBinding? = null
    private val detailsBinding get() = binding!!

    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsListBinding.inflate(inflater, container, false)
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        detailsBinding.toolbar.title = getString(R.string.app_name)
        detailsBinding.toolbar.setTitleTextAppearance(context, R.style.AppNameText)

        expandableListView = detailsBinding.detailsList

        if (expandableListView != null) {

            val listData = viewModel.expandableListDetail as? HashMap<String, List<String>>

            if (listData != null) {
                titleList = ArrayList(listData.keys)
                adapter = context?.let {
                    DetailsListAdapter(
                        it,
                        titleList as ArrayList<String>,
                        listData
                    )
                }
                expandableListView!!.setAdapter(adapter)
                expandableListView!!.setOnGroupExpandListener { groupPosition ->
                    if(listData[(titleList as ArrayList<String>)[groupPosition]].isNullOrEmpty())
                    Toast.makeText(
                        context,
                        getString(R.string.found_nothing),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}