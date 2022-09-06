package name.zzhxufeng.wanandroid.event.drawer

sealed class SharingEvent {
    class LoadMore: SharingEvent()
    class AddBookmark(val articleId: Int): SharingEvent()
}