package com.bdtd.future.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bdtd.future.ui.activity.MainActivity
import com.bdtd.future.ui.fragment.HomeFragment
import com.bdtd.future.ui.fragment.SecondFragment
import com.bdtd.future.ui.fragment.ThirdFragment

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/7/7 11:47
 */
class MainPagerAdapter(activity: MainActivity): FragmentStateAdapter(activity) {

    private val fragments : Array<Fragment> =
        arrayOf(
            HomeFragment.newInstance(),
            SecondFragment.newInstance(),
            ThirdFragment.newInstance())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}