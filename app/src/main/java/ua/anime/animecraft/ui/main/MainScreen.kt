@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import ua.anime.animecraft.MAIN
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.main.components.SearchBar
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun MainScreen(
    settingsClicked: () -> Unit,
    likeClicked: () -> Unit,
    itemClicked: (String) -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val skins by mainViewModel.skinsFlow.collectLifecycleAwareFlowAsState(initialValue = listOf())

    var searchQuery by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = false) { mainViewModel.getAllSkins() }

    Box(modifier = Modifier.background(color = AppTheme.colors.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.offset_regular),
                    end = dimensionResource(id = R.dimen.offset_regular)
                )
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_regular)))
            AppTopBar(
                currentScreen = MAIN,
                settingsClicked = settingsClicked,
                likeClicked = likeClicked
            )
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.offset_large))
            )
            SearchBar(value = searchQuery) {
                searchQuery = it
                mainViewModel.searchSkins(it)
            }
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.offset_large))
            )
            SkinsGrid(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                skins = skins,
                itemClick = itemClicked,
                likeClick = { mainViewModel.updateFavoriteSkin(it) }
            )
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
