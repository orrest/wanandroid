package name.zzhxufeng.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.screens.*
import name.zzhxufeng.wanandroid.ui.theme.WanandroidTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroid()
        }
    }
}

@Composable
fun WanAndroid(

) {
    WanandroidTheme {
        val allScreens = WanScreen.values().toList().filter { it.type == ScreenType.direct }
        val navController = rememberNavController()
        /*TODO 验证：State会触发重组？*/
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = WanScreen.fromRouteToScreen(
            backstackEntry.value?.destination?.route
        )
        val title = remember { mutableStateOf("首页") }
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        val onBackClicked = { navController.popBackStack() }

        Scaffold(
            scaffoldState =  scaffoldState,
            modifier = Modifier,
            topBar = {
                WanTopBar(
                    title = title.value,
                    onDrawerClick = { scope.launch { scaffoldState.drawerState.open() } },
                    onSearchClick = { navController.navigate(WanScreen.Search.name) }
                )
            },
            bottomBar = {
                WanBottomBar (
                    allScreens = allScreens,
                    onTabSelected = { screen -> navController.navigate(screen.name) },
                    currentScreen = currentScreen
                )
            },
            drawerContent = {
                WanDrawer()
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = WanScreen.Home.name,
                modifier = Modifier.padding(it)
            ) {
                composable(WanScreen.Home.name) {
                    Text(text = WanScreen.Home.name)
                }
                composable(WanScreen.Public.name) {
                    Text(text = WanScreen.Public.name)
                }
                composable(WanScreen.Posts.name) {
                    Text(text = WanScreen.Posts.name)
                }
                composable(WanScreen.Path.name) {
                    Text(text = WanScreen.Path.name)
                }
                composable(WanScreen.Projects.name) {
                    Text(text = WanScreen.Projects.name)
                }
                composable(WanScreen.Search.name) {
                    WanSearch { onBackClicked() }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNav() {
    WanAndroid()
}