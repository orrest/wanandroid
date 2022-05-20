package name.zzhxufeng.wanandroid.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class WanScreen (
    val icon: ImageVector
) {

    Home (icon = Icons.Filled.Home),
    Public (icon = Icons.Filled.Place),
    Posts (icon = Icons.Filled.Notifications),
    Path (icon = Icons.Filled.Person),
    Projects (icon = Icons.Filled.Face);

    /*静态方法*/
    companion object {
        fun fromRouteToScreen(route: String?): WanScreen =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                Public.name -> Public
                Posts.name -> Posts
                Path.name -> Path
                Projects.name -> Projects
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}