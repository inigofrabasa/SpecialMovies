package mx.inigofrabasa.specialmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import mx.inigofrabasa.specialmovies.SpecialMoviesApp
import mx.inigofrabasa.specialmovies.data.model.Model
import mx.inigofrabasa.specialmovies.data.repository.MoviesRepository

class MovieDetailViewModel: ViewModel() {
    private val movieLiveData = SpecialMoviesApp.instance.movie

    val movie: LiveData<Model.MovieModel>
        get() = movieLiveData

    fun getMovie(){
        SpecialMoviesApp.instance.movie
    }

    private val movieVideosLiveData = MoviesRepository.getInstance().movieVideos

    val movieVideos: LiveData<List<Model.VideoModel>>?
        get() = movieVideosLiveData

    fun getMovieVideo(movieId:Int){
        MoviesRepository.getInstance().fetchMovieVideos(movieId)
    }

    fun removeMovieVideosObserver(){
        MoviesRepository.getInstance().removeMovieVideosObserver()
    }
}