package name.zzhxufeng.wanandroid.ui.screens.drawer.items

import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.ui.composables.WanCard
import name.zzhxufeng.wanandroid.ui.model.DrawerItem
import name.zzhxufeng.wanandroid.ui.model.Theme
import name.zzhxufeng.wanandroid.ui.model.ThemeColor

@Composable
fun DrawerItemTheme(
    item: DrawerItem,
    dropdownExpanded: Boolean,
    dropdownExpandedEvent: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WanCard(onClick = { dropdownExpandedEvent() }) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = stringResource(id = item.desc),
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(text = stringResource(id = item.desc), modifier = Modifier.padding(10.dp))

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpandedEvent() }
            ) {
                ThemeColor.values().forEach { selectedTheme ->
                    DropdownMenuItem(
                        onClick = {
                            Theme.changeTheme(selectedTheme)
                            dropdownExpandedEvent()
                        }
                    ) {
                        ThemeItem(selectedTheme)
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeItem(
    item: ThemeColor
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = item.label))
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Circle,
            contentDescription = null,
            tint = item.color
        )
    }
}