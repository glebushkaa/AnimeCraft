@file:Suppress("FunctionName", "experimental:function-signature", "LongMethod")

package ua.anime.animecraft.ui.screens.info

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.core.common.capitalize
import ua.anime.animecraft.ui.ad.BannerAd
import ua.anime.animecraft.ui.common.RoundedProgressIndicator
import ua.anime.animecraft.ui.common.buttons.BackButton
import ua.anime.animecraft.ui.common.buttons.DownloadButton
import ua.anime.animecraft.ui.common.buttons.LikeButton
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
import ua.anime.animecraft.ui.extensions.advanceShadow
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun InfoScreen(
    id: Int,
    backClicked: () -> Unit = {},
    infoViewModel: InfoViewModel = hiltViewModel()
) {
    val skin by infoViewModel.skinFlow.collectLifecycleAwareFlowAsState(initialValue = null)

    var downloadClicked by rememberSaveable { mutableStateOf(false) }

    if (downloadClicked &&
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
        infoViewModel.isDownloadDialogDisabled.not()
    ) {
        DownloadSkinDialog(
            dismissRequest = { downloadClicked = false },
            dontShowAgainClick = infoViewModel::disableDownloadDialogOpen
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        SkinInfoCard(
            previewImageUrl = skin?.previewImageUrl ?: "",
            backClicked = backClicked
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                text = skin?.name?.capitalize() ?: "Skin",
                style = AppTheme.typography.headlineSmall.copy(
                    color = AppTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            LikeButton(favorite = skin?.favorite ?: false) {
                infoViewModel.updateFavoriteSkin()
            }
        }
        DownloadButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 16.dp)
                .advanceShadow(
                    borderRadius = 20.dp,
                    blurRadius = 8.dp,
                    color = AppTheme.colors.primary
                )
        ) {
            infoViewModel.saveGameSkinImage()
            downloadClicked = true
        }
        Spacer(modifier = Modifier.height(32.dp))
        BannerAd()
    }
    LaunchedEffect(key1 = false) { infoViewModel.loadSkin(id) }
}

@Composable
fun SkinInfoCard(previewImageUrl: String, backClicked: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.secondary),
        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp, top = 12.dp)
        ) {
            BackButton(backClicked)
            AsyncImage(
                model = previewImageUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(60.dp),
                contentDescription = stringResource(id = R.string.info_skin),
                onState = {
                    isLoading = it !is AsyncImagePainter.State.Success
                }
            )
            if (isLoading) {
                RoundedProgressIndicator(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center),
                    color = AppTheme.colors.primary,
                    strokeWidth = 12.dp
                )
            }
        }
    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    AnimeCraftTheme(darkTheme = true) {
        InfoScreen(0)
    }
}
