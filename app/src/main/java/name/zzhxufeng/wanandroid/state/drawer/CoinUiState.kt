package name.zzhxufeng.wanandroid.state.drawer

import androidx.compose.foundation.lazy.LazyListState
import name.zzhxufeng.wanandroid.data.model.RankModel
import name.zzhxufeng.wanandroid.data.model.UserInfoData
import name.zzhxufeng.wanandroid.data.network.START_PAGE

data class CoinUiState(
    val userInfoData: UserInfoData? = null,
    val myRank: RankModel? = null,
    val helpLink: String = "",
    val rankUiState: RankUiState = RankUiState(),
)

data class RankUiState(
    val listState: LazyListState = LazyListState(),
    val ranks: List<RankModel> = emptyList(),
    val nextPage: Int? = START_PAGE,
    val maxPage: Int = Int.MAX_VALUE,
)
