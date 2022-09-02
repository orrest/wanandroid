package name.zzhxufeng.wanandroid.viewmodel.event

import androidx.compose.foundation.lazy.LazyListState
import name.zzhxufeng.wanandroid.ui.screens.WanScreen

sealed class MainContainerEvent {
    sealed class HomeEvent: MainContainerEvent() {
        class UpdateListState(val listState: LazyListState): HomeEvent()
        object LoadMoreArticles: HomeEvent()
    }
    class ChangeScreen(val screen: WanScreen): MainContainerEvent()
}