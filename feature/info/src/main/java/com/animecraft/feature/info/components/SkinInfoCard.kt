@file:Suppress("FunctionName")

package com.animecraft.feature.info.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.anime.animecraft.core.components.RoundedProgressIndicator
import com.anime.animecraft.core.components.buttons.BackButton
import com.anime.animecraft.core.theme.theme.AppTheme
import ua.anime.animecraft.feature.info.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

@Composable
fun SkinInfoCard(
    previewImageUrl: String,
    loading: Boolean = false,
    imageLoadingState: (AsyncImagePainter.State) -> Unit = {},
    onBackClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.info_card_height)
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppTheme.elevations.medium),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface),
        shape = AppTheme.shapes.customShapes.infoCardShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = AppTheme.offsets.tiny,
                    top = AppTheme.offsets.medium
                )
        ) {
            BackButton(onBackClicked)

            this@Card.AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = loading
            ) {
                RoundedProgressIndicator(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.info_progress_bar_size)),
                    color = AppTheme.colors.primary,
                    strokeWidth = AppTheme.strokes.huge
                )
            }

            AsyncImage(
                model = previewImageUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.offsets.superGigantic),
                contentDescription = stringResource(id = R.string.info_skin),
                onState = imageLoadingState
            )
        }
    }
}
