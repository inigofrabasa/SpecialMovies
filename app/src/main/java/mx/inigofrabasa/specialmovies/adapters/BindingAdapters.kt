package mx.inigofrabasa.specialmovies.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isInvisible")
fun bindIsInvisible(view: View, isInvisible: Boolean) {
    view.visibility = if (isInvisible) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("drawable")
fun bindDrawable(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}