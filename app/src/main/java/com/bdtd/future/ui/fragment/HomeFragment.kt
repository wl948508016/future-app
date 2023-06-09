package com.bdtd.future.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bdtd.future.R
import com.bdtd.future.databinding.FragmentHomeBinding
import com.bdtd.future.model.TestModel
import com.future.components.client.base.BaseFragment

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/5 15:00
 */
class HomeFragment:BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun initData(saveInstanceState: Bundle?) {
        val model : TestModel? =  arguments?.getParcelable("data")
         model?.run {
            Log.e("HomeFragment","name: ${model.name}== age: ${model.age}")
        }
        viewBinding.button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_secondFragment)
        }
    }

    companion object{
        fun newInstance():Fragment{
            return HomeFragment()
        }
    }
}