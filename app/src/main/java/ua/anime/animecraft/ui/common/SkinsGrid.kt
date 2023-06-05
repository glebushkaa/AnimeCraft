@file:Suppress("FunctionName", "LongParameterList")
@file:OptIn(ExperimentalFoundationApi::class)

package ua.anime.animecraft.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.ui.extensions.header
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun SkinsGrid(
    modifier: Modifier = Modifier,
    skins: List<Skin>,
    itemClick: (Int) -> Unit = {},
    likeClick: (Int) -> Unit = {},
    downloadClick: (Int) -> Unit = {},
    headerItem: @Composable () -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        content = {
            header { headerItem() }
            items(
                items = skins,
                key = { skin -> skin.id }
            ) { skin ->
                PreviewItem(
                    modifier = Modifier.animateItemPlacement(),
                    skin = skin,
                    itemClick = itemClick,
                    likeClick = likeClick,
                    downloadClick = downloadClick
                )
            }
        }
    )
}
