package name.zzhxufeng.wanandroid.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import name.zzhxufeng.wanandroid.viewmodel.HomeViewModel

@Composable
fun WanNavi(
    viewModel: HomeViewModel,
    onNaviClick: (String) -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.refreshNavi()
    })

    val navi = viewModel.navi
    Column {
        navi.forEach {
            Text(text = it.name)
        }
    }
}