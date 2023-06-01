@file:Suppress("FunctionName", "LongMethod")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.favorites

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.ui.ad.BannerAd
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.SearchBar
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.common.buttons.BackButton
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
import ua.anime.animecraft.ui.navigation.FAVORITES
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun FavoritesScreen(
    backClicked: () -> Unit,
    itemClicked: (Int) -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by favoritesViewModel.favoritesFlow.collectLifecycleAwareFlowAsState(listOf())
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var downloadClicked by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = false) { favoritesViewModel.getFavorites() }

    if (downloadClicked &&
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
        favoritesViewModel.isDownloadDialogDisabled.not()
    ) {
        DownloadSkinDialog(
            dismissRequest = { downloadClicked = false },
            dontShowAgainClick = {
                favoritesViewModel.disableDownloadDialogOpen()
                downloadClicked = false
            }
        )
    }

    Scaffold(
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.offset_regular)
                ),
                currentScreen = FAVORITES
            )
        },
        bottomBar = {
            BannerAd()
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
                BackButton(backClicked)
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(id = R.string.favorites),
                    style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(32.dp))
                SearchBar(value = searchQuery) { query ->
                    searchQuery = query
                    favoritesViewModel.searchSkins(searchQuery)
                }
                Spacer(modifier = Modifier.height(32.dp))
                SkinsGrid(
                    skins = favorites,
                    itemClick = itemClicked,
                    likeClick = favoritesViewModel::updateFavoriteSkin,
                    downloadClick = { id ->
                        favoritesViewModel.saveGameSkinImage(id)
                        downloadClicked = true
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    AnimeCraftTheme {
        FavoritesScreen({}, {})
    }
}
