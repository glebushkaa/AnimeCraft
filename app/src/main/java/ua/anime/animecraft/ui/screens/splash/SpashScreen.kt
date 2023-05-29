@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

@Suppress("MagicNumber")
@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        delay(1000)
        splashViewModel.showAppOpenAd(
            adUnitId = "ca-app-pub-3940256099942544/3419835294",
            onFinish = onFinish
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_picture),
                contentDescription = stringResource(id = R.string.splash_picture),
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.splash_image_height))
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_large)))
            Text(
                text = stringResource(id = R.string.app_name),
                style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = AppTheme.colors.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    AnimeCraftTheme(darkTheme = true) {
        SplashScreen(onFinish = {})
    }
}
