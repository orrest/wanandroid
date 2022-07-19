package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.composables.UriImage
import name.zzhxufeng.wanandroid.repository.ProjectModel
import name.zzhxufeng.wanandroid.viewmodel.HomeViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WanProject(
    viewModel: HomeViewModel,
    onArticleClick: (String) -> Unit
) {
    val pagerState = rememberPagerState()
    /* pager 的标签不显示是为啥？多线程问题？*/
    /* 做个实验：tabrow的数据不从viewModel取。是能够显示的，因此写法没问题。
    *  问题在于从viewModel取数据。
    *  val list = viewModel.projectsName 这样取过来就没问题，可以用...
    * */

    val list = viewModel.projectsName
    val pagerMap = viewModel.projectFlowMap
    val scope = rememberCoroutineScope()

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            list.forEachIndexed { index, projectNameModel ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } },
                    text = { Text(text = projectNameModel.name) },
                )
            }
        }

        HorizontalPager(
            count = list.size,
            state = pagerState
        ) {
            val flowItems = pagerMap.get(list[pagerState.currentPage].id)!!.collectAsLazyPagingItems()
            LazyColumn {
                items(flowItems) {
                    ProjectItem(it!!, Modifier)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ProjectItem(
    model: ProjectModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        UriImage(
            uri = model.envelopePic,
            modifier = Modifier.width(180.dp).height(320.dp)
        )
        Column {
            Text(text = model.title)
            Text(text = model.desc)
            Row {
                Text(text = model.author)
                Text(text = model.niceDate)
            }
        }
    }
}
