package name.zzhxufeng.wanandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.event.MainContainerEvent
import name.zzhxufeng.wanandroid.state.MainContainerUiState
import name.zzhxufeng.wanandroid.ui.composables.WanBottomBar
import name.zzhxufeng.wanandroid.ui.composables.WanTopBar
import name.zzhxufeng.wanandroid.ui.screens.drawer.DrawerNavigation
import name.zzhxufeng.wanandroid.utils.SCREEN_PADDING

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SCREEN_PADDING.dp)
                .padding(bottom = it.calculateBottomPadding())
        ) {
            when (uiState.currentScreen) {
                WanScreen.Home -> {
                    WanHome(
                        uiState = uiState.homeUiState,
                        handleEvent = handleEvent,
                        onArticleClick = onArticleClick,
                    )
                }
                WanScreen.Navi -> {
                    WanNavi(onArticleClick)
                }
                WanScreen.Projects -> {
                    WanProjects(
                        onArticleClick = onArticleClick
                    )
                }
                else -> {}
            }
        }
    }
}
