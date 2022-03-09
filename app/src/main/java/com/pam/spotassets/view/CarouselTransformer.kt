package com.pam.spotassets.view

import android.content.Context
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.pam.spotassets.R

class CarouselTransformer(val context: Context) :
    ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val pageMarginPx = page.width / 2
        val offsetPx = context.resources.getDimensionPixelOffset(R.dimen._40dp)

        page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
        page.scaleX = 1 - (0.25f * kotlin.math.abs(position))
        val offset = position * -(pageMarginPx + 2 * offsetPx)
        page.translationX = offset
        page.alpha = 0.65f + (1 - kotlin.math.abs(position))


    }
}