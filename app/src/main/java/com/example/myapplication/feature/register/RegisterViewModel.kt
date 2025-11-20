package com.example.myapplication.feature.register

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.SessionRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.data.repository.UserRepository
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
class RegisterViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userDataRepository: UserDataRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val uiState = MutableStateFlow<RegisterUIState>(RegisterUIState.None)
    val data = MutableStateFlow<User>(User())

    fun resetUIState() {
        uiState.value = RegisterUIState.None
    }

    fun onValueChange(user: User) {
        data.value = user
    }

    fun onRegisterClick() {
        val user = data.value
        if (user.nickname.isBlank()) {
            uiState.value = RegisterUIState.ErrorRes("用户名不能为空")
            return
        }
        if (!SuperRegularUtil.isEmail(user.email) && user.email.isNotBlank()) {
            uiState.value = RegisterUIState.ErrorRes("请输入正确的邮箱")
            return
        }
        if(user.phone.isBlank()){
            uiState.value = RegisterUIState.ErrorRes("手机号不能为空")
            return
        }
        if (!SuperRegularUtil.isPhone(user.phone)){
            uiState.value = RegisterUIState.ErrorRes("请输入正确的手机号")
            return
        }
        if(TextUtils.isEmpty(user.password)){
            uiState.value = RegisterUIState.ErrorRes("密码不能为空")
            return
        }
        if(!StringUtil.isPassword(user.password)){
            uiState.value = RegisterUIState.ErrorRes("密码格式不正确，需包含字母和数字，且长度在6-20位之间")
            return
        }
        if(user.password != user.confirmPassword){
            uiState.value = RegisterUIState.ErrorRes("两次输入的密码不一致")
            return
        }

        val params = User(
            nickname = user.nickname,
            phone = user.phone,
            email = user.email,
            password = user.password
        )

        viewModelScope.launch {
            userRepository.register(params)
                .asResult()
                .collectLatest {
                    if (it.isSuccess) {
                        val response = it.getOrNull()
                        if (response != null) {
                            uiState.value = RegisterUIState.Success
                            login(user)
                        } else {
                            uiState.value = RegisterUIState.ErrorRes("注册失败，返回数据为空")
                        }
                    } else {
                        val exception = it.exceptionOrNull()
                        if (exception != null) {
                            uiState.value = RegisterUIState.Error(exception.localException())
                        } else {
                            uiState.value = RegisterUIState.ErrorRes("注册失败，未知错误")
                        }
                    }
                }
        }
    }

    private fun login(user: User) {
        viewModelScope.launch {
            sessionRepository.login(user)
                .asResult()
                .collectLatest {
                    if (it.isSuccess) {
                        val result = it.getOrThrow()
                        val sessionPreferences = result.data!!.toPreferences()
                        userDataRepository.setSession(sessionPreferences)
                        userDataRepository.setUser(result.data.user.toPreferences())
                    } else {
                        uiState.value = RegisterUIState.ErrorRes("注册成功，自动登录失败，请手动登录")
                    }
                }
        }
    }
}