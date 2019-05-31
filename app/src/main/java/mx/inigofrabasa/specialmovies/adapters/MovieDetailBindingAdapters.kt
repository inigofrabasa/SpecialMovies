package mx.inigofrabasa.specialmovies.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, poster_path: String?) {
    if (!poster_path.isNullOrEmpty()) {
        Glide.with(view.context)
            .load("https://image.tmdb.org/t/p/w1280$poster_path")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}
