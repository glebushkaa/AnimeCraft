package com.animecraft.feature.rating

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.domain.usecase.preferences.rate.CompleteRateDialogUseCase
import com.animecraft.core.domain.usecase.preferences.rate.DisableRateDialogUseCase
import com.animecraft.core.domain.usecase.rating.SendRatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/19/2023
 */

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val sendRatingUseCase: SendRatingUseCase,
    private val disableRateDialogUseCase: DisableRateDialogUseCase,
    private val completeRateDialogUseCase: CompleteRateDialogUseCase
) : AnimeCraftViewModel() {

    fun sendRating(rating: Int) = viewModelScope.launch {
        val params = SendRatingUseCase.Params(rating)
        sendRatingUseCase(params)
    }

    fun disableRatingDialog() = viewModelScope.launch {
        disableRateDialogUseCase()
    }

    fun setRatingCompleted() = viewModelScope.launch {
        completeRateDialogUseCase()
    }
}
