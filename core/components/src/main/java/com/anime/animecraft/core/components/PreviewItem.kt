@file:Suppress("FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.anime.animecraft.core.components

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.anime.animecraft.core.components.buttons.DownloadButton
import com.anime.animecraft.core.components.buttons.LikeButton
import com.anime.animecraft.core.components.extensions.advanceShadow
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

// TODO REMOVE CAPITALIZE

@Composable
fun PreviewItem(
    modifier: Modifier = Modifier,
    skin: Skin,
    downloadClick: (Int) -> Unit = {},
    likeClick: (Int) -> Unit = {},
    itemClick: (Int) -> Unit = {}
) {
    val downloadBorderRadius = dimensionResource(
        id = com.anime.animecraft.core.common.android.R.dimen.download_button_border_radius
    )
    val downloadBorderBlurRadius = dimensionResource(
        id = com.anime.animecraft.core.common.android.R.dimen.download_button_blur_radius
    )

    Column(
        modifier = modifier
            .wrapContentHeight()
            .width(
                dimensionResource(id = R.dimen.preview_item_width)
            )
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
                modifier = Modifier
                    .weight(1f)
                    .padding(end = AppTheme.offsets.small),
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
                .height(
                    dimensionResource(id = R.dimen.preview_download_height)
                )
                .advanceShadow(
                    borderRadius = downloadBorderRadius,
                    blurRadius = downloadBorderBlurRadius,
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
            .height(
                dimensionResource(id = R.dimen.preview_item_height)
            )
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
                    .height(
                        dimensionResource(id = R.dimen.preview_image_height)
                    ),
                contentDescription = stringResource(id = R.string.preview_card),
                onState = {
                    isLoading = it !is AsyncImagePainter.State.Success
                }
            )
            if (isLoading) {
                RoundedProgressIndicator(
                    modifier = Modifier
                        .size(
                            dimensionResource(id = R.dimen.preview_progress_size)
                        )
                        .align(Alignment.Center),
                    color = AppTheme.colors.primary,
                    strokeWidth = AppTheme.strokes.large
                )
            }
        }
    }
}
