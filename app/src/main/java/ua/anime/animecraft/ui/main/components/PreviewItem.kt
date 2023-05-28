@file:Suppress("FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.main.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ua.anime.animecraft.R
import ua.anime.animecraft.core.common.capitalize
import ua.anime.animecraft.ui.common.DownloadButton
import ua.anime.animecraft.ui.common.LikeButton
import ua.anime.animecraft.ui.common.advanceShadow
import ua.anime.animecraft.ui.model.Skin
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun PreviewItem(
    skin: Skin,
    downloadClick: (Int) -> Unit = {},
    likeClick: (Int) -> Unit = {},
    itemClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(dimensionResource(id = R.dimen.preview_card_width))
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        PreviewCard(imageUrl = skin.previewImageUrl) {
            itemClick(skin.id)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
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
                    dimensionResource(id = R.dimen.download_button_height)
                )
                .advanceShadow(
                    borderRadius = 60.dp,
                    blurRadius = 2.dp,
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
    Card(
        modifier = modifier
            .height(dimensionResource(id = R.dimen.preview_card_height))
            .fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
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
                    .height(dimensionResource(id = R.dimen.preview_card_image_width)),
                contentDescription = stringResource(id = R.string.preview_card)
            )
        }
    }
}
