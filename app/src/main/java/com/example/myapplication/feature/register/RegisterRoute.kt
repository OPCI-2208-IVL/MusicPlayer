package com.example.myapplication.feature.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.component.MyCenterTopAppBar
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.model.User
import com.example.myapplication.ui.theme.SpaceLarge

@Composable
fun RegisterRoute(
    finishPage: () -> Unit ,
    finishAllPage: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val data by viewModel.data.collectAsState()

    RegisterScreen(
        data = data,
        uiState = uiState,
        finishPage = finishPage,
        onValueChange = viewModel::onValueChange,
        onRegisterClick = viewModel::onRegisterClick
    )

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is RegisterUIState.Success -> {
                finishAllPage()
            }

            is RegisterUIState.ErrorRes -> {
                Toast
                    .makeText(
                        context,
                        state.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

            is RegisterUIState.Error -> {
                Toast
                    .makeText(
                        context,
                        state.exception.tipString,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

            else -> {}
        }

        viewModel.resetUIState()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    uiState: RegisterUIState = RegisterUIState.None,
    finishPage: () -> Unit = {},
    onValueChange:(User) -> Unit = {},
    onRegisterClick: () -> Unit = {},
    data: User = User(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            MyCenterTopAppBar(
                titleText = "Register",
                finishPage = finishPage,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val scroll = rememberScrollState()
        Column(
            verticalArrangement = Arrangement.spacedBy(SpaceLarge),
            modifier = Modifier
                .imePadding()
                .fillMaxSize()
                .verticalScroll(scroll)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickableNoRipple {
                    keyboardController?.hide()
                }
                .padding(paddingValues)
                .padding(start = SpaceLarge, end = SpaceLarge, top = SpaceLarge)
        ) {
            TextField(
                value = data.nickname,
                onValueChange = {
                    onValueChange(data.copy(nickname = it))
                },
                label = { Text("昵称")},
                placeholder = { Text("请输入昵称") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = data.phone,
                onValueChange = {
                    onValueChange(data.copy(phone = it))
                },
                label = { Text("手机号")},
                placeholder = { Text("请输入手机号") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = data.email,
                onValueChange = {
                    onValueChange(data.copy(email = it))
                },
                label = { Text("邮箱")},
                placeholder = { Text("请输入邮箱（可选）") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = data.password,
                onValueChange = {
                    onValueChange(data.copy(password = it))
                },
                label = { Text("密码")},
                placeholder = { Text("请输入密码（6~20位）") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = data.confirmPassword,
                onValueChange = {
                    onValueChange(data.copy(confirmPassword = it))
                },
                label = { Text("确认密码")},
                placeholder = { Text("请确认密码") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onRegisterClick,
                Modifier.fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "注册")
            }
        }

    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}
