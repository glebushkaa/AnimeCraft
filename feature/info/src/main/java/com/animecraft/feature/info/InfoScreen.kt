@file:Suppress("FunctionName", "experimental:function-signature", "LongMethod")

package com.animecraft.feature.info

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import com.anime.animecraft.feature.info.R
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.android.extensions.permissionGranted
import com.anime.animecraft.core.android.extensions.toast
import com.anime.animecraft.core.components.ad.BannerAd
import com.anime.animecraft.core.components.CategoryItem
import com.anime.animecraft.core.components.buttons.DownloadButton
import com.anime.animecraft.core.components.buttons.LikeButton
import com.anime.animecraft.core.components.extensions.advanceShadow
import com.animecraft.animecraft.common.capitalize
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.feature.info.components.SkinInfoCard
import com.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun InfoScreen(
    onBackClicked: () -> Unit = {},
    onDownloadDialogNavigate: () -> Unit = {},
    infoViewModel: InfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionExceptionMessage = stringResource(R.string.we_cant_load_without_permission)
    val skinDownloadedText = stringResource(id = R.string.skin_downloaded)
    val somethingWrongText = stringResource(R.string.something_went_wrong)

    val writePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            infoViewModel.saveGameSkinImage()
            return@rememberLauncherForActivityResult
        }
        context.toast(permissionExceptionMessage)
    }

    val state by infoViewModel.screenState.collectAsStateWithLifecycle(
        InfoScreenState()
    )

    InfoScreenContent(
        skin = state.skin,
        categoryName = state.categoryName,
        categoryVisible = state.categoryVisible,
        loading = state.loading,
        onFavoriteClicked = infoViewModel::updateSkinFavoriteState,
        onBackClicked = onBackClicked,
        skinImageLoadingState = infoViewModel::checkImageLoadingState,
        onDownloadClicked = {
            when {
                context.permissionGranted(WRITE_EXTERNAL_STORAGE) &&
                    SDK_INT <= Build.VERSION_CODES.Q -> {
                    infoViewModel.saveGameSkinImage()
                }

                SDK_INT > Build.VERSION_CODES.Q -> {
                    infoViewModel.showDownloadDialog()
                    infoViewModel.saveGameSkinImage()
                }

                else -> {
                    writePermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.downloadDialogShown) {
        val value = state.downloadDialogShown.getContentIfNotHandled() ?: return@LaunchedEffect
        if (!value) return@LaunchedEffect
        onDownloadDialogNavigate()
    }

    LaunchedEffect(key1 = state.downloadState) {
        val value = state.downloadState.getContentIfNotHandled() ?: return@LaunchedEffect
        val text = if (value) skinDownloadedText else somethingWrongText
        context.toast(text)
    }
}

@Composable
private fun InfoScreenContent(
    skin: Skin? = null,
    categoryName: String? = null,
    categoryVisible: Boolean = false,
    loading: Boolean = false,
    onDownloadClicked: () -> Unit = {},
    onFavoriteClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
    skinImageLoadingState: (AsyncImagePainter.State) -> Unit = {}
) {
    val downloadButtonBlurRadius = dimensionResource(
        id = com.anime.animecraft.core.common.android.R.dimen.download_button_blur_radius
    )
    val downloadButtonBorderRadius = dimensionResource(
        id = com.anime.animecraft.core.common.android.R.dimen.download_button_border_radius
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        SkinInfoCard(
            previewImageUrl = skin?.previewImageUrl ?: "",
            onBackClicked = onBackClicked,
            loading = loading,
            imageLoadingState = skinImageLoadingState
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
            LikeButton(
                favorite = skin?.favorite ?: false,
                likeClick = onFavoriteClicked
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.offsets.large))
        AnimatedVisibility(visible = categoryVisible) {
            CategoryItem(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                name = categoryName ?: ""
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        DownloadButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    dimensionResource(id = R.dimen.download_button_height)
                )
                .padding(horizontal = AppTheme.offsets.regular)
                .advanceShadow(
                    borderRadius = downloadButtonBorderRadius,
                    blurRadius = downloadButtonBlurRadius,
                    color = AppTheme.colors.primary
                ),
            onClick = onDownloadClicked
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
        BannerAd()
    }
}
