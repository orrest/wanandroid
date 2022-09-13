package name.zzhxufeng.wanandroid.event


sealed class SearchEvent{
    object LoadMore: SearchEvent()
    class Search(val text: String): SearchEvent()
    class TextChange(val text: String): SearchEvent()
}
