package name.zzhxufeng.wanandroid.state

import name.zzhxufeng.wanandroid.data.model.UserInfoData
import name.zzhxufeng.wanandroid.ui.model.DrawerItem

data class DrawerUiState(
    val loginUiState: LoginUiState = LoginUiState(),
    val drawerItems: List<DrawerItem> = DrawerItem.values().toList(),
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,
    val userInfo: UserInfoData? = null,
)

data class LoginUiState(
    val inputName: String = "",
    val password: String = "",
    val repassword: String = "",
)

enum class AuthenticationMode {
    SIGN_IN, SIGN_UP, LOGGED_IN
}