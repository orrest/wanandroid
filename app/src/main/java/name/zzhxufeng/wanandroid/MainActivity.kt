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
import name.zzhxufeng.wanandroid.ui.screens.*
import name.zzhxufeng.wanandroid.viewmodel.HomeViewModel
import name.zzhxufeng.wanandroid.ui.composables.WanWebView


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
    homeViewModel:HomeViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavControllerNav.MAIN_NAV
    ) {
        homeGraph(navController, homeViewModel)
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController, viewModel: HomeViewModel) {
    navigation(
        route = NavControllerNav.MAIN_NAV,
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
