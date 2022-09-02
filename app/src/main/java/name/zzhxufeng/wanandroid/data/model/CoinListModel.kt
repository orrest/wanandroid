package name.zzhxufeng.wanandroid.data.model


/*WanResponse<CoinListData>*/
data class CoinListData(
    val curPage: Int,
    val datas: List<CoinListModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class CoinListModel(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)