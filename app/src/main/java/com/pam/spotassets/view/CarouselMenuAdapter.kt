package com.pam.spotassets.view

import android.view.ContextMenu
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pam.spotassets.R
import com.pam.spotassets.databinding.CarouselMenuItemBinding

class CarouselMenuAdapter : RecyclerView.Adapter<CarouselMenuAdapter.PagerVH>() {

    private val carouselTexts = intArrayOf(R.string.wordlist, R.string.hosts, R.string.s3_buckets, R.string.all_assets, R.string.url_params, R.string.apps, R.string.subdomains, R.string.urls, R.string.search_for_s3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = CarouselMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerVH(binding)
    }
    //get the size of color array
    override fun getItemCount(): Int = carouselTexts.size

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        val text = context.getString(carouselTexts[position])
        holder.bind(text)
    }

    class PagerVH(private var carouselMenuItemBinding: CarouselMenuItemBinding) : RecyclerView.ViewHolder(carouselMenuItemBinding.root) {
        fun bind(text: String) {
            carouselMenuItemBinding.menuText.text = text
        }
    }
}