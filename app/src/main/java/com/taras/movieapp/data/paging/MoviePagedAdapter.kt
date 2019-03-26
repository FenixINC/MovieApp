package com.taras.movieapp.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taras.movieapp.BR
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.databinding.ItemMovieBinding

class MoviePagedAdapter : PagedListAdapter<Movie, MoviePagedAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
//        @JvmStatic
//        @BindingAdapter("contentImageThumb")
//        fun bindMovieImage(@NonNull imageView: ImageView, @NonNull movie: Movie) {
//            if (movie.poster == null) {
//                return
//            }
//            Glide.with(imageView)
//                .load(movie.poster.url)
//                .apply(RequestOptions().signature(ObjectKey(movie.updatedAt)))
//                .into(imageView)
//        }

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
        }
    }

    class MyViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR.model, data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}