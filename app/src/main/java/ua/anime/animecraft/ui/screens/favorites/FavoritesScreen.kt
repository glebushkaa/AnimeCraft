@file:Suppress("FunctionName", "LongMethod", "LongParameterList")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.favorites

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
import kotlinx.coroutines.launch
import ua.anime.animecraft.R
import com.animecraft.core.common_android.android.Event
import com.animecraft.core.common_android.android.extensions.collectLifecycleAwareFlowAsState
import com.animecraft.core.common_android.android.extensions.permissionGranted
import com.animecraft.core.common_android.android.extensions.toast
import ua.anime.animecraft.ui.ad.BannerAd
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.SearchBar
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.common.buttons.BackButton
import ua.anime.animecraft.ui.common.buttons.ScrollTopButton
import ua.anime.animecraft.ui.dialogs.downloadskin.DownloadSkinDialog
import ua.anime.animecraft.ui.model.Skin
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
    val context = LocalContext.current
    val permissionExceptionMessage = stringResource(R.string.we_cant_load_without_permission)

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

    val favorites by favoritesViewModel.favoritesFlow.collectLifecycleAwareFlowAsState(listOf())
    val downloadFlow by favoritesViewModel.downloadFlow.collectLifecycleAwareFlowAsState(
        com.animecraft.core.common_android.android.Event(null)
    )

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var downloadClicked by remember { mutableStateOf(false) }

    val downloadSelected by remember {
        derivedStateOf {
            downloadClicked &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                favoritesViewModel.isDownloadDialogDisabled.not()
        }
    }

    val somethingWrongText = stringResource(R.string.something_went_wrong)
    val skinDownloadedText = stringResource(id = R.string.skin_downloaded)

    LaunchedEffect(key1 = downloadFlow) {
        val value = downloadFlow.getContentIfNotHandled() ?: return@LaunchedEffect
        downloadClicked = value
        val text = if (value) skinDownloadedText else somethingWrongText
        context.toast(text)
    }

    if (downloadSelected) {
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
                favorites = favorites,
                onLikeClicked = favoritesViewModel::updateFavoriteSkin,
                onDownloadClicked = { id ->
                    if (context.permissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                        Build.VERSION.SDK_INT > Build.VERSION_CODES.Q
                    ) {
                        favoritesViewModel.saveGameSkinImage(id)
                    } else {
                        currentSkinDownloadId = id
                        writePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                },
                searchQuery = searchQuery,
                onSearchQueryChanged = { query ->
                    searchQuery = query
                    favoritesViewModel.searchSkins(searchQuery)
                }
            )
        }
    )
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
        SearchBar(value = searchQuery, onValueChanged = onSearchQueryChanged)
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
