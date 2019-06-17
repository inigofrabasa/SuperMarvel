package com.inigofrabasa.supermarvel.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, photo_path: String?) {
    if (!photo_path.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(photo_path)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}
