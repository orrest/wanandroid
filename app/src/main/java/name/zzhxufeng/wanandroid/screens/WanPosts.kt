package name.zzhxufeng.wanandroid.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import name.zzhxufeng.wanandroid.composables.ArticleItem
import name.zzhxufeng.wanandroid.composables.PagingSourceLazyColumn
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel

@Composable
fun WanPosts(
    viewModel: MainViewModel,
    onArticleClick: (String) -> Unit
){
    PagingSourceLazyColumn(
        flowItems = viewModel.postsFlow.collectAsLazyPagingItems(),
    ){ item ->
        ArticleItem(
            model = item,
            onArticleClick = onArticleClick,
            modifier = Modifier
        )
    }
}
