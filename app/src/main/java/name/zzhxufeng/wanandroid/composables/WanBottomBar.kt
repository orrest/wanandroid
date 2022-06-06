package name.zzhxufeng.wanandroid.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.screens.WanScreen

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
            /*
            * 没有触发bottombar的重组
            * */
            Log.d("WanBottomBar", "recomposing...")

            allScreens.forEach { screen ->
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
    val iconColor = remember { mutableStateOf(Color.Black) }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.selectable(
            selected = selected,
            onClick = {
                onScreenSelected()
                Log.d("WanBottomBar#WanTab", "onClick...")
                if (selected) {
                    iconColor.value = Color.Red
                } else {
                    iconColor.value = Color.Black
                }
            },
            role = Role.Tab,
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icon",
            tint = iconColor.value
        )
        Text(text = text)
    }
}

private val TabHeight = 56.dp
