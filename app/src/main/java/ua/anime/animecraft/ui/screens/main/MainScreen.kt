@file:Suppress("FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.main

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.ui.MAIN
import ua.anime.animecraft.ui.ad.BannerAd
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.SearchBar
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
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

    var downloadClicked by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = false) { mainViewModel.getAllSkins() }

    if (downloadClicked &&
        SDK_INT >= VERSION_CODES.Q &&
        mainViewModel.isDownloadDialogDisabled.not()
    ) {
        DownloadSkinDialog(
            dismissRequest = { downloadClicked = false },
            dontShowAgainClick = {
                mainViewModel.disableDownloadDialogOpen()
                downloadClicked = false
            }
        )
    }

    Scaffold(
        containerColor = AppTheme.colors.background,
        contentColor = AppTheme.colors.background,
        bottomBar = { BannerAd() },
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.offset_regular)),
                currentScreen = MAIN,
                settingsClicked = settingsClicked,
                likeClicked = likeClicked
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = dimensionResource(id = R.dimen.offset_regular),
                        end = dimensionResource(id = R.dimen.offset_regular),
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
            ) {
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.offset_large))
                )
                SearchBar(value = searchQuery) { query ->
                    searchQuery = query
                    mainViewModel.searchSkins(query)
                }
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.offset_large))
                )
                SkinsGrid(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    skins = skins,
                    itemClick = itemClicked,
                    likeClick = { id -> mainViewModel.updateFavoriteSkin(id) },
                    downloadClick = { id ->
                        mainViewModel.saveGameSkinImage(id)
                        downloadClicked = true
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AnimeCraftTheme {
        MainScreen({}, {}, {})
    }
}