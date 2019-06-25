package com.taras.movieapp.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taras.movieapp.BR
import com.taras.movieapp.mvvm.data.entities.Movie
import com.taras.movieapp.databinding.ItemMovieBinding

class MoviePagedAdapter : PagedListAdapter<Movie, MoviePagedAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val ITEM_MOVIE = 1
        private const val ITEM_STATE = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.id == newItem.id
        }
    }

    class MovieViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR.model, data)
        }
    }

    class StateViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: Boolean) {
            binding.setVariable(BR.state, state)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(ItemMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}