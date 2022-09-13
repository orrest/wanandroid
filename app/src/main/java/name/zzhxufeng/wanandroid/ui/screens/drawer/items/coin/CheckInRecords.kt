package name.zzhxufeng.wanandroid.ui.screens.drawer.items.coin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.data.model.CheckInModel
import name.zzhxufeng.wanandroid.event.drawer.CheckInEvent
import name.zzhxufeng.wanandroid.state.drawer.CheckInUiState
import name.zzhxufeng.wanandroid.ui.composables.WanCard
import name.zzhxufeng.wanandroid.ui.composables.WanTopBar
import name.zzhxufeng.wanandroid.utils.SCREEN_PADDING

@Composable
fun CheckInRecords(
    uiState: CheckInUiState,
    handleEvent: (CheckInEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            WanTopBar(
                leftIcon = Icons.Default.ArrowBack,
                onLeftClick = onBackClick,
                desc = stringResource(R.string.desc_check_in_records),
            )
        }
    ) {
        val padding = it
        Column(modifier = Modifier.padding(horizontal = SCREEN_PADDING.dp)) {
            LazyColumn {
                itemsIndexed(uiState.checkInRecords) { index, item ->
                    CheckInRecordItem(item)

                    LaunchedEffect(key1 = uiState.nextPage, block = {
                        if (uiState.checkInRecords.size - index == 3) {
                            handleEvent(CheckInEvent.LoadMore)
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun CheckInRecordItem(
    checkInModel: CheckInModel
) {
    WanCard(onClick = { /*no*/ }) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = checkInModel.reason)
            Text(text = checkInModel.desc)
            Text(text = "总计：${checkInModel.coinCount}")
        }
    }
}