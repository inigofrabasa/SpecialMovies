package mx.inigofrabasa.specialmovies.data.model

import com.google.gson.annotations.SerializedName

object Model {
    data class MovieFetchInfo (
        @SerializedName("page") val page : Int,
        @SerializedName("total_results") val total_results : Int,
        @SerializedName("total_pages") val total_pages : Int,
        @SerializedName("results") val results : List<MovieModel>
    )
    data class MovieModel (
        @SerializedName("vote_count") val vote_count : Int = 0,
        @SerializedName("id") val id : Int = 0,
        @SerializedName("video") val video : Boolean = false,
        @SerializedName("vote_average") val vote_average : Double = 0.0,
        @SerializedName("title") val title : String = "",
        @SerializedName("popularity") val popularity : Double = 0.0,
        @SerializedName("poster_path") val poster_path : String = "",
        @SerializedName("original_language") val original_language : String = "",
        @SerializedName("original_title") val original_title : String = "",
        @SerializedName("genre_ids") val genre_ids : List<Int> = emptyList(),
        @SerializedName("backdrop_path") val backdrop_path : String = "",
        @SerializedName("adult") val adult : Boolean = false,
        @SerializedName("overview") val overview : String = "",
        @SerializedName("release_date") val release_date : String = ""
    )
    data class CategoryItem(
        val name:String,
        val drawable:Int
    )
    data class HeaderTitle(
        val title:String,
        val id:Int
    )
    data class VideosFetchInfo (
        @SerializedName("id") val id : Int,
        @SerializedName("results") val results : List<VideoModel>
    )
    data class VideoModel (
        @SerializedName("id") val id : String,
        @SerializedName("iso_639_1") val iso_639_1 : String,
        @SerializedName("iso_3166_1") val iso_3166_1 : String,
        @SerializedName("key") val key : String,
        @SerializedName("name") val name : String,
        @SerializedName("site") val site : String,
        @SerializedName("size") val size : Int,
        @SerializedName("type") val type : String
    )
}