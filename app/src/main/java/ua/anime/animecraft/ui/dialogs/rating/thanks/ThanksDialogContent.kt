@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.dialogs.rating.thanks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.common.buttons.FilledDialogButton
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme
import ua.anime.animecraft.ui.theme.dialogsShape

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

@Composable
fun ThanksDialogContent(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.surface,
                shape = dialogsShape
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.offset_large)),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.thank_you),
            style = AppTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colors.onBackground
        )
        Image(
            modifier = Modifier.size(140.dp),
            painter = painterResource(id = R.drawable.ic_face_with_heart_eyes),
            contentDescription = stringResource(R.string.thank_you_image)
        )
        Text(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.offset_large),
                vertical = dimensionResource(id = R.dimen.offset_large)
            ),
            text = stringResource(R.string.we_will_enhance_app),
            style = AppTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center,
            color = AppTheme.colors.onBackground
        )
        FilledDialogButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = dimensionResource(id = R.dimen.offset_regular)),
            text = stringResource(R.string.you_are_welcome),
            onClick = onDismissRequest
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_regular)))
    }
}

@Preview
@Composable
fun ThanksDialogPreview() {
    AnimeCraftTheme {
        ThanksDialogContent(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.offset_regular)
            ),
            onDismissRequest = { }
        )
    }
}
