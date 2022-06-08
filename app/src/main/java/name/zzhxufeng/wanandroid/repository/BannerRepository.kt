package name.zzhxufeng.wanandroid.repository

import retrofit2.http.GET

object BannerRepository {
    suspend fun refreshBanner(): List<BannerModel> {
        return WanAndroidNetwork.fetchBanner()
    }
}

interface BannerInterface{
    @GET("banner/json")
    suspend fun fetchBanner(): Banner
}

data class Banner(
    val data: List<BannerModel>,
    val errorCode: Int,
    val errorMsg: String,
)

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