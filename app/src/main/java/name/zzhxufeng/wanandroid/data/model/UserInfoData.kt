package name.zzhxufeng.wanandroid.data.model

import com.google.gson.annotations.SerializedName


/*WanResponse<UserInfoData>*/
data class UserInfoData(
    @SerializedName("coinInfo")
    val rankModel: RankModel,
    val collectArticleInfo: CollectArticleInfo,
    val userInfo: UserInfo
)

data class CollectArticleInfo(
    val count: Int
)

data class UserInfo(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)