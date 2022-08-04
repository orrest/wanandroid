package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import name.zzhxufeng.wanandroid.ui.screens.WanScreen

@Composable
fun WanTopBar(
    currentScreen: WanScreen,
    onDrawerClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = { onDrawerClick() }) {
            Image(imageVector = Icons.Filled.Menu, contentDescription = "drawer")
        }
        Text(text = currentScreen.route)
        IconButton(onClick = { onSearchClick() }) {
            Image(imageVector = Icons.Filled.Search, contentDescription = "search")
        }
    }
}