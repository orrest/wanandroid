package name.zzhxufeng.wanandroid.ui.screens.drawer.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.ui.model.DrawerItem


@Composable
fun DrawerItem(
    item: DrawerItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
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
    }
}
