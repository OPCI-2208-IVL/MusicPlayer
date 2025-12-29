package com.example.myapplication.feature.createsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.component.MyCenterTopAppBar
import com.example.myapplication.model.Sheet
import com.example.myapplication.ui.theme.SpaceExtraMedium
import com.example.myapplication.ui.theme.SpaceExtraOuter
import com.example.myapplication.ui.theme.SpaceExtraSmall
import com.example.myapplication.ui.theme.SpaceOuter
import com.example.myapplication.util.ResourceUtil
import com.google.common.base.Strings

@Composable
fun CreateSheetRoute(
    finishPage: () -> Unit,
    viewModel: CreateSheetViewModel = hiltViewModel()
) {

    val data by viewModel.data.collectAsStateWithLifecycle()
    val tipError by viewModel.tipError.collectAsStateWithLifecycle()

    CreateSheetScreen(
        finishPage = finishPage,
        onSaveClick = viewModel::onSaveClick,
        onValueChange = viewModel::onValueChange,
        data = data
    )

    LaunchedEffect(viewModel.finish.value) {
        if (viewModel.finish.value) {
            finishPage()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSheetScreen(
    data: Sheet ,
    finishPage: () -> Unit,
    onSaveClick: () -> Unit,
    onValueChange: (Sheet) -> Unit = {},
) {
    Scaffold(
        topBar = {
            MyCenterTopAppBar(
                titleText = "创建歌单",
                finishPage = finishPage,
                actions = {
                    TextButton(
                        onClick = onSaveClick
                    ) {
                        Text("保存")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            MySettingRow(
                modifier = Modifier
                    .clickable{

                    }
            ) {
                MySettingTitle(
                    title = "歌单封面",
                    modifier = Modifier.weight(1f)
                )

                if (data.icon != null) {
                    AsyncImage(
                        model = ResourceUtil.rel2abs(data.icon),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(vertical = SpaceExtraOuter)
                            .size(64.dp)
                            .clip(MaterialTheme.shapes.extraSmall),
                    )
                } else {
                    Image(
                        painter = painterResource(
                            id = R.drawable.placeholder
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(vertical = SpaceExtraOuter)
                            .size(64.dp)
                            .clip(MaterialTheme.shapes.extraSmall),
                    )
                }
            }

            MySettingRow(
                modifier = Modifier
            ) {
                MySettingTitle(
                    title = "歌单名称",
                    modifier = Modifier.weight(1f)
                )

                MySettingInput(
                    value = data.title,
                    onValueChanged = {
                        onValueChange(
                            data.copy(
                                title = it
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = SpaceExtraSmall)
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(start = SpaceOuter, end = SpaceExtraMedium),

                ) {
                MySettingTitle(
                    title = "歌单描述",
                    modifier = Modifier.weight(1f)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = SpaceOuter),
            ) {
                BasicTextField(
                    value = data.detail ?: "",
                    onValueChange = {
                        onValueChange(
                            data.copy(
                                detail = it
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true,
                    modifier = Modifier.fillMaxSize()
                )
                if (Strings.isNullOrEmpty(data.detail)) {
                    Text(
                        text = "请输入歌单描述",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }

        }
    }
}

@Composable
fun MySettingTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
    )
}

@Composable
fun MySettingRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = SpaceExtraSmall)
            .fillMaxWidth()
            .heightIn(min = 50.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = SpaceOuter),

        ) {
        content()
    }
}

@Composable
fun MySettingInput(value: String,
                   onValueChanged: (String) -> Unit,
                   modifier: Modifier = Modifier,
) {
    var focused by remember {
        mutableStateOf(false)
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Right,
            color = MaterialTheme.colorScheme.outline,
        ),
        singleLine = true,
        modifier = modifier
            .onFocusChanged {
                focused = it.isFocused
            }
    )
}