@file:Suppress("FunctionName", "MagicNumber")
@file:OptIn(ExperimentalAnimationApi::class)

package com.animecraft.feature.rating.rate

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.anime.animecraft.feature.rating.R
import com.anime.animecraft.core.components.buttons.DontShowAgainButton
import com.anime.animecraft.core.components.buttons.FilledDialogButton
import com.anime.animecraft.core.components.buttons.OutlinedDialogButton
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.feature.rating.Smile

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

@Composable
fun RateDialogContent(
    modifier: Modifier = Modifier,
    dismissRequest: () -> Unit = {},
    rateClicked: (Int) -> Unit = {},
    dontShowAgainClick: () -> Unit = {}
) {
    var selectedSmile by remember { mutableStateOf(smilesList.last()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.surface,
                shape = AppTheme.shapes.large
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(
                top = AppTheme.offsets.large,
                start = AppTheme.offsets.regular,
                end = AppTheme.offsets.regular
            ),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.do_you_enjoy_our_app),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        SmilesSelector(
            modifier = Modifier.padding(
                top = AppTheme.offsets.huge,
                bottom = AppTheme.offsets.large
            ),
            selectedSmile = selectedSmile,
            onSmileChanged = {
                selectedSmile = it
            }
        )
        Text(
            modifier = Modifier.padding(
                horizontal = AppTheme.offsets.regular
            ),
            text = stringResource(R.string.provide_your_feedback),
            style = AppTheme.typography.titleMediumExtra.copy(fontWeight = FontWeight.Normal),
            color = AppTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        BottomRateDialogButtons(
            modifier = Modifier.padding(
                horizontal = AppTheme.offsets.regular,
                vertical = AppTheme.offsets.large
            ),
            dontShowAgainClick = dontShowAgainClick,
            notNowClick = dismissRequest,
            rateUsClick = {
                rateClicked(selectedSmile.grade)
            }
        )
    }
}

@Composable
private fun BottomRateDialogButtons(
    modifier: Modifier = Modifier,
    dontShowAgainClick: () -> Unit = {},
    notNowClick: () -> Unit = {},
    rateUsClick: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                AppTheme.offsets.regular,
                Alignment.CenterHorizontally
            )
        ) {
            OutlinedDialogButton(
                text = stringResource(R.string.not_now),
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.rate_button_height)
                    )
                    .weight(1f),
                onClick = notNowClick
            )
            FilledDialogButton(
                text = stringResource(R.string.rate_us),
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.rate_button_height)
                    )
                    .weight(1f),
                onClick = rateUsClick
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.offsets.regular))
        DontShowAgainButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = dontShowAgainClick
        )
    }
}

@Composable
private fun SmilesSelector(
    modifier: Modifier = Modifier,
    selectedSmile: Smile,
    onSmileChanged: (Smile) -> Unit = { }
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            AppTheme.offsets.medium,
            Alignment.CenterHorizontally
        )
    ) {
        smilesList.forEach { smile ->
            AnimatedContent(
                targetState = smile.id == selectedSmile.id,
                transitionSpec = {
                    if (targetState) chosenSmileAnimation else unChosenSmileAnimation
                }
            ) {
                Image(
                    modifier = Modifier
                        .size(
                            dimensionResource(id = R.dimen.rate_smile_size)
                        )
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null,
                            onClick = { onSmileChanged(smile) }
                        ),
                    painter = painterResource(id = smile.resId),
                    contentDescription = stringResource(R.string.smile_image),
                    colorFilter = if (smile.id == selectedSmile.id) {
                        null
                    } else {
                        AppTheme.grayscaleFilter
                    }
                )
            }
        }
    }
}

private const val CHOSEN_SMILE_ANIMATION_DURATION = 300
private const val UN_CHOSEN_SMILE_ANIMATION_DURATION = 0

private val chosenSmileAnimationSpec =
    tween<Float>(durationMillis = CHOSEN_SMILE_ANIMATION_DURATION)
private val unChosenSmileAnimationSpec =
    tween<Float>(durationMillis = UN_CHOSEN_SMILE_ANIMATION_DURATION)
private val chosenSmileAnimation =
    scaleIn(chosenSmileAnimationSpec) with scaleOut(chosenSmileAnimationSpec)
private val unChosenSmileAnimation =
    fadeIn(unChosenSmileAnimationSpec) with fadeOut(unChosenSmileAnimationSpec)

private val smilesList = listOf(
    Smile(
        id = 0,
        grade = 0,
        resId = R.drawable.ic_downcast_face_with_sweat
    ),
    Smile(
        id = 1,
        grade = 25,
        resId = R.drawable.ic_disappointed_face
    ),
    Smile(
        id = 2,
        grade = 50,
        resId = R.drawable.ic_neutral_face
    ),
    Smile(
        id = 3,
        grade = 75,
        resId = R.drawable.ic_smiling_face
    ),
    Smile(
        id = 4,
        grade = 100,
        resId = R.drawable.ic_face_with_hearts
    )
)
