@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui.dialogs.rate

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.common.DontShowAgainButton
import ua.anime.animecraft.ui.common.FilledDialogButton
import ua.anime.animecraft.ui.common.OutlinedDialogButton
import ua.anime.animecraft.ui.screens.settings.SettingsViewModel
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme
import ua.anime.animecraft.ui.theme.AppTheme.dialogProperties
import ua.anime.animecraft.ui.theme.dialogsShape

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

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

@Composable
fun RateDialog(
    dismissRequest: () -> Unit = {},
    rateClicked: (Int) -> Unit = {},
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    Dialog(
        onDismissRequest = dismissRequest,
        properties = dialogProperties
    ) {
        RateDialogContent(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.offset_large)),
            dismissRequest = dismissRequest,
            dontShowAgainClick = {
                settingsViewModel.disableRateDialog()
                dismissRequest()
            },
            rateClicked = {
                rateClicked(it)
                dismissRequest()
            }
        )
    }
}

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
            .background(color = AppTheme.colors.surface, shape = dialogsShape),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.offset_large)),
            text = stringResource(R.string.do_you_enjoy_our_app),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        SmilesSelector(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.offset_huge),
                bottom = dimensionResource(id = R.dimen.offset_large)
            ),
            selectedSmile = selectedSmile,
            onSmileChanged = {
                selectedSmile = it
            }
        )
        Text(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.offset_regular)),
            text = stringResource(R.string.provide_your_feedback),
            style = AppTheme.typography.titleMediumExtra.copy(fontWeight = FontWeight.Normal),
            color = AppTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        BottomRateDialogButtons(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.offset_regular),
                vertical = dimensionResource(id = R.dimen.offset_large)
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
                dimensionResource(id = R.dimen.offset_regular),
                Alignment.CenterHorizontally
            )
        ) {
            OutlinedDialogButton(
                text = stringResource(R.string.not_now),
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f),
                onClick = notNowClick
            )
            FilledDialogButton(
                text = stringResource(R.string.rate_us),
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f),
                onClick = rateUsClick
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_regular)))
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
            dimensionResource(id = R.dimen.offset_medium),
            Alignment.CenterHorizontally
        )
    ) {
        smilesList.forEach { smile ->
            AnimatedContent(targetState = smile.id == selectedSmile.id,
                transitionSpec = {
                    if (targetState) {
                        scaleIn(tween(300)) with scaleOut(tween(300))
                    } else fadeIn(tween(0)) with fadeOut(tween(0))
                }) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onSmileChanged(smile) }
                        ),
                    painter = painterResource(id = smile.resId),
                    contentDescription = "Smile image",
                    colorFilter = if (smile.id == selectedSmile.id) null else AppTheme.grayscaleFilter
                )
            }
        }
    }
}

@Preview
@Composable
fun RateDialogPreview() {
    AnimeCraftTheme(darkTheme = true) {
        RateDialog()
    }
}

data class Smile(
    val id: Int,
    val resId: Int,
    val grade: Int
)