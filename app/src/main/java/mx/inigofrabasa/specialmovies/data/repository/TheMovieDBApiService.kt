package mx.inigofrabasa.specialmovies.data.repository

import mx.inigofrabasa.specialmovies.data.model.Model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApiService {
    @GET("movie/now_playing")
    fun nowPlayingMovies(@Query("api_key") api_key: String,
                         @Query("language") language: String,
                         @Query("page") page: String,
                         @Query("region") region: String):
            Observable<Model.MovieFetchInfo>

    @GET("movie/popular")
    fun popularMovies(@Query("api_key") api_key: String,
                  @Query("language") language: String,
                  @Query("page") page: String,
                  @Query("region") region: String):
            Observable<Model.MovieFetchInfo>

    @GET("movie/top_rated")
    fun topMovies(@Query("api_key") api_key: String,
                  @Query("language") language: String,
                  @Query("page") page: String,
                  @Query("region") region: String):
            Observable<Model.MovieFetchInfo>

    @GET("movie/upcoming")
    fun upcomingMovies(@Query("api_key") api_key: String,
                  @Query("language") language: String,
                  @Query("page") page: String,
                  @Query("region") region: String):
            Observable<Model.MovieFetchInfo>

    @GET("movie/{id}/videos")
    fun movieVideos(
                  @Path("id") movieId: Int,
                  @Query("api_key") api_key: String,
                  @Query("language") language: String):
            Observable<Model.VideosFetchInfo>

    companion object {
        fun create(): TheMovieDBApiService {
            val retrofit =
                Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

            return retrofit.create(TheMovieDBApiService::class.java)
        }
    }

}