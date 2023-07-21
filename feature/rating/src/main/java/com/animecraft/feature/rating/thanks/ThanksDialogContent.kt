@file:Suppress("FunctionName")

package com.animecraft.feature.rating.thanks

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
import com.anime.animecraft.core.components.buttons.FilledDialogButton
import com.anime.animecraft.core.theme.theme.AppTheme
import com.anime.animecraft.feature.rating.R

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
                shape = AppTheme.shapes.large
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = AppTheme.offsets.large),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.thank_you),
            style = AppTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colors.onBackground
        )
        Image(
            modifier = Modifier.size(
                dimensionResource(id = R.dimen.thanks_image_height)
            ),
            painter = painterResource(id = R.drawable.ic_face_with_heart_eyes),
            contentDescription = stringResource(R.string.thank_you_image)
        )
        Text(
            modifier = Modifier.padding(
                horizontal = AppTheme.offsets.large,
                vertical = AppTheme.offsets.large
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
                .height(
                    dimensionResource(id = R.dimen.thanks_button_height)
                )
                .padding(horizontal = AppTheme.offsets.regular),
            text = stringResource(R.string.you_are_welcome),
            onClick = onDismissRequest
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.regular))
    }
}
