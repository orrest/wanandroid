package name.zzhxufeng.wanandroid.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.data.model.NaviData
import name.zzhxufeng.wanandroid.state.NavUiState
import name.zzhxufeng.wanandroid.ui.composables.ErrorDialog
import name.zzhxufeng.wanandroid.ui.composables.WanCard
import name.zzhxufeng.wanandroid.viewmodel.NavViewModel

@Composable
fun WanNavi() {
    val viewModel: NavViewModel = viewModel()

    Navi(
        uiState = viewModel.uiState.collectAsState().value,
        error = viewModel.errorMsg.value,
        dismissError = { viewModel.dismissError() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navi(
    uiState: NavUiState,
    error: String?,
    dismissError: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Row(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.widthIn(100.dp)
        ) {
            itemsIndexed(uiState.naviList) { index, naviData ->
                TitleItem(
                    naviData = naviData,
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(
                                uiState.getScrollToIndex(index)
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        LazyColumn(
            state = listState
        ){
            uiState.naviList.forEachIndexed { index, naviData ->
                stickyHeader {
                    WanCard(
                        onClick = { /*TODO*/ },
                        padding = false,
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = naviData.name)
                    }
                }
                items(naviData.articles) { article ->
                    WanCard(onClick = { /*TODO*/ }) {
                        Text(text = article.title)
                    }
                }
                item { Divider() }
            }
        }
    }

    error?.let {
        ErrorDialog(
            error = error,
            dismissError = dismissError
        )
    }
}

@Composable
private fun TitleItem(
    naviData: NaviData,
    onClick: () -> Unit
) {
    WanCard(
        onClick = { onClick() }
    ) {
        Text(text = naviData.name)
    }
}
