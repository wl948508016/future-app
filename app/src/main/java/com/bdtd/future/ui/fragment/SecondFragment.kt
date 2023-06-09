package com.bdtd.future.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bdtd.future.R
import com.bdtd.future.databinding.FragmentSecondBinding
import com.future.components.client.base.BaseFragment

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/5 15:00
 */
class SecondFragment:BaseFragment<FragmentSecondBinding, SecondViewModel>() {

    override fun initData(saveInstanceState: Bundle?) {
        viewBinding.button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_secondFragment_to_thirdFragment)
        }
    }

    companion object{
        fun newInstance(): Fragment {
            return SecondFragment()
        }
    }
}