@file:Suppress("FunctionName", "experimental:function-signature", "LongMethod")

package ua.animecraft.feature.info

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.animecraft.core.android.Event
import com.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.animecraft.core.android.extensions.permissionGranted
import com.animecraft.core.android.extensions.toast
import ua.animecraft.core.extensions.capitalize
import ua.animecraft.core.components.ad.BannerAd
import ua.animecraft.core.components.CategoryItem
import ua.animecraft.core.components.RoundedProgressIndicator
import ua.animecraft.core.components.buttons.BackButton
import ua.animecraft.core.components.buttons.DownloadButton
import ua.animecraft.core.components.buttons.LikeButton
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
import ua.animecraft.core.components.extensions.advanceShadow
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
    val context = LocalContext.current
    val permissionExceptionMessage = stringResource(R.string.we_cant_load_without_permission)

    val writePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            infoViewModel.saveGameSkinImage()
        } else {
            context.toast(permissionExceptionMessage)
        }
    }

    val skin by infoViewModel.skinFlow.collectAsStateWithLifecycle(null)
    val categoryName by infoViewModel.categoryFlow.collectAsStateWithLifecycle(null)
    var downloadClicked by rememberSaveable { mutableStateOf(false) }

    val downloadFlow by infoViewModel.downloadFlow.collectAsStateWithLifecycle(
        Event(
            null
        )
    )
    var downloadSelected by remember { mutableStateOf(false) }

    val skinDownloadedText = stringResource(id = R.string.skin_downloaded)
    val somethingWrongText = stringResource(R.string.something_went_wrong)

    if (downloadClicked &&
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
        infoViewModel.isDownloadDialogDisabled.not()
    ) {
        DownloadSkinDialog(
            dismissRequest = { downloadClicked = false },
            dontShowAgainClick = infoViewModel::disableDownloadDialogOpen
        )
    }

    LaunchedEffect(key1 = downloadFlow) {
        val value = downloadFlow.getContentIfNotHandled() ?: return@LaunchedEffect
        downloadSelected = value
        val text = if (value) skinDownloadedText else somethingWrongText
        context.toast(text)
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
        Spacer(modifier = Modifier.height(AppTheme.offsets.gigantic))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.offsets.regular)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = skin?.name?.capitalize() ?: "Skin",
                style = AppTheme.typography.headlineSmall.copy(
                    color = AppTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            ua.animecraft.core.components.buttons.LikeButton(favorite = skin?.favorite ?: false) {
                infoViewModel.updateFavoriteSkin()
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.offsets.large))
        AnimatedVisibility(visible = categoryName != null) {
            ua.animecraft.core.components.CategoryItem(
                modifier = Modifier
                    .padding(horizontal = AppTheme.offsets.regular),
                name = categoryName ?: ""
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ua.animecraft.core.components.buttons.DownloadButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.sizes.screens.info.downloadButtonHeight)
                .padding(horizontal = AppTheme.offsets.regular)
                .advanceShadow(
                    borderRadius = AppTheme.sizes.generic.downloadButtonBorderRadius,
                    blurRadius = AppTheme.sizes.generic.downloadButtonBlurRadius,
                    color = AppTheme.colors.primary
                ),
            onClick = {
                if (context.permissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    Build.VERSION.SDK_INT > Build.VERSION_CODES.Q
                ) {
                    infoViewModel.saveGameSkinImage()
                } else {
                    writePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
        ua.animecraft.core.components.ad.BannerAd()
    }
    LaunchedEffect(key1 = false) {
        infoViewModel.loadSkin(id)
    }
}

@Composable
fun SkinInfoCard(previewImageUrl: String, backClicked: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.sizes.screens.info.infoCardHeight),
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
            ua.animecraft.core.components.buttons.BackButton(backClicked)
            AsyncImage(
                model = previewImageUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.offsets.superGigantic),
                contentDescription = stringResource(id = R.string.info_skin),
                onState = {
                    isLoading = it !is AsyncImagePainter.State.Success
                }
            )
            if (isLoading) {
                ua.animecraft.core.components.RoundedProgressIndicator(
                    modifier = Modifier
                        .size(AppTheme.sizes.screens.info.progressBarSize)
                        .align(Alignment.Center),
                    color = AppTheme.colors.primary,
                    strokeWidth = AppTheme.strokes.huge
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
