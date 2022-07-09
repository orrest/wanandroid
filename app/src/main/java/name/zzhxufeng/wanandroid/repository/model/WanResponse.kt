package name.zzhxufeng.wanandroid.repository.model

data class WanResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)