@file:Suppress("FunctionName", "LongMethod", "LongParameterList")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.animecraft.feature.favorites

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.anime.animecraft.feature.favorites.R
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.android.extensions.permissionGranted
import com.anime.animecraft.core.android.extensions.toast
import kotlinx.coroutines.launch
import com.anime.animecraft.core.components.AppTopBar
import com.anime.animecraft.core.components.SearchBar
import com.anime.animecraft.core.components.SkinsGrid
import com.anime.animecraft.core.components.ad.BannerAd
import com.anime.animecraft.core.components.buttons.BackButton
import com.anime.animecraft.core.components.buttons.ScrollTopButton
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit = {},
    onItemClicked: (Int) -> Unit = {},
    onSettingsScreenNavigate: () -> Unit = {},
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionExceptionMessage = stringResource(R.string.we_cant_load_without_permission)
    val somethingWrongText = stringResource(R.string.something_went_wrong)
    val skinDownloadedText = stringResource(id = R.string.skin_downloaded)

    var currentSkinDownloadId: Int? by rememberSaveable { mutableStateOf(null) }

    val writePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            currentSkinDownloadId?.let { favoritesViewModel.saveGameSkinImage(it) }
        } else {
            context.toast(permissionExceptionMessage)
        }
        currentSkinDownloadId = null
    }

    val state by favoritesViewModel.screenState.collectAsStateWithLifecycle(
        initialValue = FavoriteScreenState()
    )

    Scaffold(
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                likeIconFilled = true,
                settingsClicked = onSettingsScreenNavigate
            )
        },
        bottomBar = {
            BannerAd()
        },
        content = {
            FavoritesScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = AppTheme.offsets.regular,
                        end = AppTheme.offsets.regular,
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    ),
                onBackClick = onBackClick,
                onItemClicked = onItemClicked,
                favorites = state.favorites,
                onLikeClicked = favoritesViewModel::updateFavoriteSkin,
                onDownloadClicked = { id ->
                    when {
                        context.permissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                            Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q -> {
                            favoritesViewModel.saveGameSkinImage(id)
                        }

                        Build.VERSION.SDK_INT > Build.VERSION_CODES.Q -> {
                            favoritesViewModel.showDownloadDialog()
                            favoritesViewModel.saveGameSkinImage(id)
                        }

                        else -> {
                            currentSkinDownloadId = id
                            writePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    }
                },
                searchQuery = state.searchInput,
                onSearchQueryChanged = favoritesViewModel::updateSearchInput
            )
        }
    )

    LaunchedEffect(key1 = state.downloadState) {
        val value = state.downloadState.getContentIfNotHandled() ?: return@LaunchedEffect
        val text = if (value) skinDownloadedText else somethingWrongText
        context.toast(text)
    }
}

@Composable
private fun FavoritesScreenContent(
    modifier: Modifier = Modifier,
    favorites: List<Skin> = listOf(),
    onBackClick: () -> Unit = {},
    onItemClicked: (Int) -> Unit = {},
    onLikeClicked: (Int) -> Unit = {},
    onDownloadClicked: (Int) -> Unit = {},
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit = {}
) {
    val skinsGridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val isScrollToTopVisible by remember {
        derivedStateOf { skinsGridState.firstVisibleItemIndex > 2 }
    }

    Column(modifier = modifier) {
        BackButton(onBackClick)
        Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
        Text(
            text = stringResource(id = R.string.favorites),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
        SearchBar(
            value = searchQuery,
            onValueChanged = onSearchQueryChanged
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
        Box(modifier = Modifier.weight(1f)) {
            SkinsGrid(
                skins = favorites,
                itemClick = onItemClicked,
                likeClick = onLikeClicked,
                downloadClick = onDownloadClicked,
                gridState = skinsGridState
            )
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomEnd),
                visible = isScrollToTopVisible,
                enter = slideInHorizontally { fullSize -> fullSize },
                exit = slideOutHorizontally { fullSize -> fullSize + fullSize }
            ) {
                ScrollTopButton {
                    scope.launch { skinsGridState.animateScrollToItem(index = 0) }
                }
            }
        }
    }
}
