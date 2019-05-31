package mx.inigofrabasa.specialmovies.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import mx.inigofrabasa.specialmovies.data.model.Model

import mx.inigofrabasa.specialmovies.databinding.CategoryListItemBinding
import mx.inigofrabasa.specialmovies.views.CategoryActivity

class CategoriesAdapter(private val context: Context) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private lateinit var items : ArrayList<Model.CategoryItem>

    fun submitList(inItems : ArrayList<Model.CategoryItem>){
        this.items = inItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        fun onViewClick() {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category_number", position)
            context.startActivity(intent)
        }

        holder.bindData(items[position], ::onViewClick)

    }

    override fun getItemCount(): Int {
        items.apply { return this.size }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> 0
            else -> 1
        }
    }

    class ViewHolder (
        private val binding: CategoryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bindData(item: Model.CategoryItem, expandHandler: () -> Unit) {
            (binding).apply{
                category = item
                setClickListener { expandHandler()  }
                itemView.tag = item
            }
        }
    }
}