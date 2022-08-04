package name.zzhxufeng.wanandroid.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import name.zzhxufeng.wanandroid.data.model.AuthenticationModel
import name.zzhxufeng.wanandroid.data.model.CoinInfo
import name.zzhxufeng.wanandroid.data.model.UserInfoModel
import name.zzhxufeng.wanandroid.data.model.WanResponse
import name.zzhxufeng.wanandroid.data.network.WanAndroidNetwork
import retrofit2.http.*

object DrawerRepository {
    private val drawerService = WanAndroidNetwork.retrofit.create(DrawerInterface::class.java)

    suspend fun login(name: String, pwd: String): WanResponse<AuthenticationModel> =
        drawerService.login(name, pwd)

    suspend fun register(
        username: String,
        password: String,
        repassword: String
    ): WanResponse<AuthenticationModel> =
        drawerService.register(username, password, repassword)

    suspend fun logout() {
        drawerService.logout()
    }

    suspend fun userInfo(): WanResponse<UserInfoModel>
    = withContext(Dispatchers.Default) {
        drawerService.userInfo()
    }
}

interface DrawerInterface {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): WanResponse<AuthenticationModel>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    ): WanResponse<AuthenticationModel>

    @GET("user/logout/json")
    suspend fun logout()

    @GET("user/lg/userinfo/json")
    suspend fun userInfo(): WanResponse<UserInfoModel>

    @GET("lg/coin/userinfo/json")
    suspend fun coinInfo(): WanResponse<CoinInfo>
}
