package name.zzhxufeng.wanandroid.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.composables.WanBottomBar
import name.zzhxufeng.wanandroid.composables.WanTopBar
import name.zzhxufeng.wanandroid.repository.ArticleModel
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel

@Composable
fun WanHome(
    viewModel: MainViewModel,
    navController: NavHostController,
    onArticleClicked: (String) -> Unit
) {
    /*抽屉*/
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    /*
    * bottom bar
    * remember意味着使用同一个变量，只初始化一次
    * state代表它将触发重组
    * */
    val selectedScreen = remember { mutableStateOf<WanScreen>(WanScreen.Home) }

    /*只需要初始化一次，并且不需要作为state更新*/
    val allScreens = remember { WanScreen.allScreens() }

    /*作为state，它的更新会触发重组; 每次重组重新计算currentRoute*/
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = WanScreen.fromRouteToScreen(
        backstackEntry.value?.destination?.route
    )

    /*
    * TODO
    * 这个currentScreen一直是Home容器，没有发生变化，（并且它也不是一个State）
    * */
    Log.d("currentScreen", currentScreen.toString())

    val state = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            WanTopBar(
                title = "首页",
                onDrawerClick = { scope.launch { scaffoldState.drawerState.open() } },
                onSearchClick = { navController.navigate(WanScreen.Search.route) }
            )
        },
        bottomBar = {
            WanBottomBar(
                allScreens = allScreens,
                /*事件下降，状态上升：*/
                /*让这个状态的改变发生的事件发生在它该发生的地方*/
                /*这个状态反过来影响这个地方的重组*/
                onScreenSelected = { screen -> selectedScreen.value = screen },
                currentScreen = selectedScreen.value
            )
        },
        drawerContent = { WanDrawer() },
    ) {
        val padding = it
        Log.d("Switching...", selectedScreen.value.toString())

        when (selectedScreen.value) {
            WanScreen.Home -> {
                HomeArticleList(
                    viewModel = viewModel,
                    onArticleClicked = onArticleClicked,
                    state = state
                )
            }
            WanScreen.Public -> {
                Text("Public")
            }
            else -> {}
        }
    }
}

@Composable
private fun HomeArticleList(
    viewModel: MainViewModel,
    onArticleClicked: (String) -> Unit,
    state: LazyListState
) {
    Log.d("WanHome#HomeArticleList",
        "entering... ${state.firstVisibleItemIndex}, ${state.firstVisibleItemScrollOffset}")
    val flowItems = viewModel.articleFlow.collectAsLazyPagingItems()

    // A Google issue: https://issuetracker.google.com/issues/177245496?pli=1
    val refresh = flowItems.loadState.refresh
    if (flowItems.itemCount == 0 && refresh is LoadState.NotLoading ) {
        Log.d("WanHome#HomeArticleList", "skip...")
        return //skip dummy state, waiting next compose
    }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        state = state
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
