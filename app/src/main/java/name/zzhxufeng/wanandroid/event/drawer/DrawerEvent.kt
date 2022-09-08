package name.zzhxufeng.wanandroid.event.drawer

import name.zzhxufeng.wanandroid.event.UiEvent


sealed class DrawerEvent: UiEvent {
    /*Drawer sign in, sign up.*/
    class AccountNameChanged(val name: String): DrawerEvent()
    class PasswordChanged(val pwd: String): DrawerEvent()
    class RepasswordChanged(val repwd: String): DrawerEvent()

    object Authenticate: DrawerEvent()
    object ToggleAuthenticationMode: DrawerEvent()
    object ErrorDismissed: DrawerEvent()
    object Register: DrawerEvent()
    object ThemeDropdownMenu: DrawerEvent()
}