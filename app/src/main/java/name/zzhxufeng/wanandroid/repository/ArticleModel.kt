package name.zzhxufeng.wanandroid.repository

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
data class ArticleData(
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