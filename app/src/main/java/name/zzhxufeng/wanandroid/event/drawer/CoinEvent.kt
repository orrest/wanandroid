package name.zzhxufeng.wanandroid.event.drawer

import name.zzhxufeng.wanandroid.event.UiEvent

sealed class CoinEvent: UiEvent {
    object LoadMoreRankList: CoinEvent()
}