package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.data.model.UserInfoData
import name.zzhxufeng.wanandroid.event.drawer.DrawerEvent
import name.zzhxufeng.wanandroid.state.drawer.AuthenticationMode
import name.zzhxufeng.wanandroid.state.drawer.DrawerUiState
import name.zzhxufeng.wanandroid.ui.model.DrawerItem
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.DrawerItem
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.DrawerItemTheme
import name.zzhxufeng.wanandroid.ui.screens.drawer.items.NameLevelRank

@Composable
fun WanDrawer(
    uiState: DrawerUiState,
    handleEvent: (DrawerEvent) -> Unit,
    onDrawerItemNavigate: (DrawerItem) -> Unit
) {

    when (uiState.authenticationMode == AuthenticationMode.LOGGED_IN) {
        true ->{
            DrawerContent(
                userInfo = uiState.userInfo,
                themeDropdownExpanded = uiState.themeDropdownExpanded,
                themeDropdownExpandedEvent = { handleEvent(DrawerEvent.ThemeDropdownMenu) },
                onNavigate = onDrawerItemNavigate
            )
        }

        false -> {
            AuthenticationContent(
                authenticationMode = uiState.authenticationMode,
                state = uiState.loginUiState,
                handleEvent = handleEvent
            )
        }
    }
}

@Composable
fun DrawerContent(
    userInfo: UserInfoData?,
    themeDropdownExpanded: Boolean,
    themeDropdownExpandedEvent: () -> Unit,
    onNavigate: (DrawerItem) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "avatar", Modifier.size(80.dp)
        )
        NameLevelRank(
            name = userInfo?.userInfo?.publicName,
            level = userInfo?.rankModel?.level,
            rank = userInfo?.rankModel?.rank
        )
        Spacer(modifier = Modifier.height(80.dp))

        DrawerItem(
            item = DrawerItem.COINS,
            onClick = { onNavigate(DrawerItem.COINS) }
        )

        DrawerItem(
            item = DrawerItem.BOOKMARKS,
            onClick = { onNavigate(DrawerItem.BOOKMARKS) }
        )

        DrawerItem(
            item = DrawerItem.SHARE,
            onClick = { onNavigate(DrawerItem.SHARE) }
        )

        DrawerItemTheme(
            item = DrawerItem.THEME,
            dropdownExpanded = themeDropdownExpanded,
            dropdownExpandedEvent = themeDropdownExpandedEvent
        )

        DrawerItem(
            item = DrawerItem.CLEAR_CACHE,
            onClick = { /*TODO*/ }
        )

        DrawerItem(
            item = DrawerItem.LOGOUT,
            onClick = { /*TODO*/ }
        )
    }
}
