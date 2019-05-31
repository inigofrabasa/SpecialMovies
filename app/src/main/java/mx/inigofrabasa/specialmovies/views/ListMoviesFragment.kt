package mx.inigofrabasa.specialmovies.views

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.transition.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

import mx.inigofrabasa.specialmovies.adapters.MoviesAdapter
import mx.inigofrabasa.specialmovies.databinding.FragmentListMoviesBinding
import mx.inigofrabasa.specialmovies.utils.SlideExplode
import mx.inigofrabasa.specialmovies.viewmodels.MoviesViewModel

import kotlinx.android.synthetic.main.fragment_list_movies.*
import mx.inigofrabasa.specialmovies.data.model.Model
import kotlin.collections.ArrayList
import android.app.Activity
import android.view.inputmethod.InputMethodManager


private val transitionInterpolator = FastOutSlowInInterpolator()
private const val TRANSITION_DURATION = 300L
private const val TAP_POSITION = "tap_position"

class ListMoviesFragment(private val isMainView:Boolean = true, private val movieCategory:Int = 0) : Fragment() {

    private lateinit var binding: FragmentListMoviesBinding
    private lateinit var rvMovieList: RecyclerView

    private var tapPosition = NO_POSITION
    val viewRect = Rect()

    private val viewModel: MoviesViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        rvMovieList = binding.moviesList
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tapPosition = savedInstanceState?.getInt(TAP_POSITION, NO_POSITION) ?: NO_POSITION

        postponeEnterTransition()

        binding.hideSearch = true
        if(!isMainView) binding.hideSearch = false

        binding.hideDeleteSearch = true
        binding.etSearechTerm.text.clear()

        rvMovieList = binding.moviesList
        rvMovieList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        binding.isLoaded = false

        val adapter = MoviesAdapter(
            context!!,
            viewRect,
            transitionInterpolator,
            TRANSITION_DURATION,
            activity as FragmentActivity,
            this@ListMoviesFragment)

        when(movieCategory){
            0 -> if (viewModel.nowPlayingMovies.value == null) viewModel.getNowPlayingMovies(1)
            1 -> if (viewModel.popularMovies.value == null) viewModel.getPopularMovies(1)
            2 -> if (viewModel.topMovies.value == null) viewModel.getTopMovies(1)
            3 -> if (viewModel.upcomingMovies.value == null) viewModel.getUpcomingMovies(1)
        }
        subscribeUi(adapter)

        binding.searchButton.setOnClickListener {
            if (binding.etSearechTerm.text.toString() != ""){
                searchMovie(binding.etSearechTerm.text.toString(), adapter)
            }
        }

        binding.deleteSearchButton.setOnClickListener {
            deleteSearch(adapter)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun subscribeUi(adapter: MoviesAdapter) {

        when(movieCategory){
            0 -> {viewModel.nowPlayingMovies.observe(this, Observer { movies ->
                if (movies != null) {
                    populateView(movies, adapter)
                }
            })}
            1 -> {viewModel.popularMovies.observe(this, Observer { movies ->
                if (movies != null) {
                    populateView(movies, adapter)
                }
            })}
            2 -> {viewModel.topMovies.observe(this, Observer { movies ->
                if (movies != null) {
                    populateView(movies, adapter)
                }
            })}
            3 -> {viewModel.upcomingMovies.observe(this, Observer { movies ->
                if (movies != null) {
                    populateView(movies, adapter)
                }
            })}
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun populateView(movies : Any, adapter: MoviesAdapter){

        binding.moviesList.adapter = adapter
        val allElements = ArrayList<Any>()

        allElements.apply{

            addAll(movies as ArrayList<Any>)

            when(movieCategory){
                0 -> add(0, Model.HeaderTitle("Now Playing", 1))
                1 -> add(0, Model.HeaderTitle("Popular", 2))
                2 -> add(0, Model.HeaderTitle("Top Rated", 3))
                3 -> add(0, Model.HeaderTitle("Upcoming", 4))
            }

            when(isMainView){
                true -> {
                    add(0, "categories")
                    add(0, Model.HeaderTitle("Categories", 0))
                }
            }
        }

        with(adapter) { submitList(allElements) }
        binding.isLoaded = true

        (view?.parent as? ViewGroup)?.doOnPreDraw {
            if (exitTransition == null) {
                exitTransition = SlideExplode().apply {
                    duration = TRANSITION_DURATION
                    interpolator = transitionInterpolator
                }
            }

            val layoutManager = moviesList.layoutManager as LinearLayoutManager
            layoutManager.findViewByPosition(tapPosition)?.let { view ->
                view.getGlobalVisibleRect(viewRect)
                (exitTransition as Transition).epicenterCallback =
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    object : Transition.EpicenterCallback() {
                        override fun onGetEpicenter(transition: Transition) = viewRect
                    }
            }

            startPostponedEnterTransition()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun searchMovie(request: String, adapter: MoviesAdapter) {
        hideKeyboard(activity as FragmentActivity)
        binding.hideDeleteSearch = false
        viewModel.searchMovies(request, movieCategory)
        viewModel.searchMovies.observe(this, Observer { movies ->
            if (movies != null) {
                populateView(movies, adapter)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun deleteSearch(adapter: MoviesAdapter){
        binding.hideDeleteSearch = true
        binding.etSearechTerm.text.clear()
        when(movieCategory){
            0 -> populateView(viewModel.nowPlayingMovies.value as Any, adapter)
            1 -> populateView(viewModel.popularMovies.value as Any, adapter)
            2 -> populateView(viewModel.topMovies.value as Any, adapter)
            3 -> populateView(viewModel.upcomingMovies.value as Any, adapter)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAP_POSITION, tapPosition)
    }
}
