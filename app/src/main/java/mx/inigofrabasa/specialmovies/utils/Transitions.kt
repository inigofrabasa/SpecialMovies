package mx.inigofrabasa.specialmovies.utils

import android.os.Build
import android.transition.TransitionSet
import android.view.animation.Interpolator
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun TransitionSet.setCommonInterpolator(interpolator: Interpolator): TransitionSet {
    (0 until transitionCount)
        .map { index -> getTransitionAt(index) }
        .forEach { transition -> transition.interpolator = interpolator }

    return this
}
