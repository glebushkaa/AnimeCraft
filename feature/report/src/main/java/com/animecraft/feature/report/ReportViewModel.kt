package com.animecraft.feature.report

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.animecraft.common.ONE_SECOND
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

@HiltViewModel
class ReportViewModel @Inject constructor() : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(ReportScreenState())
    val screenState = _screenState.asStateFlow()

    fun updateReport(report: String) = viewModelScope.launch {
        val state = _screenState.value.copy(report = report)
        _screenState.emit(state)
    }

    fun showThanksDialogWithDelay() = viewModelScope.launch {
        delay(ONE_SECOND)
        val state = _screenState.value.copy(
            thanksDialogShown = true,
            report = ""
        )
        _screenState.emit(state)
    }
}
