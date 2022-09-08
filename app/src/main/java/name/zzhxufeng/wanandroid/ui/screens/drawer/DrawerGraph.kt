package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import name.zzhxufeng.wanandroid.ui.model.DrawerItem
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.Bookmarks
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.coin.CheckInRecords
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.coin.Coins
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.sharing.SharingAdd
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.sharing.Sharings
import name.zzhxufeng.wanandroid.viewmodel.drawer.*

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

private fun navigate(navController: NavHostController, route: String) {
    navController.navigate(route)
}

fun NavGraphBuilder.drawerGraph(navController: NavHostController) {
    navigation(
        route = NavControllerNav.DRAWER_NAV,
        startDestination = DrawerItem.MAIN.route
    ) {
        composable(route = DrawerItem.MAIN.route) {
            val drawerViewModel: DrawerViewModel = viewModel()
            WanDrawer(
                uiState = drawerViewModel.uiState.collectAsState().value,
                handleEvent = drawerViewModel::handleEvent,
                onDrawerItemNavigate = { drawerItem ->
                    when (drawerItem) {
                        DrawerItem.THEME -> { /* no navigation */ }
                        DrawerItem.LOGOUT -> { /* no navigation */ }
                        DrawerItem.MAIN -> { /*This won't be clicked, MAIN not shown.*/ }
                        else -> { navigate(navController, drawerItem.route) }
                    }
                }
            )
        }

        composable(route = DrawerItem.COINS.route) {
            val coinViewModel: CoinViewModel = viewModel()
            Coins(
                uiState = coinViewModel.uiState.collectAsState().value,
                handleEvent = coinViewModel::handleEvent,
                navigateBack = { navController.popBackStack() },
                navigateToCheckInRecord = { navController.navigate(CHECK_IN_RECORDS_ROUTE) },
                navigateToHelp = { navController.navigate(WanScreen.Web.createRoute(
                    "https://www.wanandroid.com/blog/show/2653"
                )) },
            )
        }

        composable(route = DrawerItem.BOOKMARKS.route) {
            val bookmarkViewModel: BookmarkViewModel = viewModel()
            Bookmarks(
                uiState = bookmarkViewModel.uiState.collectAsState().value,
                handleEvent = bookmarkViewModel::handleEvent,
                navigateBack = { navController.popBackStack() },
                navigateToBookmarkPage = { link ->
                    navController.navigate(WanScreen.Web.createRoute(link))
                },
            )
        }

        composable(route = DrawerItem.SHARE.route) {
            val shareViewModel: ShareViewModel = viewModel()
            Sharings(
                uiState = shareViewModel.uiState.collectAsState().value,
                handleEvent = shareViewModel::handleEvent,
                navigateBack = { navController.popBackStack() },
                navigateToPostShare = { navigate(navController, SHARING_ADD) }
            )
        }

        composable(route = DrawerItem.TODO.route) {
            Text(text = "COINS TODO")
        }

        composable(route = DrawerItem.LOGOUT.route) {
            Text(text = "COINS TODO")
        }

        composable(route = CHECK_IN_RECORDS_ROUTE) {
            val checkInViewModel: CheckInRecordViewModel = viewModel()
            CheckInRecords(
                uiState = checkInViewModel.uiState.collectAsState().value,
                handleEvent = checkInViewModel::handleEvent,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = SHARING_ADD) {
            val viewModel: ShareArticleViewModel = viewModel()
            SharingAdd(
                uiState = viewModel.uiState.collectAsState().value,
                handleEvent = viewModel::handleEvent,
                navigateBack = { navController.popBackStack() }
            )
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

private const val CHECK_IN_RECORDS_ROUTE = "check_in_records_route"
private const val SHARING_ADD = "sharing_add"