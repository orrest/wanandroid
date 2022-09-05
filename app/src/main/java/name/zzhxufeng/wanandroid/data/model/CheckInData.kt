package name.zzhxufeng.wanandroid.data.model

// /lg/coin/list/1/json
data class CheckInData(
    val curPage: Int,
    val datas: List<CheckInModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class CheckInModel(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)