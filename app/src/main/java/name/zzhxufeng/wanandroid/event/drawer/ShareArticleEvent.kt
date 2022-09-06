package name.zzhxufeng.wanandroid.event.drawer

sealed class ShareArticleEvent {
    class ShareArticle: ShareArticleEvent()
    class TitleChange(val title: String): ShareArticleEvent()
    class LinkChange(val link: String): ShareArticleEvent()
}