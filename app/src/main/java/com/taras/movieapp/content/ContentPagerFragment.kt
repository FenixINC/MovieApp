package com.taras.movieapp.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.taras.movieapp.data.Constants
import com.taras.movieapp.databinding.FragmentContentPagerBinding
import com.taras.movieapp.utils.PageAdapter

class ContentPagerFragment : Fragment() {

    private lateinit var mBinding: FragmentContentPagerBinding

    companion object {
        fun newInstance(): ContentPagerFragment {
            return ContentPagerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentContentPagerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pageAdapter = PageAdapter(childFragmentManager)
        pageAdapter
            .add("Hotties", ContentFragment.newInstance(Constants.GENRE_HOTTIES))
            .add("Shorts", ContentFragment.newInstance(Constants.GENRE_SHORTS))
            .add("Action", ContentFragment.newInstance(Constants.GENRE_ACTION))
            .add("Documentary", ContentFragment.newInstance(Constants.GENRE_DOCUMENTARY))
            .add("Music", ContentFragment.newInstance(Constants.GENRE_MUSIC))
            .add("Horror", ContentFragment.newInstance(Constants.GENRE_HORROR))
            .add("Drama", ContentFragment.newInstance(Constants.GENRE_DRAMA))
            .add("Sports", ContentFragment.newInstance(Constants.GENRE_SPORTS))
            .add("Comedy", ContentFragment.newInstance(Constants.GENRE_COMEDY))
            .add("Animation", ContentFragment.newInstance(Constants.GENRE_ANIMATION))
            .add("Film", ContentFragment.newInstance(Constants.GENRE_FILM))
            .add("Western", ContentFragment.newInstance(Constants.GENRE_WESTERN))
            .add("TV Mix", ContentFragment.newInstance(Constants.GENRE_TV_MIX))

        mBinding.viewPager.adapter = pageAdapter
        mBinding.viewPager.offscreenPageLimit = 13
        mBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }
}