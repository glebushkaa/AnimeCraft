@file:Suppress("FunctionName", "LongMethod", "LongParameterList")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.animecraft.feature.main

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.android.extensions.permissionGranted
import com.anime.animecraft.core.android.extensions.toast
import com.anime.animecraft.core.components.AppTopBar
import com.anime.animecraft.core.components.CategoriesFlowRow
import com.anime.animecraft.core.components.RoundedProgressIndicator
import com.anime.animecraft.core.components.SearchBar
import com.anime.animecraft.core.components.SkinsGrid
import com.anime.animecraft.core.components.ad.BannerAd
import com.anime.animecraft.core.components.buttons.ScrollTopButton
import com.anime.animecraft.core.components.extensions.rememberForeverLazyGridState
import com.anime.animecraft.core.components.model.GridState
import com.anime.animecraft.core.theme.theme.AppTheme
import com.anime.animecraft.feature.main.R
import com.animecraft.model.Category
import com.animecraft.model.Skin
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun MainScreen(
    onSettingsNavigate: () -> Unit,
    onFavoritesNavigate: () -> Unit,
    onItemNavigate: (Int) -> Unit,
    onDownloadDialogNavigate: () -> Unit,
    onRateDialogNavigate: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val permissionExceptionMessage = stringResource(
        R.string.we_cant_load_without_permission
    )
    val skinDownloadedText = stringResource(R.string.skin_downloaded)
    val somethingWrongText = stringResource(R.string.something_went_wrong)

    var currentSkinDownloadId: Int? by rememberSaveable { mutableStateOf(null) }

    val writePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            currentSkinDownloadId?.let { mainViewModel.saveGameSkinImage(it) }
        } else {
            context.toast(permissionExceptionMessage)
        }
        currentSkinDownloadId = null
    }

    val screenState by mainViewModel.screenState.collectAsStateWithLifecycle(
        MainScreenState()
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colors.background,
        contentColor = AppTheme.colors.background,
        bottomBar = { BannerAd() },
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                settingsClicked = onSettingsNavigate,
                likeClicked = onFavoritesNavigate
            )
        },
        content = {
            MainScreenContent(
                modifier = Modifier.padding(
                    start = AppTheme.offsets.regular,
                    end = AppTheme.offsets.regular,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                searchQuery = screenState.searchInput,
                skins = screenState.skins,
                categories = screenState.categories,
                onItemClicked = onItemNavigate,
                onSearchQueryUpdated = mainViewModel::updateSearchInput,
                onGridStateUpdated = mainViewModel::updateFirstItemIndex,
                onDownloadClicked = { id ->
                    when {
                        context.permissionGranted(WRITE_EXTERNAL_STORAGE) &&
                            SDK_INT <= VERSION_CODES.Q -> {
                            mainViewModel.saveGameSkinImage(id)
                        }

                        SDK_INT > VERSION_CODES.Q -> {
                            mainViewModel.tryShowDownloadDialog()
                            mainViewModel.saveGameSkinImage(id)
                        }

                        else -> {
                            currentSkinDownloadId = id
                            writePermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
                        }
                    }
                },
                selectedCategory = screenState.selectedCategory,
                onCategorySelected = mainViewModel::updateSelectedCategory,
                onLikeClicked = { id -> mainViewModel.updateFavoriteSkin(id) },
                areSkinsLoaded = screenState.isLoading,
                gridState = screenState.gridState
            )
        }
    )

    LaunchedEffect(key1 = screenState.rateDialogShown) {
        val value = screenState.rateDialogShown.getContentIfNotHandled() ?: return@LaunchedEffect
        if (!value) return@LaunchedEffect
        onRateDialogNavigate()
    }

    LaunchedEffect(key1 = screenState.downloadDialogShown) {
        val value =
            screenState.downloadDialogShown.getContentIfNotHandled() ?: return@LaunchedEffect
        if (!value) return@LaunchedEffect
        onDownloadDialogNavigate()
    }

    LaunchedEffect(key1 = screenState.downloadState) {
        val value = screenState.downloadState?.getContentIfNotHandled() ?: return@LaunchedEffect
        val text = if (value) skinDownloadedText else somethingWrongText
        context.toast(text)
    }
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    skins: List<Skin>,
    gridState: GridState?,
    categories: List<Category>,
    selectedCategory: Category? = null,
    onItemClicked: (Int) -> Unit = {},
    onSearchQueryUpdated: (String) -> Unit = {},
    onDownloadClicked: (Int) -> Unit = {},
    onLikeClicked: (Int) -> Unit = {},
    onGridStateUpdated: (GridState) -> Unit = {},
    onCategorySelected: (Category) -> Unit = {},
    areSkinsLoaded: Boolean = false
) {
    val skinsGridState = rememberForeverLazyGridState(
        gridState = gridState,
        onDispose = onGridStateUpdated
    )
    val isScrollToTopVisible by remember {
        derivedStateOf { skinsGridState.firstVisibleItemIndex > 2 }
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.padding(vertical = AppTheme.offsets.large),
            value = searchQuery,
            onValueChanged = onSearchQueryUpdated
        )

        Box(modifier = Modifier.weight(1f)) {
            if (!areSkinsLoaded && skins.isEmpty()) {
                RoundedProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(
                            dimensionResource(id = R.dimen.progress_bar_size)
                        ),
                    color = AppTheme.colors.primary,
                    strokeWidth = AppTheme.strokes.huge
                )
            }
            SkinsGrid(
                modifier = Modifier.align(Alignment.TopCenter),
                skins = skins,
                itemClick = onItemClicked,
                likeClick = onLikeClicked,
                downloadClick = onDownloadClicked,
                headerItem = {
                    CategoriesFlowRow(
                        categories = categories,
                        onCategorySelected = onCategorySelected,
                        selectedCategory = selectedCategory
                    )
                },
                gridState = skinsGridState
            )
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomEnd),
                visible = isScrollToTopVisible,
                enter = slideInHorizontally { fullSize -> fullSize },
                exit = slideOutHorizontally { fullSize -> fullSize + fullSize }
            ) {
                ScrollTopButton {
                    scope.launch {
                        skinsGridState.animateScrollToItem(index = 0)
                    }
                }
            }
        }
    }
}
