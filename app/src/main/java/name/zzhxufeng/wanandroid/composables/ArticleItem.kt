package name.zzhxufeng.wanandroid.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import name.zzhxufeng.wanandroid.repository.ArticleModel

@Composable
fun ArticleItem(
    model: ArticleModel,
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onArticleClick(model.link) }
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            model.tags!!.forEach {
                Text(text = it.name)
            }
            Text(text = model.author!!)
            Text(text = model.niceDate)
        }
        Text(text = model.title, fontSize = 22.sp)
        Text(text = "${model.superChapterName}/${model.chapterName}")
    }
}
