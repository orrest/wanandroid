package name.zzhxufeng.wanandroid.data.model

data class WanResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)