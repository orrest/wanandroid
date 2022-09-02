package name.zzhxufeng.wanandroid.data.model

data class BannerModel(
    val desc: String?,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    /*Banner链接的文章*/
    val url: String
)