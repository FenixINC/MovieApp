package com.taras.movieapp.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.taras.movieapp.databinding.FragmentContentPagerBinding
import com.taras.movieapp.utils.PageAdapter

class FragmentContentPager : Fragment() {

    private lateinit var mBinding: FragmentContentPagerBinding

    companion object {
        fun newInstance(): FragmentContentPager {
            return FragmentContentPager()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentContentPagerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pageAdapter = PageAdapter(childFragmentManager)
        pageAdapter
            .add("Movies", MovieFragment.newInstance())

        mBinding.viewPager.adapter = pageAdapter
//        mBinding.viewPager.offscreenPageLimit = 2
        mBinding.tabLayout.tabMode = TabLayout.MODE_FIXED
        mBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }
}