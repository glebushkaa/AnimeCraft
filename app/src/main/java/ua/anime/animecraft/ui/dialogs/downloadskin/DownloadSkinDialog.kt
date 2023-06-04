@file:OptIn(ExperimentalFoundationApi::class)
@file:Suppress("LongMethod", "FunctionName")

package ua.anime.animecraft.ui.dialogs.downloadskin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.common.buttons.DontShowAgainButton
import ua.anime.animecraft.ui.dialogs.component.AnimatedScaleDialogContent
import ua.anime.animecraft.ui.dialogs.component.PRE_DISMISS_DELAY
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

@Composable
fun DownloadSkinDialog(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit = { },
    dontShowAgainClick: () -> Unit = { }
) {
    var isDismissed by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { isDismissed = true },
        properties = AppTheme.dialogProperties
    ) {
        AnimatedScaleDialogContent(isDismissed = isDismissed, content = {
            DownloadSkinDialogContent(
                modifier = modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                dontShowAgainClick = {
                    isDismissed = true
                    dontShowAgainClick()
                }
            )
        })
    }

    LaunchedEffect(key1 = isDismissed) {
        if (isDismissed) {
            delay(PRE_DISMISS_DELAY)
            dismissRequest()
        }
    }
}

@Composable
private fun DownloadSkinDialogContent(
    modifier: Modifier = Modifier,
    dontShowAgainClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = AppTheme.colors.surface, shape = AppTheme.shapes.large)
    ) {
        Text(
            modifier = Modifier.padding(
                top = AppTheme.offsets.huge,
                start = AppTheme.offsets.large,
                end = AppTheme.offsets.large
            ),
            color = AppTheme.colors.onBackground,
            text = stringResource(R.string.how_download_skin),
            style = AppTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
        )
        Text(
            modifier = Modifier.padding(
                top = AppTheme.offsets.medium,
                start = AppTheme.offsets.large,
                end = AppTheme.offsets.large
            ),
            color = AppTheme.colors.onBackground,
            style = AppTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            text = stringResource(R.string.download_skin_reason_description)
        )
        HorizontalPager(
            modifier = Modifier.padding(
                start = AppTheme.offsets.huge,
                end = AppTheme.offsets.huge,
                top = AppTheme.offsets.large
            ),
            pageCount = imagesList.size
        ) { page ->
            Image(
                painter = painterResource(id = imagesList[page]),
                modifier = Modifier
                    .height(AppTheme.sizes.dialogs.download.imageHeight)
                    .fillMaxWidth(),
                contentDescription = stringResource(R.string.skin_description_picture)
            )
        }
        DontShowAgainButton(
            modifier = Modifier.padding(
                top = AppTheme.offsets.regular,
                start = AppTheme.offsets.large,
                end = AppTheme.offsets.large,
                bottom = AppTheme.offsets.large
            ),
            onClick = dontShowAgainClick
        )
    }
}

private val imagesList = listOf(
    R.drawable.download_skin_help_first,
    R.drawable.download_skin_help_second,
    R.drawable.download_skin_help_third
)
