package name.zzhxufeng.wanandroid.viewmodel.event

import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItem

sealed class DrawerEvent {
    class OpenDrawerItem(val drawerItem: DrawerItem): DrawerEvent()
    class AccountNameChanged(val name: String): DrawerEvent()
    class PasswordChanged(val pwd: String): DrawerEvent()
    class RepasswordChanged(val repwd: String): DrawerEvent()

    object Authenticate: DrawerEvent()
    object ToggleAuthenticationMode: DrawerEvent()
    object ErrorDismissed: DrawerEvent()
    object Register: DrawerEvent()
}