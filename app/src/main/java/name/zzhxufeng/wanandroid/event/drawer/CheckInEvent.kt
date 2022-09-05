package name.zzhxufeng.wanandroid.event.drawer

import name.zzhxufeng.wanandroid.event.UiEvent

sealed class CheckInEvent: UiEvent {
    object LoadMore: CheckInEvent()
}