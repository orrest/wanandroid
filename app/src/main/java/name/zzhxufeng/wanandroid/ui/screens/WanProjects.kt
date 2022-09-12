package name.zzhxufeng.wanandroid.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.ui.composables.UriImage
import name.zzhxufeng.wanandroid.data.repository.ProjectModel
import name.zzhxufeng.wanandroid.event.ProjectsEvent
import name.zzhxufeng.wanandroid.state.ProjectsUiState
import name.zzhxufeng.wanandroid.ui.composables.WanCard
import name.zzhxufeng.wanandroid.viewmodel.ProjectViewModel
import kotlin.random.Random

@Composable
fun WanProjects(
    onArticleClick: (String) -> Unit
) {
    val viewModel: ProjectViewModel = viewModel()

    Projects(
        uiState = viewModel.uiState.collectAsState().value,
        handleEvent = viewModel::handleEvent,
        onArticleClick = onArticleClick
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Projects(
    uiState: ProjectsUiState,
    handleEvent: (ProjectsEvent) -> Unit,
    onArticleClick: (String) -> Unit
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val titles = uiState.titles

    Column {
        titles?.let {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
            ) {
                titles.forEachIndexed { index, projectNameModel ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.scrollToPage(index) } },
                        text = { Text(text = projectNameModel.name) },
                    )
                }
            }

            HorizontalPager(
                count = uiState.projects.size,
                state = pagerState,
            ) {
                val projects = uiState.projects[pagerState.currentPage]
                LazyColumn {
                    if (projects != null) {
                        itemsIndexed(projects) { index, project ->
                            ProjectItem(
                                model = project,
                                onArticleClick = onArticleClick
                            )
                            Divider()
                        }
                    } else {
                        item {
                            LaunchedEffect(key1 = currentPage) {
                                handleEvent(ProjectsEvent.RefreshProjects(titles[currentPage].id, currentPage))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectItem(
    model: ProjectModel,
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit
) {
    WanCard(onClick = { onArticleClick(model.link) }) {
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
}
