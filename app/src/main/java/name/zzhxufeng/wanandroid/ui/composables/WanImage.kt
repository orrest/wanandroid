package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@Composable
fun UriImage(
    uri: Any?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        val painter = rememberAsyncImagePainter(
            model = uri
        )
        Image(
            painter = painter,
            contentDescription = null,
            /*FillBounds in the column.*/
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )
    }
}