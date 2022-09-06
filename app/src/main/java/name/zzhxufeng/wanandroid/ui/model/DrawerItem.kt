package name.zzhxufeng.wanandroid.ui.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import name.zzhxufeng.wanandroid.R

enum class DrawerItem(
    @StringRes val desc: Int,
    val icon: ImageVector?,
    val route: String,
    val show: Boolean = true,
){
    MAIN(R.string.item_drawer_main, null, "DRAWER_CONTAINER", show = false),
    COINS(R.string.title_loyalty, Icons.Default.Savings, "COINS"),
    BOOKMARKS(R.string.title_bookmarks, Icons.Default.Favorite, "BOOKMARKS"),
    SHARE(R.string.title_share, Icons.Default.Share, "SHARE"),
    TODO(R.string.title_todo, Icons.Default.Event, "TODO", show = false), /*not yet*/
    DARK_MODE(R.string.title_dark_mode, Icons.Default.DarkMode, "DARK_MODE"),
    SETTINGS(R.string.title_settings, Icons.Default.Settings, "SETTINGS"),
    LOGOUT(R.string.title_logout, Icons.Default.Logout, "LOGOUT")
}