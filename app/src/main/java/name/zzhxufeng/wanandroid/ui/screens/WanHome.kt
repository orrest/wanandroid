package name.zzhxufeng.wanandroid.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.delay
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
            BannerImage(
                banner = uiState.currentBanner(),
                onBannerClick = onArticleClick,
                onAutoBannerChange = { handleEvent(MainContainerEvent.HomeEvent.AutoChangeBanner) },
            )
        }

        itemsIndexed(uiState.articles) { index, item ->
            ArticleItem(model = item, onArticleClick = onArticleClick)

            LaunchedEffect(key1 = uiState.nextPage, block = {
                if (uiState.articles.size - index == 3) {
                    handleEvent(MainContainerEvent.HomeEvent.LoadMoreArticles)
                }
            })
        }
    }
}

@Composable
fun BannerImage(
    banner: BannerModel?,
    onBannerClick: (String) -> Unit,
    onAutoBannerChange: () -> Unit,
    modifier: Modifier = Modifier,
    delayMillis: Long = 5000
) {
    LaunchedEffect(key1 = banner){
        delay(delayMillis)
        onAutoBannerChange()
    }

    Crossfade(
        targetState = banner,
        animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
    ) {
        val bm = it
        Card(
            shape = MaterialTheme.shapes.medium,
        ) {
            banner?.let {
                Column(
                    modifier = Modifier.clickable { onBannerClick(banner.url) }
                ) {
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = banner.imagePath)
                            .apply(block = fun ImageRequest.Builder.() {
                                transformations(RoundedCornersTransformation())
                            }).build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )

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
}
