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
}

sealed class WanScreen (
    val route: String,
    val type: ScreenType,
    val icon: ImageVector
) {

    /*main*/
    object Home: WanScreen (
        route = "首页",
        type = ScreenType.main,
        icon = Icons.Filled.Home
    )
    object Navi: WanScreen (
        route = "导航",
        type = ScreenType.main,
        icon = Icons.Filled.Person
    )
    object Projects: WanScreen (
        route = "项目",
        type = ScreenType.main,
        icon = Icons.Filled.Face
    )

    /*deep*/
    object Search: WanScreen(
        route = "搜索",
        type = ScreenType.deep,
        icon = Icons.Filled.Search
    )

    object Web: WanScreen(
        route = "Web/{url}",
        type = ScreenType.deep,
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
        fun fromRouteToScreen(route: String?): WanScreen {
            Log.d("fromRouteToScreen", route.toString())
            return when (route) {
                /*direct compose*/
                Home.route -> Home
                Navi.route -> Navi
                Projects.route -> Projects
                /*deep compose*/
                Search.route -> Search
                Web.route -> Web
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
        }

        fun allScreens(): List<WanScreen> {
            return mutableListOf(
                Home,
                Navi,
                Projects,
            )
        }
    }
}

enum class ScreenType {
    main,
    deep
}