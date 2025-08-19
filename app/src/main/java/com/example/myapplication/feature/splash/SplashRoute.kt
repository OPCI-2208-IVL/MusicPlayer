package com.example.myapplication.feature.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.SpaceTop

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = viewModel(),
    toMain: () -> Unit
) {
    val timeLeft by viewModel.timeLeft.collectAsStateWithLifecycle()
    val navigateToMain by viewModel.navigateToNext.collectAsState()

    SplashScreen(
        timeLeft = timeLeft,
        onSkip = viewModel::onSkip
    )

    if (navigateToMain) {
        LaunchedEffect(true) {
            toMain()
        }
    }
}

@Composable
fun SplashScreen(
    onSkip: () -> Unit = {},
    timeLeft: Long = 0,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Button(
                onClick = onSkip,
            ) {
                Text("SKIP")
            }
            Text(
                "$timeLeft",
                color = MaterialTheme.colorScheme.onPrimary

            )
        }
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = SpaceTop)
                .align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen()
    }
}
