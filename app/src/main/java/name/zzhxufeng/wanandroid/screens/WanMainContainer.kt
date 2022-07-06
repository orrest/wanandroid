package name.zzhxufeng.wanandroid.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.composables.WanBottomBar
import name.zzhxufeng.wanandroid.composables.WanTopBar
import name.zzhxufeng.wanandroid.repository.ArticleModel
import name.zzhxufeng.wanandroid.repository.BannerModel
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel

@Composable
fun WanMainContainer(
    viewModel: MainViewModel,
    navController: NavHostController,
    onArticleClick: (String) -> Unit
) {
    /*抽屉*/
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    /*
    * bottom bar
    * remember意味着使用同一个变量，只初始化一次
    * state代表它将触发重组
    * */
    val selectedScreen = viewModel.selectedScreen

    /*只需要初始化一次，并且不需要作为state更新*/
    val allScreens = remember { WanScreen.allScreens() }

    Log.d("selectedScreen", selectedScreen.toString())
    Log.d("Is this the same ViewModel?", viewModel.toString())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            WanTopBar(
                currentScreen = selectedScreen.value,
                onDrawerClick = { scope.launch { scaffoldState.drawerState.open() } },
                onSearchClick = { navController.navigate(WanScreen.Search.route) {
                    launchSingleTop = true
                } }
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
                Home(
                    viewModel = viewModel,
                    onArticleClicked = onArticleClick,
                )
            }
            WanScreen.Posts -> {
                Text("Posts")
            }
            WanScreen.Path -> {
                Text("Path")
            }
            WanScreen.Projects -> {
                Text("Projects")
            }
            else -> {}
        }
    }
}

@Composable
fun HomeImageList(
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
private fun Home(
    viewModel: MainViewModel,
    onArticleClicked: (String) -> Unit,
) {
    val flowItems = viewModel.articleFlow.collectAsLazyPagingItems()

    // A Google issue: https://issuetracker.google.com/issues/177245496?pli=1

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        modifier = Modifier.height(900.dp)
    ) {
        item{
            HomeImageList(
                banners = viewModel.banners.value,
                navigateToArticle = onArticleClicked,
            )
        }

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
