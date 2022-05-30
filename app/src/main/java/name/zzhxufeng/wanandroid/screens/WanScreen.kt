package name.zzhxufeng.wanandroid.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class WanScreen (
    val type: ScreenType,
    val icon: ImageVector
) {

    Home (
        type = ScreenType.direct,
        icon = Icons.Filled.Home
    ),
    Public (
        type = ScreenType.direct,
        icon = Icons.Filled.Place
    ),
    Posts (
        type = ScreenType.direct,
        icon = Icons.Filled.Notifications
    ),
    Path (
        type = ScreenType.direct,
        icon = Icons.Filled.Person
    ),
    Projects (
        type = ScreenType.direct,
        icon = Icons.Filled.Face
    ),

    Search(
        type = ScreenType.deep,
        icon = Icons.Filled.Search
    ),

    Web(
        type = ScreenType.deep,
        icon = Icons.Filled.Web
    );

    /*静态方法*/
    companion object {
        fun fromRouteToScreen(route: String?): WanScreen =
            when (route?.substringBefore("/")) {
                /*direct compose*/
                Home.name -> Home
                Public.name -> Public
                Posts.name -> Posts
                Path.name -> Path
                Projects.name -> Projects
                /*deep compose*/
                Search.name -> Search
                Web.name -> Web
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}

enum class ScreenType {
    direct,
    deep
}