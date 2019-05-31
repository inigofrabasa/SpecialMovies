package mx.inigofrabasa.specialmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

import mx.inigofrabasa.specialmovies.views.ListMoviesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //showToolbar("", false)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ListMoviesFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount >= 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    fun showToolbar(tittle: String, upButton: Boolean) {
        val toolbar = findViewById<Toolbar>(R.id.w_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = tittle
        supportActionBar?.setDisplayHomeAsUpEnabled(upButton)
    }
}