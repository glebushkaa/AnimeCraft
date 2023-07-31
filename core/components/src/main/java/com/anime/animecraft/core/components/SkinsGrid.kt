@file:Suppress("FunctionName", "LongParameterList")
@file:OptIn(ExperimentalFoundationApi::class)

package com.anime.animecraft.core.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anime.animecraft.core.components.extensions.header
import com.animecraft.model.Skin

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
    headerItem: @Composable () -> Unit = {},
    gridState: LazyGridState = rememberLazyGridState()
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        state = gridState,
        content = {
            header { headerItem() }
            items(
                items = skins,
                key = { it.id }
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
