package social.androiddev.timeline

//import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import social.androiddev.common.composables.UserAvatar
import social.androiddev.common.theme.MastodonTheme

/**
 * Stateful
 */
@Composable
fun TimelineCard(
    state: FeedItemState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    TimelineCard(
        userAvatarUrl = state.userAvatarUrl,
        date = state.date,
        username = state.username,
        userAddress = state.acctAddress,
        toot = state.message,
        videoUrl = state.videoUrl,
        images = state.images,
        contentPadding = contentPadding,
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor
    )
}

/**
 * Stateless
 */
@Composable
fun TimelineCard(
    userAvatarUrl: String,
    date: String,
    username: String,
    userAddress: String,
    toot: String?,
    videoUrl: String?,
    images: List<String>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    Card(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserAvatar(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
                url = userAvatarUrl
            )

            HorizontalSpacer()

            TootContent(
                modifier = Modifier.weight(1f).wrapContentHeight(),
                username = username,
                userAddress = userAddress,
                message = toot,
                date = date,
                images = images,
                videoUrl = videoUrl
            )
        }
    }
}

data class FeedItemState(
    val id: String,
    val userAvatarUrl: String,
    val date: String,
    val username: String,
    val acctAddress: String,
    val message: String?,
    val videoUrl: String?,
    val images: List<String>,
)

val dummyFeedItem = FeedItemState(
    id = "1",
    userAvatarUrl = "https://media.mastodon.cloud/accounts/avatars/000/018/251/original/e78973b0b821c7e3.jpg",
    date = "1d",
    username = "Benjamin Stürmer",
    acctAddress = "@bino@mastodon.cloud",
    message = "\uD83D\uDC4BHello #AndroidDev",
    videoUrl = null,
    images = emptyList(),
)

//@Preview
@Composable
private fun PreviewFeedCardLight() {
    MastodonTheme(useDarkTheme = false) {
        Box(Modifier.padding(12.dp)) {
            TimelineCard(
                state = dummyFeedItem,
                modifier = Modifier.wrapContentSize(),
            )
        }
    }
}

//@Preview
@Composable
private fun PreviewFeedCardDark() {
    MastodonTheme(useDarkTheme = true) {
        Box(Modifier.padding(12.dp)) {
            TimelineCard(
                state = dummyFeedItem,
                modifier = Modifier.wrapContentSize(),
            )
        }
    }
}