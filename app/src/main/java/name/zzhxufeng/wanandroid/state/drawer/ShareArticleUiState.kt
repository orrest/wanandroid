package name.zzhxufeng.wanandroid.state.drawer

data class ShareArticleUiState(
    val title: String? = null,
    val link: String? = null,
    val canPost: Boolean = false,
) {
    fun canPost(): Boolean {
        return title != null && link != null
    }
}