package name.zzhxufeng.wanandroid.data.repository

import name.zzhxufeng.wanandroid.data.model.*
import name.zzhxufeng.wanandroid.data.network.WanAndroidNetwork
import retrofit2.http.*

object DrawerRepository {
    private val drawerService = WanAndroidNetwork.retrofit.create(DrawerInterface::class.java)

    suspend fun login(name: String, pwd: String): WanResponse<AuthenticationData> =
        drawerService.login(name, pwd)

    suspend fun register(
        username: String,
        password: String,
        repassword: String
    ): WanResponse<AuthenticationData> =
        drawerService.register(username, password, repassword)

    suspend fun logout() {
        drawerService.logout()
    }

    suspend fun userInfo(): WanResponse<UserInfoData>
    = drawerService.userInfo()

    suspend fun rankInfo(): WanResponse<RankModel>
    = drawerService.rankInfo()

    suspend fun coinCheckInList(page: Int): WanResponse<CheckInData>
    = drawerService.coinCheckInList(page)

    suspend fun coinRankList(page: Int): WanResponse<RankData>
    = drawerService.rank(page)

    suspend fun bookmarks(page: Int): WanResponse<BookmarkData>
    = drawerService.bookmarks(page)
}

interface DrawerInterface {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): WanResponse<AuthenticationData>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    ): WanResponse<AuthenticationData>

    @GET("user/logout/json")
    suspend fun logout()

    @GET("user/lg/userinfo/json")
    suspend fun userInfo(): WanResponse<UserInfoData>

    @GET("lg/coin/userinfo/json")
    suspend fun rankInfo(): WanResponse<RankModel>

    @GET("/lg/coin/list/{page}/json")
    suspend fun coinCheckInList(
        @Path("page") page: Int
    ): WanResponse<CheckInData>

    @GET("/coin/rank/{page}/json")
    suspend fun rank(
        @Path("page") page: Int
    ): WanResponse<RankData>

    @GET("/lg/collect/list/{page}/json")
    suspend fun bookmarks(
        @Path("page") page: Int
    ): WanResponse<BookmarkData>
}
