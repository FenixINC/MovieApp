package com.taras.movieapp.data.paging

import androidx.paging.PagedList
import com.taras.movieapp.data.model.Movie

class MovieBoundaryCallback() : PagedList.BoundaryCallback<Movie>() {

    override fun onZeroItemsLoaded() {
//        super.onZeroItemsLoaded()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
//        super.onItemAtEndLoaded(itemAtEnd)
    }

    override fun onItemAtFrontLoaded(itemAtFront: Movie) {
//        super.onItemAtFrontLoaded(itemAtFront)
    }
}