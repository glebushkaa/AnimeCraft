@file:Suppress("FunctionName", "LongMethod", "LongParameterList")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.main

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.Event
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.core.android.extensions.toast
import ua.anime.animecraft.core.common.TWO_SECONDS
import ua.anime.animecraft.ui.ad.BannerAd
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.CategoriesFlowRow
import ua.anime.animecraft.ui.common.RoundedProgressIndicator
import ua.anime.animecraft.ui.common.SearchBar
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.common.buttons.ScrollTopButton
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
import ua.anime.animecraft.ui.dialogs.rating.RatingDialog
import ua.anime.animecraft.ui.model.Category
import ua.anime.animecraft.ui.model.Skin
import ua.anime.animecraft.ui.navigation.MAIN
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun MainScreen(
    settingsClicked: () -> Unit,
    likeClicked: () -> Unit,
    itemClicked: (Int) -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val skins by mainViewModel.skinsFlow.collectLifecycleAwareFlowAsState(initialValue = listOf())
    val categories by mainViewModel.categoriesFlow.collectLifecycleAwareFlowAsState(
        initialValue = listOf()
    )
    val downloadFlow by mainViewModel.downloadFlow.collectLifecycleAwareFlowAsState(Event(null))
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(mainViewModel.selectedCategory) }

    var downloadSelected by remember { mutableStateOf(false) }

    val downloadDialogShown by remember {
        derivedStateOf {
            downloadSelected &&
                SDK_INT >= VERSION_CODES.Q &&
                mainViewModel.isDownloadDialogDisabled.not()
        }
    }
    var ratingDialogShown by remember { mutableStateOf(false) }
    val skinDownloadedText = stringResource(R.string.skin_downloaded)
    val somethingWrongText = stringResource(R.string.something_went_wrong)

    LaunchedEffect(key1 = downloadFlow) {
        val value = downloadFlow.getContentIfNotHandled() ?: return@LaunchedEffect
        downloadSelected = value
        val text = if (value) skinDownloadedText else somethingWrongText
        context.toast(text)
    }

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
                currentScreen = MAIN,
                settingsClicked = settingsClicked,
                likeClicked = likeClicked

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
                searchQuery = searchQuery,
                skins = skins,
                categories = categories,
                onItemClicked = itemClicked,
                onSearchQueryUpdated = { query ->
                    searchQuery = query
                    mainViewModel.searchSkins(searchQuery)
                },
                onDownloadClicked = mainViewModel::saveGameSkinImage,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = if (category.id == selectedCategory?.id) {
                        null
                    } else category
                    mainViewModel.selectCategory(selectedCategory)
                },
                onLikeClicked = { id -> mainViewModel.updateFavoriteSkin(id) },
                areSkinsLoaded = mainViewModel.areSkinsLoaded
            )
        }
    )

    if (downloadDialogShown) {
        DownloadSkinDialog(
            dismissRequest = { downloadSelected = false },
            dontShowAgainClick = mainViewModel::disableDownloadDialogOpen
        )
    }

    if (ratingDialogShown) {
        mainViewModel.setDialogWasShown()
        RatingDialog(
            onDismissRequest = {
                ratingDialogShown = false
            },
            onRatingDialogCompleted = {
                mainViewModel.setRateDialogCompleted()
            },
            onRatingDialogDisable = {
                mainViewModel.disableRateDialog()
            }
        )
    }

    LaunchedEffect(key1 = false) {
        delay(TWO_SECONDS)
        ratingDialogShown = mainViewModel.shouldRateDialogBeShown()
    }
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    skins: List<Skin>,
    categories: List<Category>,
    selectedCategory: Category? = null,
    onItemClicked: (Int) -> Unit = {},
    onSearchQueryUpdated: (String) -> Unit = {},
    onDownloadClicked: (Int) -> Unit = {},
    onLikeClicked: (Int) -> Unit = {},
    onCategorySelected: (Category) -> Unit = {},
    areSkinsLoaded: Boolean = false
) {
    val skinsGridState = rememberLazyGridState()
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
                        .size(AppTheme.sizes.screens.main.progressBarSize),
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AnimeCraftTheme {
        MainScreen({}, {}, {})
    }
}
