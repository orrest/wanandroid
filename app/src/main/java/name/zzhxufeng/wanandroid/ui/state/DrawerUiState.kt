package name.zzhxufeng.wanandroid.ui.state

import androidx.annotation.StringRes
import name.zzhxufeng.wanandroid.R

data class DrawerUiState(
    val email: String? = null,
    val password: String? = null,
    val login: Boolean? = null,
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,
    val isLoading: Boolean = false,
    val error: String? = null,
    val drawerList: List<DrawerItem> = DrawerItem.values().toList(),
    val loyaltyPoints: String? = null,
    val level: String? = null,
    val rank: String? = null
)

enum class DrawerItem(@StringRes val desc: Int){
    LOYALTY(R.string.title_loyalty),
    BOOKMARKS(R.string.title_bookmarks),
    SHARE(R.string.title_share),
    TODO(R.string.title_todo),
    DARK_MODE(R.string.title_dark_mode),
    SETTINGS(R.string.title_settings),
    LOGOUT(R.string.title_logout)
}

enum class AuthenticationMode {
    SIGN_IN, SIGN_UP
}