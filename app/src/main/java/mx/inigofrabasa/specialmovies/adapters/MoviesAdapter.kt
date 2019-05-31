package mx.inigofrabasa.specialmovies.adapters

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.transition.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager

import mx.inigofrabasa.specialmovies.R
import mx.inigofrabasa.specialmovies.SpecialMoviesApp
import mx.inigofrabasa.specialmovies.data.model.Model
import mx.inigofrabasa.specialmovies.databinding.CategoriesListBinding
import mx.inigofrabasa.specialmovies.databinding.MovieListItemBinding
import mx.inigofrabasa.specialmovies.databinding.TitleCategoryItemBinding
import mx.inigofrabasa.specialmovies.utils.setCommonInterpolator
import mx.inigofrabasa.specialmovies.views.MovieDetailFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MoviesAdapter(
    private val context: Context,
    private val viewRect: Rect,
    private val transitionInterpolator: FastOutSlowInInterpolator,
    private val TRANSITION_DURATION: Long,
    private val activity: FragmentActivity,
    private val fragment: Fragment
)
    : RecyclerView.Adapter<MoviesAdapter.ViewHolder>()
{
    private lateinit var items : ArrayList<Any>
    private var mViewType = 0

    fun submitList(inItems : ArrayList<Any>){
        this.items = inItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mViewType = viewType
        return when(viewType){
            2 -> ViewHolder(TitleCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            1 -> ViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            0 -> {
                val view = CategoriesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val categoriesList = arrayListOf(
                    Model.CategoryItem("Popular",   R.drawable.popular),
                    Model.CategoryItem("Top Rated", R.drawable.top_rated),
                    Model.CategoryItem("Upcoming",  R.drawable.upcoming))

                val categoriesAdapter = CategoriesAdapter(context)
                categoriesAdapter.submitList(categoriesList)
                view.categoryList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                view.categoryList.adapter = categoriesAdapter

                return ViewHolder(view)
            }
            else -> return ViewHolder()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        fun onViewClick() {
            SpecialMoviesApp.instance.movie.value = items[position] as Model.MovieModel
            holder.itemView.getGlobalVisibleRect(viewRect)

            (fragment.exitTransition as Transition).epicenterCallback =
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                object : Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition) = viewRect
                }

            Timer().schedule(250L){
                val sharedElementTransition = TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeTransform())
                    .addTransition(ChangeImageTransform()).apply {
                        duration = TRANSITION_DURATION
                        setCommonInterpolator(transitionInterpolator)
                    }

                val fragment = MovieDetailFragment().apply {
                    sharedElementEnterTransition = sharedElementTransition
                    sharedElementReturnTransition = sharedElementTransition
                }

                activity.supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .addSharedElement(holder.itemView, "transition_name")
                    .commit()
            }
        }

        when(items[position]){
            is Model.HeaderTitle -> holder.bindDataHeader(items[position] as Model.HeaderTitle)
            is Model.MovieModel -> holder.bindData(items[position] as Model.MovieModel, ::onViewClick)
        }
    }

    override fun getItemCount(): Int {
         items.apply { return this.size }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is Model.HeaderTitle -> 2
            is Model.MovieModel -> 1
            is String -> 0
            else -> 0
        }
    }

    class ViewHolder (
        private val binding: ViewDataBinding? = null
    ) : RecyclerView.ViewHolder(binding!!.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bindData(item: Model.MovieModel, expandHandler: () -> Unit) {
            when(binding){
                is MovieListItemBinding -> (binding).apply{
                    movie = item
                    setClickListener { expandHandler()  }
                    itemView.transitionName = item.id.toString()
                    itemView.tag = item.id.toString()
                }
            }
        }

        fun bindDataHeader(item: Model.HeaderTitle){
            when(binding){
                is TitleCategoryItemBinding -> (binding).apply{
                    title = item
                    itemView.tag = item.id.toString()
                }
            }
        }
    }
}

