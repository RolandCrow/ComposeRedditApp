package com.example.composeredditapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.example.composeredditapp.R
import com.example.composeredditapp.databinding.ViewTrendingTopicBinding

class TrendingTopicView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): CardView(context,attrs,defStyleAttr) {
    private var binding: ViewTrendingTopicBinding =
        ViewTrendingTopicBinding.inflate(LayoutInflater.from(context),this)

    var text: String = ""
        set(value) {
            field = value
            binding.text.text = value
        }

    var image: Int= 0
        set(value) {
            field = value
            binding.image.setImageResource(value)
        }

    init {
        radius = resources.getDimension(R.dimen.trending_view_corner_radius)
        val width = resources.getDimensionPixelSize(R.dimen.trending_view_width)
        val height = resources.getDimensionPixelSize(R.dimen.trending_view_height)
        val layout = LayoutParams(width,height)
        layoutParams = layout
    }
}