package name.zzhxufeng.wanandroid.screens

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.composables.WanBottomBar
import name.zzhxufeng.wanandroid.composables.WanTopBar
import name.zzhxufeng.wanandroid.viewmodel.HomeViewModel

@Composable
fun WanMainContainer(
    viewModel: HomeViewModel,
    navController: NavHostController,
    onArticleClick: (String) -> Unit
) {
    /*抽屉*/
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
        drawerContent = {
            WanDrawer()
        },
    ) {
        val padding = it
        Log.d("Switching...", selectedScreen.value.toString())

        when (selectedScreen.value) {
            WanScreen.Home -> {
                WanHome(
                    viewModel = viewModel,
                    onArticleClick = onArticleClick,
                )
            }
            WanScreen.Navi -> {
                WanNavi(
                    viewModel = viewModel,
                    onNaviClick = onArticleClick,
                )
            }
            WanScreen.Projects -> {
                WanProject(
                    viewModel = viewModel,
                    onArticleClick = onArticleClick
                )
            }
            else -> {}
        }
    }
}
