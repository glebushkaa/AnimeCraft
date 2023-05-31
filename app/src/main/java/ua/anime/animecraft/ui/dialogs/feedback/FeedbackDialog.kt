@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.dialogs.feedback

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.common.FilledDialogButton
import ua.anime.animecraft.ui.common.OutlinedDialogButton
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme
import ua.anime.animecraft.ui.theme.AppTheme.dialogProperties
import ua.anime.animecraft.ui.theme.dialogButtonShape
import ua.anime.animecraft.ui.theme.dialogsShape


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023.
 */

@Composable
fun FeedbackDialog(
    onDismissRequest: () -> Unit = {},
    onFeedbackSent: (String) -> Unit = {},
    onCanceled: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest, properties = dialogProperties) {
        FeedbackDialogContent(
            onFeedbackSent = onFeedbackSent,
            onCanceled = onCanceled
        )
    }
}

@Composable
private fun FeedbackDialogContent(
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
                shape = dialogsShape
            )
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.offset_large))
                .align(CenterHorizontally),
            text = stringResource(R.string.sorry_to_hear),
            style = AppTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colors.onBackground
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 160.dp)
                .padding(horizontal = dimensionResource(id = R.dimen.offset_regular))
                .border(
                    width = 2.dp,
                    color = AppTheme.colors.primary,
                    shape = dialogButtonShape
                ),
            value = feedback,
            onValueChange = { feedback = it },
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
            modifier = Modifier.padding(dimensionResource(id = R.dimen.offset_regular)),
            text = stringResource(R.string.please_provide_your_thoughts),
            style = AppTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = AppTheme.colors.onBackground
        )
        BottomFeedbackDialogButtons(
            modifier = Modifier,
            onCanceled = onCanceled,
            onSubmitted = { onFeedbackSent(feedback) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_regular)))
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
                horizontal = dimensionResource(id = R.dimen.offset_regular)
            ),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.offset_medium),
            CenterHorizontally
        )
    ) {
        OutlinedDialogButton(
            modifier = Modifier
                .height(40.dp)
                .weight(1f),
            text = stringResource(R.string.cancel),
            onClick = onCanceled
        )
        FilledDialogButton(
            modifier = Modifier
                .height(40.dp)
                .weight(1f),
            text = stringResource(R.string.submit),
            onClick = onSubmitted
        )
    }
}

@Preview
@Composable
fun FeedbackDialogPreview() {
    AnimeCraftTheme(darkTheme = true) {
        FeedbackDialogContent(onFeedbackSent = {}) {

        }
    }
}