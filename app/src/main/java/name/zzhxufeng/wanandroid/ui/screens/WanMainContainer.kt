package name.zzhxufeng.wanandroid.ui.screens

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.ui.composables.WanBottomBar
import name.zzhxufeng.wanandroid.ui.composables.WanTopBar
import name.zzhxufeng.wanandroid.ui.screens.drawer.DrawerNavigation
import name.zzhxufeng.wanandroid.event.MainContainerEvent
import name.zzhxufeng.wanandroid.state.MainContainerUiState

@Composable
fun WanMainContainer(
    uiState: MainContainerUiState,
    handleEvent: (MainContainerEvent) -> Unit,
    onArticleClick: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    /*抽屉*/
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    /*只需要初始化一次，并且不需要作为state更新*/
    val allScreens = remember { WanScreen.allScreens() }

    Log.d("selectedScreen", uiState.currentScreen.toString())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            WanTopBar(
                desc = uiState.currentScreen.route,
                leftIcon = Icons.Default.Menu,
                rightIcon = Icons.Default.Search,
                onLeftIconClick = { scope.launch { scaffoldState.drawerState.open() } },
                onRightIconClick = onSearchClick
            )
        },
        bottomBar = {
            WanBottomBar(
                allScreens = allScreens,
                /*事件下降，状态上升：*/
                /*让这个状态的改变发生的事件发生在它该发生的地方*/
                /*这个状态反过来影响这个地方的重组*/
                onScreenSelected = {
                    screen -> handleEvent(MainContainerEvent.ChangeScreen(screen))
                },
                currentScreen = uiState.currentScreen
            )
        },
        drawerContent = {
            DrawerNavigation()
        },
    ) {
        val padding = it
        Log.d("Switching...", uiState.currentScreen.toString())

        when (uiState.currentScreen) {
            WanScreen.Home -> {
                WanHome(
                    uiState = uiState.homeUiState,
                    handleEvent = handleEvent,
                    onArticleClick = onArticleClick,
                )
            }
            WanScreen.Navi -> {
                WanNavi()
            }
            WanScreen.Projects -> {
                /*TODO*/
//                WanProject(
//                    viewModel = viewModel(),
//                    onArticleClick = onArticleClick
//                )
            }
            else -> {}
        }
    }
}
