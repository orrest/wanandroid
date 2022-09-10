package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.utils.ITEM_PADDING

@Composable
fun WanCard(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = ITEM_PADDING.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        content()
    }
}