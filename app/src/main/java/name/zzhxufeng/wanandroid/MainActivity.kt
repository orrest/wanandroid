package name.zzhxufeng.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import name.zzhxufeng.wanandroid.screens.*
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel
import name.zzhxufeng.wanandroid.composables.WanWebView


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation(
    mainViewModel:MainViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WanNavigation.MAIN_NAV
    ) {
        homeGraph(navController, mainViewModel)
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController, viewModel: MainViewModel) {
    navigation(
        route = WanNavigation.MAIN_NAV,
        startDestination = WanScreen.Home.route
    ) {
        composable(route = WanScreen.Home.route) {
            WanMainContainer(
                viewModel = viewModel,
                navController = navController,
                onArticleClick = { navController.navigate(WanScreen.Web.createRoute(it)) }
            )
        }

        composable(route = WanScreen.Search.route) {
            WanSearch( onBackClicked = {navController.navigateUp()} )
        }

        composable(
            route = WanScreen.Web.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val encodedUrl = navBackStackEntry.arguments?.getString("url")
            if (encodedUrl != null) {
                WanWebView(url = WanScreen.Web.parseUrl(encodedUrl))
            } else {
                Text(text = "不该发生的情况")
            }
        }
    }
}
