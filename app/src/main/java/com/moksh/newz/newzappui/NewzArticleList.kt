import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moksh.data.model.NewsArticle
import com.moksh.newz.ui.theme.articleTitleStyle
import com.moksh.newz.ui.theme.dateTextStyle
import com.moksh.newz.ui.theme.sourceTextStyle
import com.moksh.newz.util.CustomTabUtil
import com.moksh.newz.util.HeightSpacer
import com.moksh.newz.util.RemoteImage
import com.moksh.newz.util.WidthSpacer

@Composable
fun ArticleRow(article: NewsArticle, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = { onClick() })) {
        Row(
            modifier = Modifier.padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RemoteImage(
                url = article.urlToImage,
                modifier = Modifier.requiredSize(100.dp)
            )
            WidthSpacer(value = 10.dp)
            Column {
                if (!article.source.name.isNullOrEmpty()) {
                    Text(
                        text = article.source.name!!,
                        style = sourceTextStyle.run { copy(color = MaterialTheme.colors.secondary) }
                    )
                    HeightSpacer(value = 4.dp)
                }
                Text(
                    text = article.title,
                    style = articleTitleStyle.copy(color = MaterialTheme.colors.onSurface),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                HeightSpacer(value = 4.dp)
                Text(
                    text = article.publishedAt.substring(0, 10),
                    style = dateTextStyle.copy(color = MaterialTheme.colors.secondary)
                )
            }
        }
        HeightSpacer(value = 10.dp)
        Divider(
            color = MaterialTheme.colors.secondary.copy(
                alpha = 0.2f
            )
        )
    }
}

@Composable
fun NewzArticleList(articles: List<NewsArticle>) {
    val context = LocalContext.current
    val isDark = MaterialTheme.colors.isLight
    LazyColumn {
        items(items = articles) { article ->
            ArticleRow(
                article = article,
                onClick = {
                    CustomTabUtil.launch(context, article.url.toString(), isDark)
                }
            )
        }
    }
}