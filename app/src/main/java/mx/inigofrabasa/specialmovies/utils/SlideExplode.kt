package mx.inigofrabasa.specialmovies.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Build
import android.transition.TransitionValues
import android.transition.Visibility
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

private const val KEY_SCREEN_BOUNDS = "screenBounds"

class SlideExplode : Visibility() {
    private val mTempLoc = IntArray(2)

    private fun captureValues(transitionValues: TransitionValues) {
        val view = transitionValues.view
        view.getLocationOnScreen(mTempLoc)
        val left = mTempLoc[0]
        val top = mTempLoc[1]
        val right = left + view.width
        val bottom = top + view.height
        transitionValues.values[KEY_SCREEN_BOUNDS] = Rect(left, top, right, bottom)
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        super.captureStartValues(transitionValues)
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)
        captureValues(transitionValues)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onAppear(sceneRoot: ViewGroup, view: View,
                          startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (endValues == null) return null

        val bounds = endValues.values[KEY_SCREEN_BOUNDS] as Rect
        val endY = view.translationY
        val startY = endY + calculateDistance(sceneRoot, bounds)
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startY, endY)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDisappear(sceneRoot: ViewGroup, view: View,
                             startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (startValues == null) return null

        val bounds = startValues.values[KEY_SCREEN_BOUNDS] as Rect
        val startY = view.translationY
        var endY = startY + calculateDistance(sceneRoot, bounds)
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startY, endY)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun calculateDistance(sceneRoot: View, viewBounds: Rect): Int {
        sceneRoot.getLocationOnScreen(mTempLoc)
        val sceneRootY = mTempLoc[1]
        return when {
            epicenter == null -> -sceneRoot.height
            viewBounds.top <= epicenter.top -> (sceneRootY - epicenter.top)
            else -> sceneRootY + sceneRoot.height - epicenter.bottom
        }
    }
}
