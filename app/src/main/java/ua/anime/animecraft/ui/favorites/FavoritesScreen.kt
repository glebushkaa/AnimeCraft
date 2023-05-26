package ua.anime.animecraft.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.FAVORITES
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.BackButton
import ua.anime.animecraft.ui.common.SkinsGrid
import ua.anime.animecraft.ui.main.components.SearchBar
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

private val testStringList = listOf(
    "Piter", "Test skin", "I don't know",
    "Piter", "Test skin", "I don't know",
    "Piter", "Test skin", "I don't know",
    "Piter", "Test skin", "I don't know",
)

@Composable
fun FavoritesScreen(
    backClicked: () -> Unit,
    itemClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_regular)))
            AppTopBar(currentScreen = FAVORITES)
            BackButton(backClicked)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.favorites),
                style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            SearchBar(value = "", onValueChanged = {})
            Spacer(modifier = Modifier.height(32.dp))
//            SkinsGrid(skins = testStringList, itemClick = itemClicked)
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    AnimeCraftTheme {
        FavoritesScreen({}, {})
    }
}