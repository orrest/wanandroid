package name.zzhxufeng.wanandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import name.zzhxufeng.wanandroid.ui.composables.WanAlertDialog
import name.zzhxufeng.wanandroid.viewmodel.event.DrawerEvent
import name.zzhxufeng.wanandroid.ui.screens.drawer.AuthenticationContent
import name.zzhxufeng.wanandroid.viewmodel.drawer.DrawerItemViewModel
import name.zzhxufeng.wanandroid.viewmodel.state.AuthenticationMode
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItem
import name.zzhxufeng.wanandroid.viewmodel.drawer.LoginViewModel
import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItemUiState

@Composable
fun WanDrawer() {
    val loginViewModel: LoginViewModel = viewModel()
    val loginUiState = loginViewModel.uiState.collectAsState().value

    when (loginUiState.authenticationMode == AuthenticationMode.LOGGED_IN) {
        true ->{
            val viewModel: DrawerItemViewModel = viewModel()
            DrawerContent(
                state = viewModel.uiState.collectAsState().value,
                handleEvent = viewModel::handleEvent
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
    handleEvent: (event: DrawerEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PersonalInfo(
            name = state.userName,
            level = state.level,
            rank = state.rank
        )
        Spacer(modifier = Modifier.height(80.dp))
        state.drawerList.forEach { drawerItem ->
            DrawerItem(
                item = drawerItem,
                onClick = { handleEvent(DrawerEvent.OpenDrawerItem(drawerItem)) }
            )
        }
    }
}

@Composable
fun PersonalInfo(
    name: String?,
    level: String?,
    rank: String?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(imageVector = Icons.Default.AccountCircle, contentDescription = "avatar", Modifier.size(80.dp))
        Text(text = name?: "name", color = Color.Black)
        Text(text = "等级 ${level ?: ""}  |  排名 ${rank ?: ""}")
    }
}

@Composable
fun DrawerItem(
    item: DrawerItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = stringResource(id = item.desc),
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = stringResource(id = item.desc), modifier = Modifier.padding(10.dp))
    }
}
