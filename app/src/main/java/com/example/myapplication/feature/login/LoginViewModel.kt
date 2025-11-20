package com.example.myapplication.feature.login

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.MyApplication
import com.example.myapplication.data.repository.SessionRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.exception.localException
import com.example.myapplication.model.User
import com.example.myapplication.result.asResult
import com.example.myapplication.util.StringUtil
import com.example.myapplication.util.SuperRegularUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel(){
    val loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.None)
    fun onLoginClick(username: String, password: String) {
        if (username.isBlank()) {
            loginUIState.value = LoginUIState.ErrorRes("用户名不能为空")
            return
        }
        if (!SuperRegularUtil.isEmail(username) && !SuperRegularUtil.isPhone(username)) {
            loginUIState.value = LoginUIState.ErrorRes("请输入正确的邮箱或手机号")
            return
        }

        if(TextUtils.isEmpty(password)){
            loginUIState.value = LoginUIState.ErrorRes("密码不能为空")
            return
        }
        if(!StringUtil.isPassword(password)){
            loginUIState.value = LoginUIState.ErrorRes("密码格式不正确，需包含字母和数字，且长度在6-20位之间")
            return
        }

        val params = User(
            phone = if(SuperRegularUtil.isPhone(username)) username else "",
            email = if(SuperRegularUtil.isEmail(username)) username else "",
            password = password
        )
        login(params)
    }

    private fun login(user: User) {
        if (loginUIState.value == LoginUIState.Loading) return
        viewModelScope.launch {
            loginUIState.value = LoginUIState.Loading
                sessionRepository.login(user)
                    .asResult()
                    .collectLatest {
                        if (it.isSuccess) {
                            val result = it.getOrNull()
                            if (result != null) {
                                val sessionPreferences = result.data!!.toPreferences()
                                userDataRepository.login(sessionPreferences,result.data.user.toPreferences())
                                MyApplication.instance.initAfterLogin(sessionPreferences!!)
                                loginUIState.value = LoginUIState.Success
                            } else {
                                loginUIState.value = LoginUIState.ErrorRes("登录失败，返回数据为空")
                            }
                        } else {
                            val exception = it.exceptionOrNull()
                            if (exception != null) {
                                loginUIState.value = LoginUIState.Error(exception.localException())
                            } else {
                                loginUIState.value = LoginUIState.ErrorRes("登录失败，未知错误")
                            }
                        }
                    }
        }
    }

    fun resetUIState() {
        loginUIState.value = LoginUIState.None
    }
}