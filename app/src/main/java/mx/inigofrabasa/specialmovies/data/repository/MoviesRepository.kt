package mx.inigofrabasa.specialmovies.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mx.inigofrabasa.specialmovies.data.model.Model

class MoviesRepository {
    private val APIKEY = "6b86d1d64d800b584b1a35fbe0ce0302"

    private lateinit var disposable: Disposable

    var nowPlaying: MutableLiveData<List<Model.MovieModel>> = MutableLiveData()
    var popularMovies: MutableLiveData<List<Model.MovieModel>> = MutableLiveData()
    var topMovies: MutableLiveData<List<Model.MovieModel>> = MutableLiveData()
    var upcomingMovies: MutableLiveData<List<Model.MovieModel>> = MutableLiveData()

    var searchMovies: MutableLiveData<List<Model.MovieModel>> = MutableLiveData()
    var movieVideos: MutableLiveData<List<Model.VideoModel>>? = MutableLiveData()

    private val theMovieDBApiService by lazy {
        TheMovieDBApiService.create()
    }

    fun fetchNowPlayingMovies(page:Int) {
        disposable =
            theMovieDBApiService.nowPlayingMovies(APIKEY, "en-US", page.toString(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        nowPlaying.value = result.results
                    },
                    { error ->
                        Log.v("Error fetching movies",error.message)
                    }
                )
    }

    fun fetchPopularMovies(page:Int) {
        disposable =
            theMovieDBApiService.popularMovies(APIKEY, "en-US", page.toString(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        popularMovies.value = result.results
                    },
                    { error ->
                        Log.v("Error fetching movies",error.message)
                    }
                )
    }

    fun fetchTopMovies(page:Int) {
        disposable =
            theMovieDBApiService.topMovies(APIKEY, "en-US", page.toString(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        topMovies.value = result.results
                    },
                    { error ->
                        Log.v("Error fetching movies",error.message)
                    }
                )
    }

    fun fetchUpcomingMovies(page:Int) {
        disposable =
            theMovieDBApiService.upcomingMovies(APIKEY, "en-US", page.toString(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        upcomingMovies.value = result.results
                    },
                    { error ->
                        Log.v("Error fetching movies",error.message)
                    }
                )
    }

    fun fetchMovieVideos(movieId:Int) {
        disposable =
            theMovieDBApiService.movieVideos(movieId, APIKEY, "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        movieVideos?.value = result.results
                    },
                    { error ->
                        Log.v("Error fetching videos",error.message)
                    }
                )
    }

    fun removeMovieVideosObserver(){
        movieVideos = null
        movieVideos = MutableLiveData()
    }

    companion object {
        // For Singleton
        @Volatile private var instance: MoviesRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MoviesRepository().also { instance = it }
            }
    }
}