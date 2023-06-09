package com.bdtd.future.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bdtd.future.databinding.ActivityLoginBinding
import com.bdtd.future.ui.activity.MainActivity
import com.bumptech.glide.Glide
import com.future.components.client.base.BaseActivity

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/8/1 14:29
 */
class LoginActivity:BaseActivity<ActivityLoginBinding,LoginViewModel>() {

    override fun initData(saveInstanceState: Bundle?) {
        viewBinding.vm = viewModel
        viewModel.getCaptchaImage()
    }

    override fun createObserver() {
        viewModel.login.observe(this) {
            if (it) {
                MainActivity.start(this)
                finish()
            }
        }
        viewModel.imageUrl.observe(this){
            Glide.with(viewBinding.verificationImage.context).load("data:image/jpg;base64,$it").into(viewBinding.verificationImage)
        }
    }

    companion object{
        fun start(context:Context){
            context.startActivity(Intent(context,LoginActivity::class.java))
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }


}