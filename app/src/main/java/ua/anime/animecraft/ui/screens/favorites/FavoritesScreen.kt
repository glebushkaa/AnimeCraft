@file:Suppress("FunctionName", "LongMethod")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.favorites

import android.os.Build
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
import kotlinx.coroutines.launch
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.core.android.extensions.toast
import ua.anime.animecraft.ui.ad.BannerAd
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.SearchBar
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.common.buttons.BackButton
import ua.anime.animecraft.ui.common.buttons.ScrollTopButton
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
import ua.anime.animecraft.ui.navigation.FAVORITES
import ua.anime.animecraft.ui.theme.AppTheme

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
    val favorites by favoritesViewModel.favoritesFlow.collectLifecycleAwareFlowAsState(listOf())
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var downloadClicked by rememberSaveable { mutableStateOf(false) }
    val skinsGridState = rememberLazyGridState()
    val isScrollToTopVisible by remember {
        derivedStateOf { skinsGridState.firstVisibleItemIndex > 2 }
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val skinDownloadedText = stringResource(id = R.string.skin_downloaded)

    LaunchedEffect(key1 = false) { favoritesViewModel.getFavorites() }

    if (downloadClicked &&
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
        favoritesViewModel.isDownloadDialogDisabled.not()
    ) {
        DownloadSkinDialog(
            dismissRequest = { downloadClicked = false },
            dontShowAgainClick = favoritesViewModel::disableDownloadDialogOpen
        )
    }

    Scaffold(
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                currentScreen = FAVORITES,
                settingsClicked = onSettingsScreenNavigate
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
                        start = AppTheme.offsets.regular,
                        end = AppTheme.offsets.regular,
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
            ) {
                BackButton(onBackClick)
                Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
                Text(
                    text = stringResource(id = R.string.favorites),
                    style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
                SearchBar(value = searchQuery) { query ->
                    searchQuery = query
                    favoritesViewModel.searchSkins(searchQuery)
                }
                Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
                Box(modifier = Modifier.weight(1f)) {
                    SkinsGrid(
                        skins = favorites,
                        itemClick = onItemClicked,
                        likeClick = favoritesViewModel::updateFavoriteSkin,
                        downloadClick = { id ->
                            favoritesViewModel.saveGameSkinImage(id)
                            downloadClicked = true
                            context.toast(skinDownloadedText)
                        }
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
    )
}
