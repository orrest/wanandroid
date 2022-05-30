package name.zzhxufeng.wanandroid.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


object WanAndroidNetwork {
    private const val WAN_ANDROID = "https://www.wanandroid.com/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WAN_ANDROID)
            .build()

    private val articleService = retrofit.create(ArticleInterface::class.java)

    suspend fun fetchArticles(id: Int): List<ArticleModel>
    = withContext(Dispatchers.Default){
        /*有可能会在这里进行Json解析，所以放到不同的线程上*/
        delay(1000)
        articleService.fetchArticles(id).data.datas
    }
}

interface ArticleInterface {
    @GET("article/list/{id}/json")
    suspend fun fetchArticles(@Path("id") id: Int): ArticleResponse
}

/*
* 这里有两种方式，
* 1. 返回JsonObject然后手动解析
*   优点：灵活、避免冗余数据
*   缺点：接口如果有变动，那么需要重新进行手动解析；手动，麻烦
*
* 2. 构造像这样的数据类型，自动解析
*   优点：结构化、避免解析出错
*   缺点：接口如果有变动，需要修改数据类型； 数据冗余
* */
data class ArticleResponse(
    val data: DataObject,
    val errorCode: Int,
    val errorMsg: String
)

data class DataObject(
    val curPage: Int,
    val datas: List<ArticleModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class ArticleModel(
    val link: String,
    val niceDate: String,
    val title: String,
    val fresh: Boolean,
    val collect: Boolean,
    val author: String?,
    val superChapterName: String,
    val chapterName: String,
    val shareUser: String?,
    val tags: List<Tags>?
)

data class Tags(
    val name: String,
    val url: String?
)