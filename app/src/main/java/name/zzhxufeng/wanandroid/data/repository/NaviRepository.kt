package name.zzhxufeng.wanandroid.data.repository

import name.zzhxufeng.wanandroid.data.model.NaviData
import name.zzhxufeng.wanandroid.data.model.WanResponse
import name.zzhxufeng.wanandroid.data.network.WanAndroidNetwork
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
