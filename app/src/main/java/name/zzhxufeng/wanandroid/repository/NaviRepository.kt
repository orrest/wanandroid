package name.zzhxufeng.wanandroid.repository

import name.zzhxufeng.wanandroid.repository.model.NaviData
import name.zzhxufeng.wanandroid.repository.model.WanResponse
import retrofit2.http.GET

object NaviRepository {
    private val naviService = WanAndroidNetwork.retrofit.create(NaviInterface::class.java)

    suspend fun refreshNavi(): WanResponse<List<NaviData>> {
        return naviService.fetchNavi()
    }
}

interface NaviInterface {
    @GET("navi/json")
    suspend fun fetchNavi(): WanResponse<List<NaviData>>
}
