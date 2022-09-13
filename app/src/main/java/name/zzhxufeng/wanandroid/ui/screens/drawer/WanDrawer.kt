package name.zzhxufeng.wanandroid.ui.screens.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
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
import name.zzhxufeng.wanandroid.utils.SCREEN_PADDING

@Composable
fun WanDrawer(
    uiState: DrawerUiState,
    handleEvent: (DrawerEvent) -> Unit,
    onDrawerItemNavigate: (DrawerItem) -> Unit
) {

    Scaffold {
        val padding = it
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SCREEN_PADDING.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (uiState.authenticationMode == AuthenticationMode.LOGGED_IN) {
                true ->{
                    DrawerContent(
                        userInfo = uiState.userInfo,
                        themeDropdownExpanded = uiState.themeDropdownExpanded,
                        themeDropdownExpandedEvent = { handleEvent(DrawerEvent.ThemeDropdownMenu) },
                        onNavigate = onDrawerItemNavigate,
                        logout = { handleEvent(DrawerEvent.Logout) }
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
    }
}

@Composable
fun DrawerContent(
    userInfo: UserInfoData?,
    themeDropdownExpanded: Boolean,
    themeDropdownExpandedEvent: () -> Unit,
    onNavigate: (DrawerItem) -> Unit,
    logout: () -> Unit,
) {
    Spacer(modifier = Modifier.height(40.dp))

    NameLevelRank(
        name = userInfo?.userInfo?.publicName,
        level = userInfo?.rankModel?.level,
        rank = userInfo?.rankModel?.rank
    )

    Spacer(modifier = Modifier.height(40.dp))

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
        item = DrawerItem.LOGOUT,
        onClick = { logout() }
    )
}
