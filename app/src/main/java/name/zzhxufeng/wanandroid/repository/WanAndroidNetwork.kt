package name.zzhxufeng.wanandroid.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import name.zzhxufeng.wanandroid.repository.model.ArticleModel
import name.zzhxufeng.wanandroid.repository.model.WanResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WanAndroidNetwork {
    private const val WAN_ANDROID = "https://www.wanandroid.com/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WAN_ANDROID)
            .build()

    private val articleService = retrofit.create(ArticleInterface::class.java)
    private val bannerService = retrofit.create(BannerInterface::class.java)
    private val postsService = retrofit.create(PostsInterface::class.java)
    private val projectService = retrofit.create(ProjectsInterface::class.java)
    private val naviService = retrofit.create(NaviInterface::class.java)

    suspend fun fetchArticles(id: Int): List<ArticleModel>
    = withContext(Dispatchers.Default){
        /*有可能会在这里进行Json解析，所以放到不同的线程上*/
        delay(1000)
        articleService.fetchArticles(id).data.datas
    }

    suspend fun fetchBanner(): List<BannerModel>
    = withContext(Dispatchers.Default) {
        delay(500)
        bannerService.fetchBanner().data
    }

    suspend fun fetchPosts(id: Int): List<ArticleModel>
    = withContext(Dispatchers.Default) {
        delay(500)
        postsService.fetchPosts(id).data.datas
    }

    suspend fun fetchProjectColumnName(): List<ProjectNameModel>
    = withContext(Dispatchers.Default) {
        delay(500)
        projectService.fetchProjectColumnName().data
    }

    suspend fun fetchProjectByColumn(page: Int, cid: Int): List<ProjectModel>
    = withContext(Dispatchers.Default) {
        delay(500)
        projectService.fetchProjects(page, cid).data.datas
    }

    suspend fun fetchNavi(): List<NaviData>
    = withContext(Dispatchers.Default) {
        delay(500)
        naviService.fetchNavi().data
    }
}
