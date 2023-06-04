@file:Suppress("FunctionName", "MagicNumber")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.sendReport
import ua.anime.animecraft.core.common.HALF_SECOND
import ua.anime.animecraft.core.common.ONE_SECOND
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.buttons.BackButton
import ua.anime.animecraft.ui.common.buttons.FilledDialogButton
import ua.anime.animecraft.ui.dialogs.rating.ThanksDialog
import ua.anime.animecraft.ui.navigation.LANGUAGE_SETTINGS
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/1/2023
 */

@Composable
fun ReportScreen(
    onBackClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var report by rememberSaveable { mutableStateOf("") }
    var thanksDialogShown by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                currentScreen = LANGUAGE_SETTINGS
            )
        },
        content = {
            ReportScreenContent(
                modifier = Modifier.padding(
                    start = AppTheme.offsets.regular,
                    end = AppTheme.offsets.regular,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                onSendClicked = {
                    scope.launch {
                        context.sendReport(report)
                        delay(ONE_SECOND)
                        report = ""
                        thanksDialogShown = true
                    }
                },
                onBackClicked = onBackClicked,
                onReportChanged = { value -> report = value },
                report = report
            )
        }
    )

    if (thanksDialogShown) {
        ThanksDialog {
            scope.launch {
                thanksDialogShown = false
                delay(HALF_SECOND)
                onBackClicked()
            }
        }
    }
}

@Composable
private fun ReportScreenContent(
    modifier: Modifier = Modifier,
    onSendClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
    onReportChanged: (String) -> Unit = {},
    report: String
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BackButton(onBackClicked)
        Text(
            modifier = Modifier.padding(
                top = AppTheme.offsets.average
            ),
            text = stringResource(id = R.string.report),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.huge))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = AppTheme.sizes.screens.report.minTextFieldHeight)
                .border(
                    width = AppTheme.strokes.small,
                    color = AppTheme.colors.primary,
                    shape = AppTheme.shapes.medium
                ),
            value = report,
            onValueChange = onReportChanged,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = AppTheme.colors.primary,
                textColor = AppTheme.colors.primary,
                focusedBorderColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.tell_us_your_report),
                    style = AppTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = AppTheme.colors.onBackground.copy(0.6f)
                )
            }
        )
        Spacer(modifier = Modifier.height(AppTheme.offsets.large))
        FilledDialogButton(
            modifier = Modifier
                .height(AppTheme.sizes.screens.report.buttonHeight)
                .fillMaxWidth(),
            text = stringResource(id = R.string.send_report),
            onClick = onSendClicked,
            textStyle = AppTheme.typography.bodyLargeBold.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}
