@file:OptIn(ExperimentalFoundationApi::class)
@file:Suppress("LongMethod", "FunctionName")

package ua.anime.animecraft.ui.dialogs.downloadskin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

private val imagesList = listOf(
    R.drawable.download_skin_help_first,
    R.drawable.download_skin_help_second,
    R.drawable.download_skin_help_third
)

@Composable
fun DownloadSkinDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit = { },
    dontShowAgainClick: () -> Unit = { }
) {
    Dialog(onDismissRequest = dismissRequest) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = AppTheme.colors.background, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.offset_huge),
                    start = dimensionResource(id = R.dimen.offset_large),
                    end = dimensionResource(id = R.dimen.offset_large)
                ),
                color = AppTheme.colors.onBackground,
                text = stringResource(R.string.how_download_skin),
                style = AppTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
            )
            Text(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.offset_medium),
                    start = dimensionResource(id = R.dimen.offset_large),
                    end = dimensionResource(id = R.dimen.offset_large)
                ),
                color = AppTheme.colors.onBackground,
                style = AppTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                text = stringResource(R.string.download_skin_reason_description)
            )
            HorizontalPager(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.offset_huge),
                    end = dimensionResource(id = R.dimen.offset_huge),
                    top = dimensionResource(id = R.dimen.offset_large)
                ),
                pageCount = imagesList.size
            ) { page ->
                Image(
                    painter = painterResource(id = imagesList[page]),
                    modifier = Modifier
                        .height(260.dp)
                        .fillMaxWidth(),
                    contentDescription = stringResource(R.string.skin_description_picture)
                )
            }
            TextButton(
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.offset_medium),
                    horizontal = dimensionResource(id = R.dimen.offset_large)
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = dontShowAgainClick
            ) {
                Text(
                    text = stringResource(R.string.dont_show_this_again),
                    color = AppTheme.colors.onBackground.copy(alpha = 0.6f),
                    style = AppTheme.typography.bodySmall.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun DownloadSkinDialogPreview() {
    AnimeCraftTheme(darkTheme = true) {
        DownloadSkinDialog()
    }
}
