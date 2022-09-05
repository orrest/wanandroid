package name.zzhxufeng.wanandroid.data.model

//     @GET("/lg/collect/list/{page}/json")
// from 0
data class BookmarkData(
    val curPage: Int,
    val datas: List<BookmarkModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class BookmarkModel(
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val originId: Int,
    val publishTime: Long,
    val title: String,
    val userId: Int,
    val visible: Int,
    val zan: Int
)