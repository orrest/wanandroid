package name.zzhxufeng.wanandroid.state

import androidx.compose.foundation.lazy.LazyListState
import name.zzhxufeng.wanandroid.data.model.CheckInModel
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API

data class CheckInUiState(
    val listState: LazyListState = LazyListState(),
    val checkInRecords: List<CheckInModel> = emptyList(),
    val nextPage: Int? = START_PAGE_OLD_API,
    val maxPage: Int = Int.MAX_VALUE,
)