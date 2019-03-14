package com.taras.movieapp.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.viewmodel.MovieViewModel
import com.taras.movieapp.databinding.FragmentContentBinding

class ContentFragment : Fragment() {

    private lateinit var mBinding: FragmentContentBinding
    private lateinit var mAdapter: ContentAdapter
    private lateinit var mViewModel: MovieViewModel

    private var mMovieGenre = ""

    companion object {
        private const val MOVIE_GENRE = "movie_genre"

        fun newInstance(movieGenre: String): ContentFragment {
            val bundle = bundleOf(MOVIE_GENRE to movieGenre)
            return ContentFragment().apply { arguments = bundle }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            mMovieGenre = getString(MOVIE_GENRE)!!
        }
        mViewModel = ViewModelProviders.of(this@ContentFragment).get(MovieViewModel::class.java)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentContentBinding.inflate(inflater, container, false)
        mAdapter = ContentAdapter()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = mBinding.recyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.setHasFixedSize(true)
        rv.adapter = mAdapter

        mBinding.swipeRefresh.setOnRefreshListener {
            mBinding.swipeRefresh.isRefreshing = false
            mViewModel.doRefreshMoviesByGenre(mMovieGenre).observe(this@ContentFragment, Observer<List<Movie>> { list -> mAdapter.setList(list, true) })
        }

        mViewModel.doLoadMoviesByGenre(mMovieGenre).observe(this@ContentFragment, Observer<List<Movie>> { list -> mAdapter.setList(list, true) })
    }
}