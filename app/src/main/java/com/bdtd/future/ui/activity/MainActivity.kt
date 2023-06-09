package com.bdtd.future.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bdtd.future.R
import com.bdtd.future.databinding.ActivityMainBinding
import com.bdtd.future.retrofit.RetrofitService
import com.future.components.client.base.BaseActivity
import com.future.components.client.base.delegate.LView

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/4/25 16:48
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),LView {

//    private lateinit var adapter: MainPagerAdapter

    override fun initData(saveInstanceState: Bundle?) {
        /**
         * Navigation
         */
        val navController =Navigation.findNavController(this, R.id.nav_host)
        NavigationUI.setupWithNavController(viewBinding.bottomNavView,navController)
        viewBinding.bottomNavView.setOnItemSelectedListener{ item->
            NavigationUI.onNavDestinationSelected(item, navController)
            true
        }

        /**
         * ViewPager
         */
//        initViewPager()

        viewModel.getUserTipsData()
        viewBinding.changeUrl.setOnClickListener { RetrofitService.SERVER_URL="http://1.1.1.1:3010" }
        viewBinding.refeshData.setOnClickListener { viewModel.getUserTipsData() }
    }

//    private fun initViewPager(){
//        adapter = MainPagerAdapter(this)
//        viewBinding.viewPager.adapter = adapter
//        viewBinding.viewPager.offscreenPageLimit = 4
//        viewBinding.bottomNavView.setOnItemSelectedListener { item->
//            changeTitle(item.itemId)
//            changeFragment(item.itemId)
//            true
//        }
//        viewBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                viewBinding.bottomNavView.selectedItemId = viewBinding.bottomNavView.menu.getItem(position).itemId
//            }
//        })
//    }

//    private fun changeTitle(id:Int){
//        viewBinding.includeToolbar.toolbarTitle.text = when(id){
//            R.id.homeFragment-> "1"
//            R.id.secondFragment-> "2"
//            else -> "3"
//        }
//    }
//
//    private fun changeFragment(id:Int){
//        viewBinding.viewPager.currentItem = when(id){
//            R.id.homeFragment->0
//            R.id.secondFragment->1
//            else -> 2
//        }
//    }

    override fun showMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    companion object{
        fun start(context:Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}