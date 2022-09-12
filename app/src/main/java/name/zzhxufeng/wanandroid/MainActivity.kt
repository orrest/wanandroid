package name.zzhxufeng.wanandroid

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import name.zzhxufeng.wanandroid.ui.screens.*
import name.zzhxufeng.wanandroid.viewmodel.MainContainerViewModel
import name.zzhxufeng.wanandroid.ui.theme.WanAndroidTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavControllerNav.MAIN_NAV
    ) {
        navigation(
            route = NavControllerNav.MAIN_NAV,
            startDestination = WanScreen.Home.route
        ) {
            composable(route = WanScreen.Home.route) {
                val mainViewModel: MainContainerViewModel = viewModel()
                WanMainContainer(
                    uiState = mainViewModel.uiState.collectAsState().value,
                    handleEvent = mainViewModel::handleEvent,
                    onArticleClick = {
                        val intent = Intent(WanApplication.getContext(), WanWebActivity::class.java)
                        intent.putExtra(URL, it)
                        WanApplication.startActivity(intent)
                    },
                    onSearchClick = { navController.navigate(WanScreen.Search.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    } }
                )
            }

            composable(route = WanScreen.Search.route) {
                WanSearch( onBackClicked = {navController.navigateUp()} )
            }
        }
    }
}

