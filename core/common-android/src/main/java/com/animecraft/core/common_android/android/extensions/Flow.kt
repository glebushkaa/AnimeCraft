package com.animecraft.core.common_android.android.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Composable
fun <T> Flow<T>.collectLifecycleAwareFlowAsState(
    initialValue: T,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): State<T> {
    return remember(key1 = this, key2 = lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }.collectAsState(initial = initialValue)
}
