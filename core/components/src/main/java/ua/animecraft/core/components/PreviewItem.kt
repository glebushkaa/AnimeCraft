@file:Suppress("FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.animecraft.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import ua.anime.animecraft.R
import ua.animecraft.core.components.buttons.DownloadButton
import ua.animecraft.core.components.buttons.LikeButton
import ua.anime.animecraft.ui.extensions.advanceShadow
import ua.anime.animecraft.ui.model.Skin
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun PreviewItem(
    modifier: Modifier = Modifier,
    skin: Skin,
    downloadClick: (Int) -> Unit = {},
    likeClick: (Int) -> Unit = {},
    itemClick: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .width(AppTheme.sizes.items.previewItem.width)
            .padding(
                horizontal = AppTheme.offsets.tiny,
                vertical = AppTheme.offsets.small
            ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.offsets.average)
    ) {
        PreviewCard(imageUrl = skin.previewImageUrl) {
            itemClick(skin.id)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f).padding(end = AppTheme.offsets.small),
                text = skin.name.capitalize(),
                style = AppTheme.typography.bodyLargeBold,
                color = AppTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            LikeButton(favorite = skin.favorite) {
                likeClick(skin.id)
            }
        }
        DownloadButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.sizes.items.previewItem.downloadButtonHeight)
                .advanceShadow(
                    borderRadius = AppTheme.sizes.generic.downloadButtonBorderRadius,
                    blurRadius = AppTheme.sizes.generic.downloadButtonBlurRadius,
                    color = AppTheme.colors.primary
                ),
            onClick = { downloadClick(skin.id) }
        )
    }
}

@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onClick: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }

    Card(
        modifier = modifier
            .height(AppTheme.sizes.items.previewItem.height)
            .fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = AppTheme.elevations.medium),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.sizes.items.previewItem.imageHeight),
                contentDescription = stringResource(id = R.string.preview_card),
                onState = {
                    isLoading = it !is AsyncImagePainter.State.Success
                }
            )
            if (isLoading) {
                RoundedProgressIndicator(
                    modifier = Modifier
                        .size(AppTheme.sizes.items.previewItem.progressBarSize)
                        .align(Alignment.Center),
                    color = AppTheme.colors.primary,
                    strokeWidth = AppTheme.strokes.large
                )
            }
        }
    }
}
