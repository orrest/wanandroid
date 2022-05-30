package name.zzhxufeng.wanandroid.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.screens.WanScreen

@Composable
fun WanBottomBar(
    allScreens: List<WanScreen>,
    onTabSelected: (WanScreen) -> Unit,
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
                WanTab(
                    text = screen.route,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
fun WanTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.selectable(
            selected = selected,
            onClick = onSelected,
            role = Role.Tab,
        )
    ) {
        Icon(imageVector = icon, contentDescription = "icon")
        Text(text = text)
    }
}

private val TabHeight = 56.dp
