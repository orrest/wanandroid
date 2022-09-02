package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import name.zzhxufeng.wanandroid.ui.composables.WanWebView
import name.zzhxufeng.wanandroid.ui.screens.NavControllerNav
import name.zzhxufeng.wanandroid.ui.screens.WanScreen
import name.zzhxufeng.wanandroid.viewmodel.drawer.DrawerItemViewModel
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItem

@Composable
fun DrawerNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavControllerNav.DRAWER_NAV
    ) {
        drawerGraph(navController)
    }
}

fun NavGraphBuilder.drawerGraph(navController: NavHostController) {
    navigation(
        route = NavControllerNav.DRAWER_NAV,
        startDestination = DrawerItem.MAIN.route
    ) {
        composable(route = DrawerItem.MAIN.route) {
            WanDrawer(navController)
        }

        composable(route = DrawerItem.COINS.route) {
            Text(text = "COINS TODO")
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
