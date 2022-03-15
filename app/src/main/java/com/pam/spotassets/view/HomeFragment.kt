package com.pam.spotassets.view

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.JsonParser
import com.pam.spotassets.R
import com.pam.spotassets.databinding.HomeFragmentBinding
import com.pam.spotassets.model.Resource
import com.pam.spotassets.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private var binding: HomeFragmentBinding? = null
    private val homeFragmentBinding get() = binding!!

    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return homeFragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        setObservers()
        setCarouselMenu()
        setSearchBar()

        val installedPackages = homeFragmentBinding.installedPackages
        installedPackages.setOnClickListener {
            installedApps()
        }

    }

    override fun onResume() {
        super.onResume()
        homeFragmentBinding.searchText.setTextAppearance(R.style.HintText)
        homeFragmentBinding.searchText.setText(getString(R.string.enter_package_name))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setSearchBar() {
        homeFragmentBinding.searchText.let {

            it.setOnFocusChangeListener { _, hasFocus ->
                (activity as MainActivity).showSoftKeyboard(it, hasFocus)
                if (hasFocus) {
                    it.setTextAppearance(R.style.SearchText)
                    if ((it as TextView).text.toString() == getString(R.string.enter_package_name))
                        it.setText("")
                } else {
                    if ((it as TextView).text.toString() == "") {
                        it.setTextAppearance(R.style.HintText)
                        it.setText(getString(R.string.enter_package_name))
                    }
                }
            }
        }

        homeFragmentBinding.searchIcon.setOnClickListener { v ->

            homeFragmentBinding.searchText.let {
                (activity as MainActivity).showSoftKeyboard(
                    it,
                    false
                )
            }

            if (viewModel.menuChosen == getString(R.string.all_assets)) getAllAssets(
                homeFragmentBinding.searchText.text.toString()
            )
            else if (viewModel.menuChosen == getString(R.string.wordlist) ||
                viewModel.menuChosen == getString(R.string.hosts) ||
                viewModel.menuChosen == getString(R.string.s3_buckets) ||
                viewModel.menuChosen == getString(R.string.subdomains) ||
                viewModel.menuChosen == getString(R.string.urls) ||
                viewModel.menuChosen == getString(R.string.search_for_s3)
            ) getSelectedAssets(
                viewModel.menuChosen,
                homeFragmentBinding.searchText.text.toString()
            )
            else if (viewModel.menuChosen == getString(R.string.url_params)) getUrlParams(
                homeFragmentBinding.searchText.text.toString()
            )

            if (viewModel.menuChosen != getString(R.string.apps)) {
                v.visibility = View.GONE
                homeFragmentBinding.progressBarCyclic.visibility = View.VISIBLE
            } else wipDialog()

        }
    }

    private fun setCarouselMenu() {

        val carouselMenu = homeFragmentBinding.carouselMenu

        carouselMenu.adapter = CarouselMenuAdapter(requireActivity())
        carouselMenu.offscreenPageLimit = 1
        carouselMenu.setPageTransformer(CarouselTransformer(requireContext()))

        val itemDecoration = context?.let { HorizontalMarginItemDecoration(0) }

        if (itemDecoration != null)
            carouselMenu.addItemDecoration(itemDecoration)

        val carouselMenuPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.menuChosen = context?.getString(viewModel.carouselTexts[position]) ?: ""
                setMenuDescription(viewModel.menuChosen)
            }
        }

        carouselMenu.registerOnPageChangeCallback(carouselMenuPageChangeCallback)
    }

    private fun setObservers() {

        val chosenPackageObserver = Observer<String> {
            homeFragmentBinding.searchText.setTextAppearance(R.style.SearchText)
            homeFragmentBinding.searchText.setText(viewModel.getChosenPackage().toString())
        }

        viewModel.chosenPackage.observe(this, chosenPackageObserver)
    }

    private fun wipDialog() {

        homeFragmentBinding.searchIcon.visibility = View.VISIBLE
        homeFragmentBinding.progressBarCyclic.visibility = View.GONE

        activity?.let {
            WIPDialogFragment.newInstance().show(it.supportFragmentManager, "dialog")
        }
    }

    private fun installedApps() {
        val packList: List<PackageInfo>? = context?.packageManager?.getInstalledPackages(0)
        val installedPackageList: MutableList<String> = mutableListOf()

        if (packList != null)
            for (i in packList.indices) {
                val packInfo = packList[i]
                if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 && packInfo.packageName != null)
                    installedPackageList.add(packInfo.packageName)
            }
        activity?.let {
            InstalledPackagesListDialogFragment.newInstance(installedPackageList)
                .show(it.supportFragmentManager, "dialog")
        }
    }

    private fun getAllAssets(packageId: String) {
        viewModel.getAllAssets(packageId).observe(
            viewLifecycleOwner
        ) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        viewModel.expandableListDetail = it.data.assets
                        homeFragmentBinding.progressBarCyclic.visibility = View.GONE
                        homeFragmentBinding.searchIcon.visibility = View.VISIBLE
                        findNavController().navigate(
                            R.id.action_homeFragment_to_detailsFragment
                        )
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.please_try_again),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Resource.Status.ERROR -> {
                    val message = JsonParser().parse(it.errorResponse)
                    Toast.makeText(
                        context,
                        message.asJsonObject.get(getString(R.string.detail)).asString,
                        Toast.LENGTH_LONG
                    ).show()
                    homeFragmentBinding.progressBarCyclic.visibility = View.GONE
                    homeFragmentBinding.searchIcon.visibility = View.VISIBLE
                }
                Resource.Status.LOADING -> {
                    homeFragmentBinding.progressBarCyclic.visibility = View.VISIBLE
                    homeFragmentBinding.searchIcon.visibility = View.GONE
                }
            }
        }
    }

    private fun getUrlParams(packageId: String) {
        viewModel.getUrlParams(packageId).observe(
            viewLifecycleOwner
        ) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        viewModel.expandableListDetail = it.data.url_params
                        homeFragmentBinding.progressBarCyclic.visibility = View.GONE
                        homeFragmentBinding.searchIcon.visibility = View.VISIBLE
                        findNavController().navigate(
                            R.id.action_homeFragment_to_detailsFragment
                        )
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.please_try_again),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Resource.Status.ERROR -> {
                    val message = JsonParser().parse(it.errorResponse)
                    Toast.makeText(
                        context,
                        message.asJsonObject.get(getString(R.string.detail)).asString,
                        Toast.LENGTH_LONG
                    ).show()
                    homeFragmentBinding.progressBarCyclic.visibility = View.GONE
                    homeFragmentBinding.searchIcon.visibility = View.VISIBLE
                }
                Resource.Status.LOADING -> {
                    homeFragmentBinding.progressBarCyclic.visibility = View.VISIBLE
                    homeFragmentBinding.searchIcon.visibility = View.GONE
                }
            }
        }
    }

    private fun getSelectedAssets(type: String, packageId: String) {
        viewModel.getSelectedAssets(type, packageId).observe(
            viewLifecycleOwner
        ) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    homeFragmentBinding.progressBarCyclic.visibility = View.GONE
                    homeFragmentBinding.searchIcon.visibility = View.VISIBLE
                    if (!it.data.isNullOrEmpty()) {
                        if (!it.data.containsKey(getString(R.string.detail))) {
                            viewModel.expandableListDetail = it.data
                            viewModel.expandableListDetail.remove(getString(R.string.package_id))
                            viewModel.expandableListDetail.remove(getString(R.string.domain))
                            viewModel.expandableListDetail.remove(getString(R.string.keyword))
                            findNavController().navigate(
                                R.id.action_homeFragment_to_detailsFragment
                            )
                        } else Toast.makeText(
                            context,
                            it.data[getString(R.string.detail)].toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.please_try_again),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Resource.Status.ERROR -> {
                    val message = JsonParser().parse(it.errorResponse)
                    Toast.makeText(
                        context,
                        message.asJsonObject.get(getString(R.string.detail)).asString,
                        Toast.LENGTH_LONG
                    ).show()
                    homeFragmentBinding.progressBarCyclic.visibility = View.GONE
                    homeFragmentBinding.searchIcon.visibility = View.VISIBLE
                }
                Resource.Status.LOADING -> {
                    homeFragmentBinding.progressBarCyclic.visibility = View.VISIBLE
                    homeFragmentBinding.searchIcon.visibility = View.GONE
                }
            }
        }
    }

    private fun setMenuDescription(menu: String) {
        when (menu) {
            getString(R.string.wordlist) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.wordlist_desc)
            getString(R.string.hosts) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.hosts_desc)
            getString(R.string.s3_buckets) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.s3_buckets_desc)
            getString(R.string.all_assets) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.all_assets_desc)
            getString(R.string.url_params) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.url_params_desc)
            getString(R.string.apps) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.apps_desc)
            getString(R.string.subdomains) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.subdomains_desc)
            getString(R.string.urls) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.urls_desc)
            getString(R.string.search_for_s3) -> homeFragmentBinding.menuDescription.text =
                getString(R.string.search_for_s3_desc)
            else -> homeFragmentBinding.menuDescription.text = ""
        }
    }
}

class HorizontalMarginItemDecoration(horizontalMarginInDp: Int) :
    RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int = horizontalMarginInDp

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }

}

