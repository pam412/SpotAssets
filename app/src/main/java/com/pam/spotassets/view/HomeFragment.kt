package com.pam.spotassets.view

import android.content.Context
import android.graphics.Rect
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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




class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var homeFragmentBinding: HomeFragmentBinding? = null


    private val binding get() = homeFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
        return homeFragmentBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchBar = homeFragmentBinding?.searchText
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

