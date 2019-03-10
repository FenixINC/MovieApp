package com.taras.movieapp.content

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.taras.movieapp.BR
import com.taras.movieapp.R
import com.taras.movieapp.data.model.Movie
import java.util.*

class ContentAdapter() : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private var mList: ArrayList<Movie> = ArrayList()
//    private val mListener: OnClickListener = listener

    fun setList(list: List<Movie>, clear: Boolean) {
        if (clear) {
            mList.clear()
        }
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Movie {
        return mList[position]
    }

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any/*, listener: OnClickListener*/) {
            binding.setVariable(BR.model, data)
//            binding.setVariable(BR.clickListener, listener)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holder: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false)
        return ViewHolder(holder)
    }

    override fun onBindViewHolder(holder: ContentAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        @JvmStatic
        @BindingAdapter("contentImageThumb")
        fun bindMovieImage(@NonNull imageView: ImageView, @NonNull movie: Movie) {
            if (movie.poster == null) {
                return
            }
            Glide.with(imageView)
                .load(movie.poster.url)
                .apply(RequestOptions().signature(ObjectKey(movie.updatedAt)))
                .into(imageView)
        }
    }
}