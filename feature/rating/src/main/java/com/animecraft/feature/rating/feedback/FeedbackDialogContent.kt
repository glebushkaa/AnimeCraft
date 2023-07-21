@file:Suppress("LongMethod", "FunctionName", "MagicNumber")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.animecraft.feature.rating.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.anime.animecraft.core.components.buttons.FilledDialogButton
import com.anime.animecraft.core.components.buttons.OutlinedDialogButton
import com.anime.animecraft.core.theme.theme.AppTheme
import com.anime.animecraft.feature.rating.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023.
 */

@Composable
fun FeedbackDialogContent(
    modifier: Modifier = Modifier,
    onFeedbackSent: (String) -> Unit,
    onCanceled: () -> Unit
) {
    var feedback by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.surface,
                shape = AppTheme.shapes.large
            )
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = AppTheme.offsets.large)
                .align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.sorry_to_hear),
            style = AppTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colors.onBackground
        )
        OutlinedTextField(
            value = feedback,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = dimensionResource(id = R.dimen.feedback_min_text_field_height)
                )
                .padding(horizontal = AppTheme.offsets.regular)
                .border(
                    width = AppTheme.strokes.small,
                    color = AppTheme.colors.primary,
                    shape = AppTheme.shapes.medium
                ),
            onValueChange = {
                val query = if (it.length > FEEDBACK_MAX_SYMBOLS) {
                    it.substring(0, FEEDBACK_MAX_SYMBOLS)
                } else {
                    it
                }
                feedback = query
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = AppTheme.colors.primary,
                textColor = AppTheme.colors.primary,
                focusedBorderColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.tell_us_your_feedback),
                    style = AppTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = AppTheme.colors.onBackground.copy(0.6f)
                )
            }
        )
        Text(
            modifier = Modifier.padding(AppTheme.offsets.regular),
            text = stringResource(R.string.please_provide_your_thoughts),
            style = AppTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center,
            color = AppTheme.colors.onBackground
        )
        BottomFeedbackDialogButtons(
            modifier = Modifier,
            onCanceled = onCanceled,
            onSubmitted = { onFeedbackSent(feedback) }
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.regular))
    }
}

@Composable
private fun BottomFeedbackDialogButtons(
    modifier: Modifier = Modifier,
    onCanceled: () -> Unit = {},
    onSubmitted: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.offsets.regular
            ),
        horizontalArrangement = Arrangement.spacedBy(
            AppTheme.offsets.medium,
            CenterHorizontally
        )
    ) {
        OutlinedDialogButton(
            modifier = Modifier
                .height(
                    dimensionResource(id = R.dimen.feedback_button_height)
                )
                .weight(1f),
            text = stringResource(R.string.cancel),
            onClick = onCanceled
        )
        FilledDialogButton(
            modifier = Modifier
                .height(
                    dimensionResource(id = R.dimen.feedback_button_height)
                )
                .weight(1f),
            text = stringResource(R.string.submit),
            onClick = onSubmitted
        )
    }
}

private const val FEEDBACK_MAX_SYMBOLS = 600
