package name.zzhxufeng.wanandroid

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.screens.*
import name.zzhxufeng.wanandroid.ui.theme.WanandroidTheme
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel
import name.zzhxufeng.wanandroid.webview.WanWebView
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


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
    /*
    * 使用viewModel()要求ViewModel不能有依赖项？
    * */
    viewModel: MainViewModel = viewModel()
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

        val onBackClicked = { navController.navigateUp()
            navController.backQueue.forEach {
                Log.d("MainActivity", it.destination.toString())
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier,
            topBar = {
                WanTopBar(
                    title = title.value,
                    onDrawerClick = { scope.launch { scaffoldState.drawerState.open() } },
                    onSearchClick = { navController.navigate(WanScreen.Search.name) }
                )
            },
            bottomBar = {
                WanBottomBar(
                    allScreens = allScreens,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name) {
                            navController.backQueue.forEach {
                                Log.d("MainActivity", it.destination.toString())
                            }
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    currentScreen = currentScreen
                )
            },
            drawerContent = {
                WanDrawer(
                    onDrawerClick = { scope.launch { scaffoldState.drawerState.close() } },
                )
            },
            drawerGesturesEnabled = false
        ) {
            NavHost(
                navController = navController,
                startDestination = WanScreen.Home.name,
                modifier = Modifier.padding(it)
            ) {
                composable(WanScreen.Home.name) {
                    WanHome(
                        viewModel = viewModel,
                        onArticleClicked = { url ->
                            val encode = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                            Log.d("Encoded url", encode)
                            navController.navigate("${WanScreen.Web.name}/${encode}")
                        }
                    )
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
                composable(
                    /*这个url作为一个命名参数是不带$的*/
                    route = "${WanScreen.Web.name}/{url}",
                    arguments = listOf(
                        navArgument("url") {
                            type = NavType.StringType
                        }
                    ),
                ) { backstackEntry ->
                    val webUrl = backstackEntry.arguments?.getString("url")
                    val decode = URLDecoder.decode(webUrl, StandardCharsets.UTF_8.toString())
                    if (decode != null) {
                        WanWebView(url = decode)
                    } else {
                        Text(text = "Error article routing!")
                    }
                }
            }
        }
    }
}
