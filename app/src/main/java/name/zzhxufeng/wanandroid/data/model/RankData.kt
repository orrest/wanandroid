package name.zzhxufeng.wanandroid.data.model

// /coin/rank/1/json
data class RankData(
    val curPage: Int,
    val datas: List<RankModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class RankModel(
    val coinCount: Int,
    val rank: String,
    val userId: Int,
    val username: String,
    val level: Int?,
    val nickname: String?
)