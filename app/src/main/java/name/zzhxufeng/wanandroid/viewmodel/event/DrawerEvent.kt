package name.zzhxufeng.wanandroid.viewmodel.event

import name.zzhxufeng.wanandroid.viewmodel.state.DrawerItem

sealed class DrawerEvent {
    /*Drawer item.*/
    class OpenDrawerItem(
        val drawerItem: DrawerItem,
        val navBlock: () -> Unit
    ): DrawerEvent()

    /*Drawer sign in, sign up.*/
    class AccountNameChanged(val name: String): DrawerEvent()
    class PasswordChanged(val pwd: String): DrawerEvent()
    class RepasswordChanged(val repwd: String): DrawerEvent()

    object Authenticate: DrawerEvent()
    object ToggleAuthenticationMode: DrawerEvent()
    object ErrorDismissed: DrawerEvent()
    object Register: DrawerEvent()
}