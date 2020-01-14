package com.example.callvideo.Service;

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ListArticlesPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var fragments = ArrayList<Fragment>()
    var fragmentsTitles = ArrayList<String>()

    override fun getCount(): Int {
        return fragmentsTitles.size
    }

    override fun getItem(i: Int): Fragment {
        val fragment = fragments.get(i)
        fragment.arguments = Bundle().apply {
            val category = if(i != 0) fragmentsTitles.get(i) else ""
            putString("category", category)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentsTitles.get(position)
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentsTitles.add(title)
    }
}
