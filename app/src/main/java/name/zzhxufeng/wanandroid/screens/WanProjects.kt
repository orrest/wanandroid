package name.zzhxufeng.wanandroid.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.repository.ProjectNameModel
import name.zzhxufeng.wanandroid.viewmodels.MainViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WanProject(
    viewModel: MainViewModel,
    onArticleClick: (String) -> Unit
) {
    val pagerState = rememberPagerState()

}
