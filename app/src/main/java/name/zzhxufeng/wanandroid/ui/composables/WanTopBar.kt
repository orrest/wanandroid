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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import name.zzhxufeng.wanandroid.R

@Composable
fun WanTopBar(
    desc: String?,
    leftIcon: ImageVector?,
    rightIcon: ImageVector?,
    onLeftIconClick: () -> Unit = {},
    onRightIconClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        leftIcon?.let {
            IconButton(onClick = { onLeftIconClick() }) {
                Image(imageVector = leftIcon, contentDescription = stringResource(R.string.desc_topbar_left_icon))
            }
        }

        desc?.let { Text(text = desc) }

        rightIcon?.let {
            IconButton(onClick = { onRightIconClick() }) {
                Image(imageVector = rightIcon, contentDescription = stringResource(R.string.desc_topbar_right_icon))
            }
        }
    }
}

