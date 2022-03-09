package com.pam.spotassets.view

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.pam.spotassets.R
import com.pam.spotassets.databinding.FragmentDialogItemBinding
import com.pam.spotassets.databinding.FragmentItemListDialogBinding
import com.pam.spotassets.viewmodel.HomeViewModel
import java.util.ArrayList

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "installed_packages_list"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    InstalledPackagesListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class InstalledPackagesListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentItemListDialogBinding? = null

    private lateinit var viewModel: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.SheetDialog);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        _binding = FragmentItemListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.list.layoutManager =
            LinearLayoutManager(context)
        binding.list.adapter =
            arguments?.getStringArrayList(ARG_ITEM_COUNT)?.let { InstalledPackagesAdapter(it) }
    }

    private inner class ViewHolder(binding: FragmentDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val text: TextView = binding.text
    }

    private inner class InstalledPackagesAdapter(private val installedPackagesList: MutableList<String>) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                FragmentDialogItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = installedPackagesList[position]
            holder.text.setOnClickListener {
                viewModel.setChosenPackage(installedPackagesList[position])
                dismiss()
            }
        }

        override fun getItemCount(): Int {
            return installedPackagesList.size
        }
    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(installedPackagesList: MutableList<String>): InstalledPackagesListDialogFragment =
            InstalledPackagesListDialogFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_ITEM_COUNT, installedPackagesList as ArrayList<String>)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}