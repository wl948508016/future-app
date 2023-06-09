package com.bdtd.future.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bdtd.future.R
import com.bdtd.future.databinding.FragmentThirdBinding
import com.bdtd.future.model.TestModel
import com.future.components.client.base.BaseFragment

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/5 15:00
 */
class ThirdFragment:BaseFragment<FragmentThirdBinding, ThirdViewModel>() {

    override fun initData(saveInstanceState: Bundle?) {
        viewBinding.button.setOnClickListener {
            val bundle=Bundle()
            bundle.putParcelable("data", TestModel("魏来",30))
            NavHostFragment.findNavController(this).navigate(R.id.action_thirdFragment_to_homeFragment,bundle)
        }
        viewBinding.back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    companion object{
        fun newInstance(): Fragment {
            return ThirdFragment()
        }
    }
}