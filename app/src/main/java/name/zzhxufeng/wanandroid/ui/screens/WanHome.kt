package name.zzhxufeng.wanandroid.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import name.zzhxufeng.wanandroid.data.model.BannerModel
import name.zzhxufeng.wanandroid.ui.composables.*
import name.zzhxufeng.wanandroid.event.MainContainerEvent
import name.zzhxufeng.wanandroid.state.HomeUiState


@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun WanHome(
    uiState: HomeUiState,
    handleEvent: (MainContainerEvent.HomeEvent) -> Unit,
    onArticleClick: (String) -> Unit,
) {
    val state = rememberLazyListState(
        initialFirstVisibleItemIndex = uiState.articleListState.firstVisibleItemIndex,
        initialFirstVisibleItemScrollOffset = uiState.articleListState.firstVisibleItemScrollOffset
    )
    DisposableEffect(Unit) {
        onDispose {
            handleEvent(MainContainerEvent.HomeEvent.UpdateListState(state))
        }
    }
    LazyColumn(
        state = state
    ) {
        item {
            Banners(
                banners = uiState.banners,
                navigateToArticle = onArticleClick,
            )
        }

        itemsIndexed(uiState.articles) { index, item ->
            ArticleItem(model = item, onArticleClick = onArticleClick)
            Divider(color = Color.Gray, thickness = 1.dp)

            LaunchedEffect(key1 = uiState.nextPage, block = {
                if (uiState.articles.size - index < 3) {
                    handleEvent(MainContainerEvent.HomeEvent.LoadMoreArticles)
                }
            })
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
