package name.zzhxufeng.wanandroid.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.ui.screens.WanScreen

@Composable
fun WanBottomBar(
    allScreens: List<WanScreen>,
    onScreenSelected: (WanScreen) -> Unit,
    currentScreen: WanScreen
) {
    Surface(
        modifier = Modifier
            .height(TabHeight)
            .fillMaxWidth(),
        elevation = 10.dp
    ) {
        /*Make the map: <WanScreen, WanTab>*/
        Row(
            modifier = Modifier.selectableGroup(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            allScreens.forEach { screen ->
                /*
                * 要么通过遍历的方式，要么每个Tab单独写。
                * */
                if (screen == currentScreen) {
                    Log.d("WanBottomBar#screen#WanTab", "currentScreen ${screen.route}")
                }
                Log.d("WanBottomBar#screen#WanTab", "${screen.route} recomposing...")

                WanTab(
                    text = screen.route,
                    icon = screen.icon,
                    selected = currentScreen == screen,
                    onScreenSelected = { onScreenSelected(screen) }
                )
            }
        }
    }
}

@Composable
fun WanTab(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onScreenSelected: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.selectable(
            selected = selected,
            onClick = {
                Log.d("WanBottomBar#WanTab", "onClick...")
                onScreenSelected()
            },
            role = Role.Tab,
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icon",
            tint = if (selected) Color.Red else Color.Black
        )
        Text(text = text)
    }
}

private val TabHeight = 56.dp
