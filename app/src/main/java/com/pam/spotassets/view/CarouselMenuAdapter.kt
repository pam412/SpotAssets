package com.pam.spotassets.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pam.spotassets.databinding.CarouselMenuItemBinding
import com.pam.spotassets.viewmodel.HomeViewModel

class CarouselMenuAdapter(activity: FragmentActivity) : RecyclerView.Adapter<CarouselMenuAdapter.PagerVH>() {

    private var viewModel = ViewModelProvider(activity).get(HomeViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = CarouselMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerVH(binding)
    }

    override fun getItemCount(): Int = viewModel.carouselTexts.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        val text = context.getString(viewModel.carouselTexts[position])
        holder.bind(text)
        viewModel.menuChosen = text
    }

    class PagerVH(private var carouselMenuItemBinding: CarouselMenuItemBinding) : RecyclerView.ViewHolder(carouselMenuItemBinding.root) {
        fun bind(text: String) {
            carouselMenuItemBinding.menuText.text = text
        }
    }
}