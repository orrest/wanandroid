package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import name.zzhxufeng.wanandroid.composables.*
import name.zzhxufeng.wanandroid.repository.BannerModel
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel

/*
* 如果
* LazyColumn#
*   LazyRow
*   LazyColumn*
* 那么LazyColumn*的滚动与LazyColumn#的滚动是分开的，造成非预期的滚动效果。
*
* 所以需要
* LazyColumn
*   item    { LazyRow }
*   items   { }
* 此时LazyRow仅仅属于一个LazyColumn，因此会跟随列表一起向上滚动。
* */
@Composable
fun WanHome(
    viewModel: MainViewModel,
    onArticleClick: (String) -> Unit,
) {
    val flowItems = viewModel.articleFlow.collectAsLazyPagingItems()

    LazyColumn {
        item {
            Banners(
                banners = viewModel.banners,
                navigateToArticle = onArticleClick,
            )
        }

        items(flowItems) { item ->
            if (item == null) {
                NetworkErrorIndicator()
            } else {
                ArticleItem(model = item, onArticleClick = onArticleClick)
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
private fun Banners(
    banners: List<BannerModel>,
    navigateToArticle: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier = modifier.padding(5.dp)) {
        items(banners) { item ->
            BannerImage(
                banner = item,
                onBannerClick = navigateToArticle,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun BannerImage(
    banner: BannerModel,
    onBannerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.size(400.dp, 240.dp)
    ) {
        Column(
            modifier = Modifier.clickable { onBannerClick(banner.url) }
        ){
            val painter = rememberImagePainter(
                data = banner.imagePath,
                builder = { transformations(RoundedCornersTransformation()) }
            )

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = banner.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
