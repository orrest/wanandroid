package name.zzhxufeng.wanandroid.viewmodel.state

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import name.zzhxufeng.wanandroid.R

data class LoginUiState(
    val inputName: String = "",
    val password: String = "",
    val repassword: String = "",
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,

)

enum class DrawerItem(
    @StringRes val desc: Int,
    val icon: ImageVector
){
    LOYALTY(R.string.title_loyalty, Icons.Default.Savings),
    BOOKMARKS(R.string.title_bookmarks, Icons.Default.Favorite),
    SHARE(R.string.title_share, Icons.Default.Share),
    TODO(R.string.title_todo, Icons.Default.Event),
    DARK_MODE(R.string.title_dark_mode, Icons.Default.DarkMode),
    SETTINGS(R.string.title_settings, Icons.Default.Settings),
    LOGOUT(R.string.title_logout, Icons.Default.Logout)
}

enum class AuthenticationMode {
    SIGN_IN, SIGN_UP, LOGGED_IN
}