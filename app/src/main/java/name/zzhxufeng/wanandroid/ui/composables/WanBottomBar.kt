package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    BottomAppBar(
        elevation = 16.dp
    ){
        Row(
            modifier = Modifier.selectableGroup().fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.selectable(
            selected = selected,
            onClick = { onScreenSelected() },
            role = Role.Tab,
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icon",
            tint = if (selected) MaterialTheme.colors.onSecondary else MaterialTheme.colors.primary
        )
        Text(text = text)
    }
}

private val TabHeight = 56.dp
