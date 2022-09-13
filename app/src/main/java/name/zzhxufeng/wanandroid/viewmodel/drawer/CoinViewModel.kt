package name.zzhxufeng.wanandroid.viewmodel.drawer

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.event.drawer.CoinEvent
import name.zzhxufeng.wanandroid.state.drawer.CoinUiState
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class CoinViewModel: BaseViewModel() {

    val uiState = MutableStateFlow(CoinUiState())

    init {
        refreshRankInfo()
        refreshRankList()
    }

    fun handleEvent(event: CoinEvent) {
        when (event) {
            CoinEvent.LoadMoreRankList -> {
                loadMoreRankList()
            }
        }
    }

    private fun refreshRankInfo() = launchDataLoad {
        val response = DrawerRepository.rankInfo()
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update {
                it.copy(
                    myRank = response.data
                )
            }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun refreshRankList() = launchDataLoad {
        val response = DrawerRepository.coinRankList(START_PAGE)
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update {
                it.copy(
                    rankUiState = it.rankUiState.copy(
                        listState = LazyListState(),
                        ranks = response.data.datas,
                        maxPage = response.data.pageCount,
                        nextPage = nextPage(
                            START_PAGE,
                            response.data.curPage,
                            response.data.pageCount
                        ),
                    )
                )
            }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun loadMoreRankList() = launchDataLoad {
        val nextPage = uiState.value.rankUiState.nextPage
        if (nextPage != null) {
            val response = DrawerRepository.coinRankList(nextPage)
            if (response.errorCode == WAN_SUCCESS_CODE) {
                uiState.update {
                    it.copy(
                        rankUiState = it.rankUiState.copy(
                            ranks = it.rankUiState.ranks.toMutableList().apply {
                                addAll(response.data.datas)
                            }.toList(),
                            maxPage = response.data.pageCount,
                            nextPage = nextPage(
                                START_PAGE,
                                response.data.curPage,
                                response.data.pageCount
                            ),
                        )
                    )
                }
            } else {
                errorMsg.value = response.errorMsg
            }
        } else {
            errorMsg.value = "没有更多数据"
        }
    }
}