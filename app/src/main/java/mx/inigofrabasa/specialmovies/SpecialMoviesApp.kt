package mx.inigofrabasa.specialmovies

import android.app.Application
import androidx.lifecycle.MutableLiveData
import mx.inigofrabasa.specialmovies.data.model.Model

class SpecialMoviesApp : Application() {

    var movie: MutableLiveData<Model.MovieModel> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SpecialMoviesApp
            private set
    }
}