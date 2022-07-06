package name.zzhxufeng.wanandroid.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Divider
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items

@Composable
fun <T : Any> PagingSourceLazyColumn(
    flowItems: LazyPagingItems<T>,
    item: @Composable LazyItemScope.(T) -> Unit
) {
    // A Google issue: https://issuetracker.google.com/issues/177245496?pli=1

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        modifier = Modifier.height(900.dp)
    ) {
        items(flowItems) { item ->
            if (item == null) {
                NetworkErrorIndicator()
            } else {
                item(item)
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }

        flowItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { ListCircularProgressIndicator() }
                }
                loadState.append is LoadState.Loading -> {
                    item { ListCircularProgressIndicator() }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = flowItems.loadState.refresh as LoadState.Error
                    item { BottomSnackBar(e.toString()) }
                }
                loadState.append is LoadState.Error -> {
                    val e = flowItems.loadState.append as LoadState.Error
                    item { BottomSnackBar(e.toString()) }
                }
            }
        }
    }
}

@Composable
private fun BottomSnackBar(
    errorMsg: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ){
        Snackbar {
            Text(text = "SHOW SNACK BAR MESSAGE: ${errorMsg}}")
        }
    }
}
