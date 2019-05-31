package mx.inigofrabasa.specialmovies.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.inigofrabasa.specialmovies.R

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val ss:Int = intent.getIntExtra("category_number", 0)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ListMoviesFragment(false,ss+1))
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
}