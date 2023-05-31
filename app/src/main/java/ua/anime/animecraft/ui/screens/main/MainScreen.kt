@file:Suppress("FunctionName", "LongMethod", "LongParameterList", "MagicNumber")
@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui.screens.main

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.ui.ad.BannerAd
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.RoundedProgressIndicator
import ua.anime.animecraft.ui.common.SearchBar
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
import ua.anime.animecraft.ui.dialogs.rating.RatingDialog
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
    val skins by mainViewModel.skinsFlow.collectLifecycleAwareFlowAsState(initialValue = listOf())
    var searchQuery by rememberSaveable { mutableStateOf("") }

    var downloadSelected by rememberSaveable { mutableStateOf(false) }

    val downloadDialogShown by remember {
        derivedStateOf {
            downloadSelected && SDK_INT >= VERSION_CODES.Q &&
                mainViewModel.isDownloadDialogDisabled.not()
        }
    }
    var ratingDialogShown by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colors.background,
        contentColor = AppTheme.colors.background,
        bottomBar = { BannerAd() },
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.offset_regular)
                ),
                currentScreen = MAIN,
                settingsClicked = settingsClicked,
                likeClicked = likeClicked
            )
        },
        content = {
            MainScreenContent(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.offset_regular),
                    end = dimensionResource(id = R.dimen.offset_regular),
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                searchQuery = searchQuery,
                skins = skins,
                itemClicked = itemClicked,
                onSearchQueryUpdated = { query ->
                    searchQuery = query
                    mainViewModel.searchSkins(searchQuery)
                },
                onDownloadClicked = { id ->
                    mainViewModel.saveGameSkinImage(id)
                    downloadSelected = true
                },
                onLikeClicked = { id -> mainViewModel.updateFavoriteSkin(id) },
                areSkinsLoaded = mainViewModel.areSkinsLoaded
            )
        }
    )

    AnimatedVisibility(visible = downloadDialogShown) {
        DownloadSkinDialog(
            dismissRequest = { downloadSelected = false },
            dontShowAgainClick = {
                mainViewModel.disableDownloadDialogOpen()
                downloadSelected = false
            }
        )
    }

    AnimatedVisibility(visible = ratingDialogShown) {
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
                ratingDialogShown = false
            }
        )
    }

    LaunchedEffect(key1 = false) {
        mainViewModel.getAllSkins()
        delay(1000)
        ratingDialogShown = mainViewModel.shouldRateDialogBeShown()
    }
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    skins: List<Skin>,
    itemClicked: (Int) -> Unit,
    onSearchQueryUpdated: (String) -> Unit,
    onDownloadClicked: (Int) -> Unit,
    onLikeClicked: (Int) -> Unit,
    areSkinsLoaded: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.offset_large)),
            value = searchQuery,
            onValueChanged = onSearchQueryUpdated
        )
        if (!areSkinsLoaded && skins.isEmpty()) {
            RoundedProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp),
                color = AppTheme.colors.primary,
                strokeWidth = 12.dp
            )
        }
        SkinsGrid(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            skins = skins,
            itemClick = itemClicked,
            likeClick = onLikeClicked,
            downloadClick = onDownloadClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AnimeCraftTheme {
        MainScreen({}, {}, {})
    }
}
