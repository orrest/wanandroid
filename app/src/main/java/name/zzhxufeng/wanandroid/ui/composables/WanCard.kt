package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.utils.ITEM_PADDING

@Composable
fun WanCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    padding: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit
) {
    Card(
        backgroundColor = backgroundColor,
        modifier = modifier
            .padding(vertical = if (padding) ITEM_PADDING.dp else 0.dp)
            .clickable { onClick() }
    ) {
        content()
    }
}