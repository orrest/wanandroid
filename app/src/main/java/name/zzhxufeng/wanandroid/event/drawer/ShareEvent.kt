package name.zzhxufeng.wanandroid.event.drawer

sealed class ShareEvent {
    class LoadMore: ShareEvent()
    class AddBookmark(val articleId: Int): ShareEvent()
}