package name.zzhxufeng.wanandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WanSearch(
    onBackClicked: () -> Unit
) {
    Scaffold(
        topBar = { SearchTopBar(onBackClicked) },
    ) {
        val padding = it
        Column {
            Text(text = "熱門搜索")

            Text(text = "搜索歷史")
        }
    }
}

@Composable
private fun SearchTopBar(
    onBackClicked: () -> Unit
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackClicked() }) {
            Image(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }

        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            placeholder = { Text(text = "search ...") },
            singleLine = true
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSearchTopBar() {
    SearchTopBar {

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWanSearch() {
    WanSearch {

    }
}