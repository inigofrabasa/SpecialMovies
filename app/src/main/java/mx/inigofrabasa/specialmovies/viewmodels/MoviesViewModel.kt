package mx.inigofrabasa.specialmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import mx.inigofrabasa.specialmovies.data.model.Model
import mx.inigofrabasa.specialmovies.data.repository.MoviesRepository

class MoviesViewModel: ViewModel(){

    private val nowPlayingMoviesLiveData = MoviesRepository.getInstance().nowPlaying
    private val popularMoviesLiveData = MoviesRepository.getInstance().popularMovies
    private val topMoviesLiveData = MoviesRepository.getInstance().topMovies
    private val upcomingMoviesLiveData = MoviesRepository.getInstance().upcomingMovies

    private val searchMoviesLiveData = MoviesRepository.getInstance().searchMovies

    val nowPlayingMovies: LiveData<List<Model.MovieModel>>
        get() = nowPlayingMoviesLiveData

    val popularMovies: LiveData<List<Model.MovieModel>>
        get() = popularMoviesLiveData

    val topMovies: LiveData<List<Model.MovieModel>>
        get() = topMoviesLiveData

    val upcomingMovies: LiveData<List<Model.MovieModel>>
        get() = upcomingMoviesLiveData

    val searchMovies: LiveData<List<Model.MovieModel>>
        get() = searchMoviesLiveData

    fun getNowPlayingMovies(page:Int){
        MoviesRepository.getInstance().fetchNowPlayingMovies(page)
    }

    fun getPopularMovies(page:Int){
        MoviesRepository.getInstance().fetchPopularMovies(page)
    }

    fun getTopMovies(page:Int){
        MoviesRepository.getInstance().fetchTopMovies(page)
    }

    fun getUpcomingMovies(page:Int){
        MoviesRepository.getInstance().fetchUpcomingMovies(page)
    }

    fun searchMovies(request:String, moviesId:Int){
        val searchResult = arrayListOf<Model.MovieModel>()

        when(moviesId){
            1 -> {
                popularMovies.value?.filter { movie -> movie.title.toLowerCase().contains(request) }?.let { searchResult.addAll(it) }
            }
            2 -> {
                topMovies.value?.filter { movie -> movie.title.toLowerCase().contains(request) }?.let { searchResult.addAll(it) }
            }
            3 -> {
                upcomingMovies.value?.filter { movie -> movie.title.toLowerCase().contains(request) }?.let { searchResult.addAll(it) }
            }
        }
        searchMoviesLiveData.value = searchResult
    }
}