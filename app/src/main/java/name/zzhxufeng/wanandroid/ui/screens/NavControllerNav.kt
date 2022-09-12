package name.zzhxufeng.wanandroid.ui.screens

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavControllerNav{
    const val MAIN_NAV = "main"
    const val PUBLIC_NAV = "public"
    const val DRAWER_NAV = "drawer"
}

sealed class WanScreen(
    val route: String,
    val icon: ImageVector
) {

    object Home: WanScreen (
        route = "首页",
        icon = Icons.Filled.Home
    )
    object Navi: WanScreen (
        route = "导航",
        icon = Icons.Filled.Person
    )
    object Projects: WanScreen (
        route = "项目",
        icon = Icons.Filled.Face
    )

    object Search: WanScreen(
        route = "搜索",
        icon = Icons.Filled.Search
    )

    object Web: WanScreen(
        route = "Web/{url}",
        icon = Icons.Filled.Web
    ){
        fun createRoute(url: String): String {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            Log.d("Web", "Web/$encodedUrl")
            return "Web/${encodedUrl}"
        }
        fun parseUrl(encodedUrl: String): String {
            Log.d("Web", URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString()))
            return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
        }
    }

    /*静态方法*/
    companion object {
        fun allScreens(): List<WanScreen> {
            return mutableListOf(
                Home,
                Navi,
                Projects,
            )
        }
    }
}
