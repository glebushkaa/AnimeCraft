package com.anime.animecraft.core.components.extensions

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.anime.animecraft.core.components.model.GridState

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/20/2023
 */

@Composable
fun rememberForeverLazyGridState(
    gridState: GridState? = null,
    onDispose: (GridState) -> Unit = {}
): LazyGridState {
    val state = rememberLazyGridState()
    DisposableEffect(key1 = null) {
        onDispose {
            onDispose(
                GridState(
                    firstItemIndex = state.firstVisibleItemIndex,
                    firstItemScrollOffset = state.firstVisibleItemScrollOffset
                )
            )
        }
    }

    LaunchedEffect(key1 = gridState) {
        val value = gridState ?: return@LaunchedEffect
        state.scrollToItem(index = value.firstItemIndex, scrollOffset = value.firstItemScrollOffset)
    }
    return state
}
