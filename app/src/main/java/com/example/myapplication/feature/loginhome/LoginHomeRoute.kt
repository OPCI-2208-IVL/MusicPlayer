package com.example.myapplication.feature.loginhome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Space3XLarge
import com.example.myapplication.ui.theme.SpaceLarge

@Composable
fun LoginHomeRoute(
    finishPage: () -> Unit,
    toLogin: () -> Unit,
    toCodeLogin: () -> Unit,
    finishAllLoginPage: () -> Unit
) {

    LoginHomeScreen(
        finishPage = finishPage,
        toLogin = toLogin,
        toCodeLogin = toCodeLogin,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginHomeScreen(
    finishPage: () -> Unit = {},
    toLogin: () -> Unit = {},
    toCodeLogin: () -> Unit = {},
    toWebPage: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = finishPage
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                        )
                    }
                },
                title = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(paddingValues)
        ){
            Image(
                painter = painterResource(R.drawable.placeholder),
                contentDescription = "Login Home",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp)
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
            )

            BottomView(
                toLogin = toLogin,
                toCodeLogin = toCodeLogin,
                toWebPage = toWebPage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = SpaceLarge,
                        end = SpaceLarge,
                        bottom = Space3XLarge
                    )
            )
        }
    }
}

@Composable
fun BottomView(
    modifier: Modifier = Modifier,
    toLogin: () -> Unit = {},
    toCodeLogin: () -> Unit = {},
    toWebPage: () -> Unit = {},
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = toLogin,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
           Text(text = "Account Login")
        }

        Spacer(modifier = Modifier.size(SpaceLarge))

        Button(
            onClick = toCodeLogin,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
           Text(text = "Code Login")
        }

        Spacer(modifier = Modifier.size(SpaceLarge))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(70.dp)
                .fillMaxWidth()
        ) {
            RoundLoginButton(
                icon = R.drawable.passport_sns_qq
            )

            RoundLoginButton(
                icon = R.drawable.passport_sns_wechat
            )

            RoundLoginButton(
                icon = R.drawable.passport_sns_weibo
            )

            RoundLoginButton(
                icon = R.drawable.passport_sns_google
            )
        }
    }
}

@Composable
fun RoundLoginButton(
    @DrawableRes icon:Int,
    onClick: () -> Unit = {},
) {
    Image(
        painter = painterResource(icon),
        contentDescription = "Login Button",
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(
                onClick = onClick
            )
    )
}

@Composable
@Preview
fun LoginHomePreview() {
    LoginHomeScreen()
}

@Composable
@Preview
fun BottomViewPreview() {
    BottomView()
}