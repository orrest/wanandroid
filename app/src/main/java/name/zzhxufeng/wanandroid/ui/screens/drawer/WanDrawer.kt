package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import name.zzhxufeng.wanandroid.ui.composables.WanAlertDialog
import name.zzhxufeng.wanandroid.viewmodel.drawer.DrawerItemViewModel
import name.zzhxufeng.wanandroid.viewmodel.drawer.LoginViewModel
import name.zzhxufeng.wanandroid.viewmodel.event.DrawerEvent
import name.zzhxufeng.wanandroid.viewmodel.state.AuthenticationMode
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItem
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItemUiState

@Composable
fun WanDrawer(
    navHostController: NavHostController,
) {
    val loginViewModel: LoginViewModel = viewModel()
    val loginUiState = loginViewModel.uiState.collectAsState().value

    when (loginUiState.authenticationMode == AuthenticationMode.LOGGED_IN) {
        true ->{
            val viewModel: DrawerItemViewModel = viewModel()
            DrawerContent(
                navHostController = navHostController,
                state = viewModel.uiState.collectAsState().value,
                handleEvent = viewModel::handleEvent,
            )

            viewModel.errorMsg.value?.let {
                WanAlertDialog(
                    error = viewModel.errorMsg.value.toString(),
                    dismissError = { viewModel.dismissError() }
                )
            }
        }

        false -> {
            AuthenticationContent(
                state = loginUiState,
                handleEvent = loginViewModel::handleEvent
            )

            if (loginViewModel.isRefreshing.value) {
                Dialog(onDismissRequest = { /*no need*/ }) {
                    CircularProgressIndicator()
                }
            }

            loginViewModel.errorMsg.value?.let {
                WanAlertDialog(
                    error = loginViewModel.errorMsg.value.toString(),
                    dismissError = { loginViewModel.dismissError() }
                )
            }
        }
    }
}

@Composable
fun DrawerContent(
    state: DrawerItemUiState,
    handleEvent: (event: DrawerEvent) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PersonalInfo(
            name = state.userInfo?.userInfo?.publicName,
            level = state.userInfo?.coinInfo?.level.toString(),
            rank = state.userInfo?.coinInfo?.rank
        )
        Spacer(modifier = Modifier.height(80.dp))
        state.drawerItems.forEach { drawerItem ->
            DrawerItem(
                item = drawerItem,
                onClick = { handleEvent(DrawerEvent.OpenDrawerItem(
                    drawerItem = drawerItem,
                    navBlock = { navHostController.navigate(drawerItem.route) }
                )) },
                navBlock = { navHostController.navigate(drawerItem.route) }
            )
        }
    }

    state.drawerItemOpenState?.let {
        when (it) {
            DrawerItem.COINS -> {

            }
            DrawerItem.BOOKMARKS -> {

            }
            DrawerItem.SHARE -> {

            }
            DrawerItem.TODO -> {

            }
            DrawerItem.DARK_MODE -> {

            }
            DrawerItem.SETTINGS -> {

            }
            DrawerItem.LOGOUT -> {

            }
            else -> {}
        }
    }
}
