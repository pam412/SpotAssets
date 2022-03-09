package com.pam.spotassets.view

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.pam.spotassets.R
import com.pam.spotassets.databinding.CarouselMotionLayoutBinding
import com.pam.spotassets.databinding.HomeFragmentBinding
import com.pam.spotassets.viewmodel.HomeViewModel
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import android.content.pm.PackageInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var homeFragmentBinding: HomeFragmentBinding? = null

    private lateinit var viewModel: HomeViewModel

    private val binding get() = homeFragmentBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
        return homeFragmentBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val searchBar = homeFragmentBinding?.searchText
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)


        searchBar?.setOnFocusChangeListener { _, hasFocus ->
            (activity as MainActivity).showSoftKeyboard(searchBar,hasFocus)
            if (hasFocus) {
                searchBar.setTextAppearance(R.style.SearchText)
                Log.v("TAG", (searchBar as TextView).text.toString())
                Log.v("TAG", getString(R.string.enter_package_name))

                if ((searchBar as TextView).text.toString() == getString(R.string.enter_package_name))
                    searchBar.setText("")
            } else {
                if ((searchBar as TextView).text.toString() == "") {
                    searchBar.setTextAppearance(R.style.HintText)
                    searchBar.setText(getString(R.string.enter_package_name))

                }
            }
        }

        val chosenPackageObserver = Observer<String> { chosenPackage ->
            searchBar?.setTextAppearance(R.style.SearchText)
            searchBar?.setText(viewModel.getChosenPackage().toString())
            Log.d("TAG",viewModel.getChosenPackage().toString())
        }
        viewModel.chosenPackage.observe(this, chosenPackageObserver)

        val carouselMenu = homeFragmentBinding?.carouselMenu

        if (carouselMenu != null) {
            carouselMenu.adapter = CarouselMenuAdapter()
            carouselMenu.offscreenPageLimit = 1
            carouselMenu.setPageTransformer(CarouselTransformer(requireContext()))


            val itemDecoration = context?.let {
                HorizontalMarginItemDecoration(
                    it,
                    0
                )
            }
            if (itemDecoration != null) {
                carouselMenu.addItemDecoration(itemDecoration)
            }
        }

        val DRAWABLE_RIGHT = 2
        searchBar?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.getAction() === MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= searchBar.right - searchBar.getCompoundDrawables()
                            .get(DRAWABLE_RIGHT).getBounds().width()
                    ) {
                        Toast.makeText(context, "search clicked", Toast.LENGTH_SHORT).show()
                            viewModel.getAllAssets(searchBar.text.toString())
                        findNavController().navigate(
                            R.id.action_homeFragment_to_detailsFragment
                        )
                        // your action here                    edittextview_confirmpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                } else {
//                searchBar.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                }
                return v?.onTouchEvent(event) ?: true

            }
        })

        val installedPackages = homeFragmentBinding?.installedPackages

        installedPackages?.setOnClickListener {
//            val pm: PackageManager? = context?.packageManager
////get a list of installed apps.
////get a list of installed apps.
//            if(pm != null)
//            {
//                val packages: List<ApplicationInfo> =
//                    pm.getInstalledApplications(PackageManager.GET_META_DATA)
//
//                for (packageInfo in packages) {
//                    Log.d("TAG", "Installed package :" + packageInfo.packageName)
//                }
//            }
            installedApps()
        }


//
//        carouselMotionLayoutBinding?.carousel?.setAdapter(object : Carousel.Adapter {
//            override fun count(): Int {
//                // need to return the number of items we have in the carousel
//                return 9
//            }
//
//            override fun populate(view: View, index: Int) {
//                // need to implement this to populate the view at the given index
//                (view as TextView).setText(carouselTexts.get(index))
//            }
//
//            override fun onNewItem(index: Int) {
//                // called when an item is set
//            }
//        })
    }

    private fun installedApps() {
        val packList: List<PackageInfo>? = context?.packageManager?.getInstalledPackages(0)
        val installedPackageList: MutableList<String> = mutableListOf()

        if(packList != null)
            for (i in packList.indices) {
                val packInfo = packList[i]
                if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 && packInfo.packageName != null)
                    installedPackageList.add(packInfo.packageName)
            }
        activity?.let { InstalledPackagesListDialogFragment.newInstance(installedPackageList).show(it.supportFragmentManager, "dialog") }
    }

}

class HorizontalMarginItemDecoration(context: Context, horizontalMarginInDp: Int) :
    RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int = horizontalMarginInDp
//        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }

}

