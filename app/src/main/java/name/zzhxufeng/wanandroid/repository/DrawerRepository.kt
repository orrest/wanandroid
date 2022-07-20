package name.zzhxufeng.wanandroid.repository

import retrofit2.http.*

object DrawerRepository {
    private val drawerService = WanAndroidNetwork.retrofit.create(DrawerInterface::class.java)

    suspend fun login(name: String, pwd: String) {
        drawerService.login(name, pwd)
    }

    suspend fun register(
        username: String,
        password: String,
        repassword: String
    ) {
        drawerService.register(username, password, repassword)
    }

    suspend fun logout() {
        drawerService.logout()
    }
}

interface DrawerInterface {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    )

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    )

    @GET("user/logout/json")
    suspend fun logout()
}
