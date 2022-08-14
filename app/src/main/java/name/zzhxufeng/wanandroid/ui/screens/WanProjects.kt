package name.zzhxufeng.wanandroid.ui.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.ui.composables.UriImage
import name.zzhxufeng.wanandroid.data.ProjectModel
import name.zzhxufeng.wanandroid.ui.composables.WanWebView
import name.zzhxufeng.wanandroid.viewmodel.HomeViewModel
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
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
            modifier = Modifier
                .width(180.dp)
                .height(320.dp)
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