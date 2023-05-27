@file:Suppress("FunctionName", "experimental:function-signature")

package ua.anime.animecraft.ui.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.common.BackButton
import ua.anime.animecraft.ui.common.DownloadButton
import ua.anime.animecraft.ui.common.advanceShadow
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun InfoScreen(
    name: String?,
    backClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        SkinInfoCard(backClicked)
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            text = name ?: "Skin",
            style = AppTheme.typography.headlineSmall.copy(
                color = AppTheme.colors.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
        DownloadButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 16.dp)
                .advanceShadow(
                    borderRadius = 60.dp,
                    blurRadius = 4.dp,
                    color = AppTheme.colors.primary
                )
        ) {}
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SkinInfoCard(backClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.secondary),
        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp, top = 12.dp)
        ) {
            BackButton(backClicked)
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(60.dp),
                painter = painterResource(id = R.drawable.test_picture),
                contentDescription = stringResource(id = R.string.info_skin)
            )
        }
    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    AnimeCraftTheme(darkTheme = true) {
        InfoScreen("Gamer boy") {}
    }
}
