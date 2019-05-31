package mx.inigofrabasa.specialmovies.views

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import mx.inigofrabasa.specialmovies.databinding.FragmentMovieDetailBinding
import mx.inigofrabasa.specialmovies.viewmodels.MovieDetailViewModel
import java.util.*
import kotlin.concurrent.schedule
import android.widget.RelativeLayout
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.Resources
import android.webkit.WebSettings
import mx.inigofrabasa.specialmovies.R


class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private var screenWidth = 0
    private var screenHeight = 0
    private var aspectRatio:Double = 0.0

    private val viewModel: MovieDetailViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        screenWidth = this.resources.displayMetrics.widthPixels
        screenHeight = this.resources.displayMetrics.heightPixels

        aspectRatio = (screenHeight.toDouble()/screenWidth.toDouble())

        viewModel.getMovie()
        subscribeUi()

        val content = view.findViewById<View>(R.id.content).also { it.alpha = 0f }
        ObjectAnimator.ofFloat(content, View.ALPHA, 0f, 1f).apply {
            startDelay = 50
            duration = 150
            start()
        }

        Timer().schedule(300L){
            activity!!.runOnUiThread {
                val marginTop = ValueAnimator.ofInt(0, 22)
                marginTop.duration = 200
                marginTop.addUpdateListener { animation ->
                    val lp = content.layoutParams as RelativeLayout.LayoutParams
                    lp.setMargins(0, animation.animatedValue as Int, 0, animation.animatedValue as Int)
                    content.layoutParams = lp
                }
                marginTop.start()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun subscribeUi() {
        viewModel.movie.observe(this, Observer { movie ->
            if (movie != null) {
                binding.movie = movie
                viewModel.getMovieVideo(movie.id)
                viewModel.movieVideos?.observe(this, Observer { movieVideos ->
                    if(movieVideos != null && movieVideos.count() > 0){
                        Timer().schedule(500L){
                            activity!!.runOnUiThread {
                                val frameVideo =
                                    "<html>" +
                                        "<body>" +
                                            "<iframe "+
                                                "scrolling=\"no\"" +
                                                "height=\"100%\""+
                                                "width=\"100%\""+
                                                "src=\"https://www.youtube.com/embed/${movieVideos[0].key}\" " +
                                                "frameborder=\"0\" " +
                                                "style=\"display:block;width:100%;height:100%\""+
                                                "allowfullscreen>" +
                                            "</iframe>" +
                                        "</body>" +
                                    "</html>"

                                val webSettings = binding.webView.settings
                                webSettings.javaScriptEnabled = true
                                binding.webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                                binding.webView.settings.loadWithOverviewMode = true
                                binding.webView.settings.useWideViewPort = true
                                binding.webView.loadData(frameVideo, "text/html", "utf-8")
                            }
                        }
                    }
                    viewModel.removeMovieVideosObserver()
                })
            }
        })
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    val Double.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}