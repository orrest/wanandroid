package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import name.zzhxufeng.wanandroid.composables.PagingSourceLazyColumn
import name.zzhxufeng.wanandroid.repository.ArticleModel
import name.zzhxufeng.wanandroid.repository.BannerModel
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel

@Composable
fun Home(
    viewModel: MainViewModel,
    onArticleClicked: (String) -> Unit,
) {
    Banners(
        banners = viewModel.banners,
        navigateToArticle = onArticleClicked,
    )

    PagingSourceLazyColumn(
        flowItems = viewModel.articleFlow.collectAsLazyPagingItems()
    ){ item ->
        ArticleItem(
            model = item,
            onArticleClicked = onArticleClicked,
            modifier = Modifier
        )
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

