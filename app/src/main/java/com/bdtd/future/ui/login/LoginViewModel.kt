package com.bdtd.future.ui.login

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bdtd.future.R
import com.bdtd.future.retrofit.RetrofitService
import com.bdtd.future.retrofit.RetrofitService.Companion.SERVER_URL
import com.future.components.client.databinding.StringObservableField
import com.future.components.client.viewmodel.BaseViewModel
import com.future.components.net.NetworkHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/8/1 14:29
 */
class LoginViewModel:BaseViewModel() {

    private var apiService = NetworkHelper.INSTANCE.getApi(RetrofitService::class.java, SERVER_URL)

    var loading= ObservableBoolean()

    var userName = StringObservableField()

    var userPassword = StringObservableField()

    var userVerification = StringObservableField()

    private var userUuid :String =""

    private var publicKey :String =""

    val login = MutableLiveData<Boolean>()

    val imageUrl: MutableLiveData<String> = MutableLiveData()

//    fun loadingVisibility()=object :ObservableInt(loading) { override fun get(): Int = if(loading.get()) View.VISIBLE else View.GONE } //显示隐藏另外一种写法

    fun setApiServer() {
        apiService = NetworkHelper.INSTANCE.getApi(RetrofitService::class.java, RetrofitService.SERVER_URL)
    }

    /**
     * 自动登录
     */
    fun autoLogin(){
        //刷新Token
//        find({DataBaseManager.getLastUserInfo()},{
//            when(it){
//                null-> LogUtils.e(" lastUserInfo = null :")
//                else ->{
//                    val account = it.loginAccount
//                    val password = it.loginPassword
//                    if (account.isEmpty() || password.isEmpty()) {
//                        return@find
//                    }
//                    userName.set(account)
//                    userPassword.set(password)
////                    login()
//                }
//            }
//        })

    }

    /**
     * 登录
     */
    fun login(){
        when{
            userName.get().isEmpty() -> postMessage(R.string.error_account_field_required)
            userPassword.get().isEmpty()-> postMessage(R.string.error_password_field_required)
            userVerification.get().isEmpty()-> postMessage(R.string.error_verification_field_required)
            else ->{
                loading.set(true)
                viewModelScope.launch {
                    delay(2000)
                    loading.set(false)
                    login.postValue(true)
                }
//                val hashMap = HashMap<String, Any>()
//                hashMap["username"] = userName.get()
//                hashMap["password"] = userPassword.get()
//                hashMap["code"] = userVerification.get()
//                hashMap["uuid"] = userUuid
//                hashMap["loginType"] = 1
//                apiService?.apply {
//                    requestNoCheck({login(RequestBodyUtils.createRequestBody(hashMap))}, {
//                        loading.set(false)
//                        if(it.code==200){
//                            val token = it.token
//                            find({DataBaseManager.getLastUserInfo()},{ lastUser->
//                                var newUser = false
//                                var userEntity = LoginUserEntity()
//                                when (lastUser) {
//                                    null -> newUser = true
//                                    else -> userEntity = lastUser
//                                }
//                                val now: Int = getNow()
//                                userEntity.lastLoginTime = now
//                                userEntity.loginPassword = userPassword.get()
//                                userEntity.loginAccount = userName.get()
//                                userEntity.token = token
//                                userEntity.role = it.roles
//                                it.user.user?.let { user->
//                                    userEntity.userNick = user.nickName
//                                    userEntity.userName = user.userName
//                                    userEntity.userAge = user.people.age
//                                    userEntity.userSex = user.sex
//                                    userEntity.userCardNum = user.people.cardNum ?: ""
//                                    userEntity.userId = user.userId
//                                }
//                                if (newUser) {
//                                    find({DataBaseManager.insertLoginUser(userEntity)})
//                                } else {
//                                    find({DataBaseManager.updateLoginUser(userEntity)})
//                                }
//                            })
//                            if(it.pwdTime){
//                                postMessage(R.string.dialog_login_confirm_content)
//                                return@requestNoCheck
//                            }
//                            login.postValue(true)
//                        }else{
//                            postMessage(it.msg)
//                            getCaptchaImage()
//                        }
//                    }, {
//                        postMessage(it.error)
//                        loading.set(false)
//                        getCaptchaImage()
//                    })
//                }

            }
        }

    }

    private fun getNow(): Int {
        return (Date().time / 1000).toInt()
    }

    fun getCaptchaImage(){
        // TODO: 刷新验证码
//        requestNoCheck({apiService.captchaImage()},{
//            if(it.code ==200)  {
//                imageUrl.postValue(it.img)
//                userUuid = it.uuid
//                publicKey = it.publicKey
//            }
//        })
    }

}