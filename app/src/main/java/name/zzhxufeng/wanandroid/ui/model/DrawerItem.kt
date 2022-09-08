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
    THEME(R.string.title_theme, Icons.Default.Palette, "THEME"),
    CLEAR_CACHE(R.string.title_clear_cache, Icons.Default.Settings, "CLEAR_CACHE"),
    LOGOUT(R.string.title_logout, Icons.Default.Logout, "LOGOUT")
}