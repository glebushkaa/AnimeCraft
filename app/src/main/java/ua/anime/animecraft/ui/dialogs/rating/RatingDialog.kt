@file:Suppress("FunctionName", "MagicNumber")
@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui.dialogs.rating

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.sendFeedback
import ua.anime.animecraft.ui.dialogs.rating.feedback.FeedbackDialogContent
import ua.anime.animecraft.ui.dialogs.rating.rate.RateDialogContent
import ua.anime.animecraft.ui.dialogs.rating.thanks.ThanksDialogContent

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

@Composable
fun RatingDialog(
    onDismissRequest: () -> Unit = {},
    onRatingDialogDisable: () -> Unit = {},
    onRatingDialogCompleted: () -> Unit = {}

) {
    val context = LocalContext.current
    var currentRatingDialog by rememberSaveable { mutableStateOf(RatingDialogMode.RATING) }

    val dialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    )

    AnimatedContent(targetState = currentRatingDialog) { dialog ->
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = dialogProperties
        ) {
            when (dialog) {
                RatingDialogMode.RATING -> RateDialogContent(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.offset_large)
                    ),
                    dismissRequest = onDismissRequest,
                    dontShowAgainClick = onRatingDialogDisable,
                    rateClicked = {
                        currentRatingDialog = if (it <= 50) {
                            RatingDialogMode.FEEDBACK
                        } else {
                            RatingDialogMode.THANKS
                        }
                    }
                )

                RatingDialogMode.FEEDBACK -> {
                    FeedbackDialogContent(
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.offset_regular)
                        ),
                        onFeedbackSent = {
                            context.sendFeedback(it)
                            onRatingDialogCompleted()
                            currentRatingDialog = RatingDialogMode.THANKS
                        },
                        onCanceled = {
                            onDismissRequest()
                            currentRatingDialog = RatingDialogMode.RATING
                        }
                    )
                }

                RatingDialogMode.THANKS -> {
                    ThanksDialogContent(
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.offset_regular)
                        ),
                        onDismissRequest = onDismissRequest
                    )
                }
            }
        }
    }
}
