package name.zzhxufeng.wanandroid.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.state.NavUiState
import name.zzhxufeng.wanandroid.viewmodel.NavViewModel
import kotlin.math.roundToInt

@Composable
fun WanNavi() {
    val viewModel: NavViewModel = viewModel()

    Navi(
        uiState = viewModel.uiState.collectAsState().value,
        error = viewModel.errorMsg.value,
        dismissError = { viewModel.dismissError() }
    )
}

@Composable
fun Navi(
    uiState: NavUiState,
    error: String?,
    dismissError: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollStateTitle = rememberScrollState()
    val scrollStateContent = rememberScrollState()
    val contentPosition = remember { mutableStateListOf<Float>() }
    val state = uiState.naviResponse

    if (state == null) {
        CircularProgressIndicator()
    } else {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollStateTitle)
            ) {
                state.data.forEachIndexed { index, naviData ->
                    Text(
                        text = naviData.name,
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    scrollStateContent.animateScrollTo(contentPosition[index].roundToInt())
                                }
                            }
                            .padding(8.dp),
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }

            Column(
                modifier = Modifier
                    .verticalScroll(scrollStateContent)
            ) {
                state.data.forEachIndexed { index, naviData ->
                    Column {
                        Text(
                            text = naviData.name
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(8.dp),
                            userScrollEnabled = false,
                            modifier = Modifier.height(400.dp)
                        ) {
                            items(naviData.articles) {
                                Text(
                                    text = it.title,
                                    modifier = Modifier.onGloballyPositioned { coordinates ->
                                        contentPosition.add(coordinates.positionInParent().y)
                                    }
                                )
                            }
                        }
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
    }

}

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    error: String,
    dismissError: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            dismissError()
        },
        confirmButton = {
            TextButton(onClick = {
                dismissError()
            }) {
                Text(text = stringResource(id = R.string.error_action))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.error_title), fontSize = 18.sp)
        },
        text = {
            Text(text = error)
        }
    )
}
