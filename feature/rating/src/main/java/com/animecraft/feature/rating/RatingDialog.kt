@file:Suppress("FunctionName", "MagicNumber", "LongMethod")
@file:OptIn(ExperimentalAnimationApi::class)

package com.animecraft.feature.rating

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.anime.animecraft.core.android.extensions.sendFeedback
import com.anime.animecraft.core.components.AnimatedScaleDialogContent
import com.anime.animecraft.core.components.PRE_DISMISS_DELAY
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.animecraft.common.ONE_SECOND
import com.animecraft.feature.rating.feedback.FeedbackDialogContent
import com.animecraft.feature.rating.rate.RateDialogContent
import com.animecraft.feature.rating.thanks.ThanksDialogContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

@Composable
fun RatingDialog(
    onDismissRequest: () -> Unit = {},
    ratingViewModel: RatingViewModel = hiltViewModel()
) {
    var isDismissed by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isDismissed) {
        if (isDismissed) {
            delay(PRE_DISMISS_DELAY)
            onDismissRequest()
        }
    }

    val dialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    )

    Dialog(
        onDismissRequest = {
            isDismissed = true
            onDismissRequest()
        },
        properties = dialogProperties
    ) {
        AnimatedScaleDialogContent(content = {
            AnimatedRatingDialogContent(
                onDismissRequest = { isDismissed = true },
                onRatingDialogDisable = ratingViewModel::disableRatingDialog,
                onRatingDialogCompleted = ratingViewModel::setRatingCompleted,
                onRatingSend = ratingViewModel::sendRating
            )
        }, isDismissed = isDismissed)
    }
}

@Composable
private fun AnimatedRatingDialogContent(
    onDismissRequest: () -> Unit = {},
    onRatingDialogDisable: () -> Unit = {},
    onRatingDialogCompleted: () -> Unit = {},
    onRatingSend: (Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var currentRatingDialog by rememberSaveable { mutableStateOf(RatingDialogMode.RATING) }

    AnimatedContent(
        targetState = currentRatingDialog,
        transitionSpec = { ratingDialogModeContentTransform }
    ) { dialog ->
        when (dialog) {
            RatingDialogMode.RATING -> RateDialogContent(
                modifier = Modifier.padding(horizontal = AppTheme.offsets.large),
                dismissRequest = onDismissRequest,
                dontShowAgainClick = {
                    onDismissRequest()
                    onRatingDialogDisable()
                },
                rateClicked = {
                    onRatingSend(it)
                    currentRatingDialog = if (it <= MIN_GOOD_GRADE) {
                        RatingDialogMode.FEEDBACK
                    } else {
                        RatingDialogMode.THANKS
                    }
                }
            )

            RatingDialogMode.FEEDBACK -> {
                FeedbackDialogContent(
                    modifier = Modifier.padding(
                        horizontal = AppTheme.offsets.regular
                    ),
                    onFeedbackSent = {
                        scope.launch {
                            context.sendFeedback(it)
                            delay(ONE_SECOND)
                            onRatingDialogCompleted()
                            currentRatingDialog = RatingDialogMode.THANKS
                        }
                    },
                    onCanceled = onDismissRequest
                )
            }

            RatingDialogMode.THANKS -> {
                ThanksDialogContent(
                    modifier = Modifier.padding(
                        horizontal = AppTheme.offsets.regular
                    ),
                    onDismissRequest = onDismissRequest
                )
            }
        }
    }
}

private const val MIN_GOOD_GRADE = 50
private const val RATING_DIALOG_ANIMATION_DURATION = 600
private val ratingDialogAnimationSpec = tween<Float>(RATING_DIALOG_ANIMATION_DURATION)

private val ratingDialogModeContentTransform =
    scaleIn(ratingDialogAnimationSpec) + fadeIn(ratingDialogAnimationSpec) with
        scaleOut(ratingDialogAnimationSpec) + fadeOut(ratingDialogAnimationSpec)
