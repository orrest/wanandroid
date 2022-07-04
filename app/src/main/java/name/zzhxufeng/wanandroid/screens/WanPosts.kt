package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import name.zzhxufeng.wanandroid.repository.ArticleModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WanPosts(
    authors: List<Author>,
    /*a view model method to get data*/
    searchAuthorPosts: (Author) -> List<ArticleModel>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { positions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, positions)
                )
            }
        ) {
            for (author in authors) {
                Text(text = author.name)
            }
        }

        HorizontalPager(count = authors.size) {
            authors.forEach { author ->
                var posts: List<ArticleModel>? = null
                /*TODO 这不知道具体该怎么整*/
                LaunchedEffect(key1 = author, block = {
                    posts = searchAuthorPosts(authors[pagerState.currentPage])
                })

                LazyColumn {
                    if (posts == null) {
                        item { /*progress indicator*/ }
                    } else {
                        items(posts!!) {
                            PostsItem(post = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostsItem(
    post: ArticleModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(text = post.author.toString())
            Text(text = post.niceDate.toString())
        }
        Text(text = post.title.toString())
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            post.tags!!.forEach {
                Text(text = it.name)
            }
            Icon(imageVector = Icons.Filled.MonitorHeart, contentDescription = "")
        }
    }
}

data class Author(
    val name: String,
    val visible: Int,/*0 or 1*/
)