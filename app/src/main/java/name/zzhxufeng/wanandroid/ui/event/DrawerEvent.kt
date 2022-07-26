package name.zzhxufeng.wanandroid.ui.event

import name.zzhxufeng.wanandroid.ui.state.DrawerItem

sealed class DrawerEvent {
    class OpenDrawerItem(val drawerItem: DrawerItem): DrawerEvent()
    class EmailChanged(val name: String): DrawerEvent()
    class PasswordChanged(val pwd: String): DrawerEvent()

    object Authenticate: DrawerEvent()
    object ToggleAuthenticationMode: DrawerEvent()
    object ErrorDismissed: DrawerEvent()
}