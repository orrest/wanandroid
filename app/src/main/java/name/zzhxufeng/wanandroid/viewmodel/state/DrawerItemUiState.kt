package name.zzhxufeng.wanandroid.viewmodel.state


data class DrawerItemUiState(
    val userName: String? = null,
    val level: String? = null,
    val rank: String? = null,
    val coinCount: Int = 0,
    val userId: Int = 0,
    val collectArticleCount: Int = 0,
    val collectArticleIds: List<Int> = emptyList(),
    val drawerList: List<DrawerItem> = DrawerItem.values().toList(),
)
