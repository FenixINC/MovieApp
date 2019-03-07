package com.taras.movieapp.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.taras.movieapp.databinding.FragmentContentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class MovieFragment : Fragment(), CoroutineScope {

    private lateinit var mBinding: FragmentContentBinding
    private lateinit var mAdapter: ContentAdapter

    companion object {
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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


    }
}