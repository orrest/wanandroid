package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import name.zzhxufeng.wanandroid.repository.ArticleModel
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel

@Composable
fun WanHome(
    viewModel: MainViewModel,
    onArticleClicked: (String) -> Unit
) {
    val flowItems = viewModel.articleFlow.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
    ) {
        items(flowItems) { item ->
            if (item == null) {
                NetworkErrorIndicator()
            } else {
                ArticleItem(model = item, onArticleClicked = onArticleClicked)
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
fun NetworkErrorIndicator() {
    TODO("Not yet implemented")
}

@Composable
private fun ArticleItem(
    model: ArticleModel,
    onArticleClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onArticleClicked(model.link) }
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            model.tags!!.forEach {
                Text(text = it.name)
            }
            Text(text = model.author!!)
            Text(text = model.niceDate)
        }
        Text(text = model.title, fontSize = 22.sp)
        Text(text = "${model.superChapterName}/${model.chapterName}")
    }
}

@Composable
fun ListCircularProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
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
