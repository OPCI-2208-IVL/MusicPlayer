package com.example.myapplication.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.component.MyCenterTopAppBar
import com.example.myapplication.ui.theme.SpaceExtraSmall2
import com.example.myapplication.ui.theme.SpaceLarge

@Composable
fun LoginRoute(
    finishPage: () -> Unit,
    toRegister: () -> Unit,
    toSetPassword: () -> Unit,
    finishAllLoginPage: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreen(
        finishPage = finishPage,
        toRegister = toRegister,
        toSetPassword = toSetPassword,
        finishAllLoginPage = finishAllLoginPage,
        onLoginClick = viewModel::onLoginClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    finishPage: () -> Unit,
    toRegister: () -> Unit,
    toSetPassword: () -> Unit,
    finishAllLoginPage: () -> Unit,
    onLoginClick: (String, String) -> Unit = { _, _ -> }
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold (
        topBar = {
            MyCenterTopAppBar(
                titleText = "登录",
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                finishPage = finishPage,
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(paddingValues)
                .padding(start = SpaceLarge, end = SpaceLarge, top = SpaceLarge),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserTextField(
                value = username,
                onValueChanged = { username = it }
            )

            PasswordTextField(
                value = password,
                onValueChanged = { password = it },
                loginClick = {
                    keyboardController?.hide()
                    onLoginClick(username, password)
                }
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    onLoginClick(username, password)
                },
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth()
                    .padding(top = SpaceLarge)
            ) {
                Text(text = "登录")
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                TextButton(
                    onClick = toRegister,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent
                    )
                ) { Text(text = "注册账号") }

                TextButton(
                    onClick = toSetPassword,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent
                    )
                ) { Text(text = "忘记密码？") }

            }

        }
    }
}

@Composable
fun UserTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var focused by remember { mutableStateOf(false) }

    OutlinedTextField(
        leadingIcon =
        {
            Icon(
                imageVector = Icons.Default.PermIdentity,
                contentDescription = null
            )
        },
        trailingIcon =
        {
            if (focused and value.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChanged("") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(24.dp)
                            .padding(SpaceExtraSmall2)
                    )
                }
            }
        },

        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = "手机号/邮箱") },
        placeholder = { Text(text = "请输入手机号/邮箱") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                focused = it.isFocused
            },
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    loginClick: () -> Unit = {}
) {
    var focused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        leadingIcon =
        {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
        },
        trailingIcon =
        {
            if ( value.isNotEmpty()) {
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(
                        imageVector = if( isPasswordVisible) {
                            Icons.Filled.Lightbulb
                        } else{
                            Icons.Outlined.Lightbulb
                        },

                        contentDescription = null,
                        modifier = Modifier
                            .padding(24.dp)
                            .padding(SpaceExtraSmall2)
                    )
                }
            }
        },

        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = "密码") },
        placeholder = { Text(text = "请输入密码") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                loginClick()
            }
        ),
        singleLine = true,
        visualTransformation =
        if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
               }
        ,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                focused = it.isFocused
            },
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        finishPage = {},
        toRegister = {},
        toSetPassword = {},
        finishAllLoginPage = {}
    )
}