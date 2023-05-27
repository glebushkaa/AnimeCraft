@file:Suppress("LongMethod", "FunctionName")

package ua.anime.animecraft.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.R
import ua.anime.animecraft.SETTINGS
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.BackButton
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/15/2023.
 */

private val testLanguageList = listOf(
    Language(0, R.string.english),
    Language(1, R.string.ukrainian),
    Language(2, R.string.russian)
)

@Composable
fun SettingsScreen(
    backClicked: () -> Unit
) {
    var newSkinsNotification by rememberSaveable { mutableStateOf(false) }
    var darkMode by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_regular)))
            AppTopBar(currentScreen = SETTINGS)
            BackButton(backClicked)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.settings),
                style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_huge)))
            Row(
                modifier = Modifier
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier.size(24.dp),
                    checked = newSkinsNotification,
                    onCheckedChange = { newSkinsNotification = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = AppTheme.colors.primary,
                        uncheckedColor = AppTheme.colors.primary,
                        checkmarkColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.offset_regular)))
                Text(
                    text = stringResource(id = R.string.notifications_new_skins),
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_medium)))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier.size(24.dp),
                    checked = darkMode,
                    onCheckedChange = { darkMode = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = AppTheme.colors.primary,
                        uncheckedColor = AppTheme.colors.primary,
                        checkmarkColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.offset_regular)))
                Text(
                    text = stringResource(id = R.string.dark_mode),
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_average)))
            LanguageSection(list = testLanguageList)
        }
    }
}

@Composable
fun LanguageSection(list: List<Language>) {
    var selectedItemId by rememberSaveable { mutableStateOf(0) }
    Column {
        Text(
            text = stringResource(id = R.string.language),
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(content = {
            items(list) {
                LanguageItem(language = it, selected = it.id == selectedItemId) { id ->
                    selectedItemId = id
                }
            }
        }, verticalArrangement = Arrangement.spacedBy(8.dp))
    }
}

@Composable
fun LanguageItem(language: Language, selected: Boolean, onClick: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.size(24.dp),
            selected = selected,
            onClick = { onClick(language.id) },
            colors = RadioButtonDefaults.colors(
                selectedColor = AppTheme.colors.primary,
                unselectedColor = AppTheme.colors.primary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = language.resId),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onBackground
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    AnimeCraftTheme {
        SettingsScreen {}
    }
}

@Preview
@Composable
fun LanguageSectionPreview() {
    AnimeCraftTheme(darkTheme = true) {
        LanguageSection(testLanguageList)
    }
}

data class Language(
    val id: Int,
    val resId: Int
)
