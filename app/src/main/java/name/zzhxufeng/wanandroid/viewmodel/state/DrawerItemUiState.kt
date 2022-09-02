package name.zzhxufeng.wanandroid.viewmodel.state

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.data.model.CoinListData
import name.zzhxufeng.wanandroid.data.model.CollectionsData
import name.zzhxufeng.wanandroid.data.model.UserInfoData


data class DrawerItemUiState(
    /*record the item that to be opened.*/
    val drawerItemOpenState: DrawerItem? = null,
    val drawerItems: List<DrawerItem> = DrawerItem.values().toList(),
    /*the item data from network.*/
    val userInfo: UserInfoData? = null,
    val coinListData: CoinListData? = null,
    val collectionsData: CollectionsData? = null
    // share articles
)

enum class DrawerItem(
    @StringRes val desc: Int,
    val icon: ImageVector?,
    val route: String
){
    MAIN(R.string.drawer_main, null, "DRAWER_CONTAINER"),
    COINS(R.string.title_loyalty, Icons.Default.Savings, "COINS"),
    BOOKMARKS(R.string.title_bookmarks, Icons.Default.Favorite, "BOOKMARKS"),
    SHARE(R.string.title_share, Icons.Default.Share, "SHARE"),
    TODO(R.string.title_todo, Icons.Default.Event, "TODO"),
    DARK_MODE(R.string.title_dark_mode, Icons.Default.DarkMode, "DARK_MODE"),
    SETTINGS(R.string.title_settings, Icons.Default.Settings, "SETTINGS"),
    LOGOUT(R.string.title_logout, Icons.Default.Logout, "LOGOUT")
}