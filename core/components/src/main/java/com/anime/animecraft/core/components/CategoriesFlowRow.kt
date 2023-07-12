@file:Suppress("FunctionName")
@file:OptIn(ExperimentalLayoutApi::class, ExperimentalAnimationApi::class)

package com.anime.animecraft.core.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.anime.animecraft.core.components.R
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.model.Category

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

@Composable
fun CategoriesFlowRow(
    categories: List<Category>,
    selectedCategory: Category? = null,
    onCategorySelected: (Category) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(
            AppTheme.offsets.small,
            Alignment.Start
        )
    ) {
        categories.forEach {
            AnimatedContent(
                targetState = it.id == selectedCategory?.id,
                transitionSpec = {
                    scaleIn(enterScaleAnimationSpec) with scaleOut(exitScaleAnimationSpec)
                }
            ) { selected ->
                CategoryItem(
                    name = it.name,
                    onClick = { onCategorySelected(it) },
                    selected = selected
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    name: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier
            .padding(bottom = AppTheme.offsets.small)
            .height(
                dimensionResource(id = R.dimen.category_item_height)
            ),
        contentPadding = PaddingValues(
            vertical = AppTheme.offsets.tiny,
            horizontal = AppTheme.offsets.small
        ),
        onClick = onClick,
        border = BorderStroke(AppTheme.strokes.tiny, AppTheme.colors.primary),
        shape = AppTheme.shapes.small,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) {
                AppTheme.colors.tertiary
            } else {
                AppTheme.colors.surfaceVariant
            }
        )
    ) {
        Text(
            text = produceCategoryText(name),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.primary
        )
    }
}

private fun produceCategoryText(name: String) = "#${name.uppercase()}"

private const val ENTER_CATEGORY_ANIMATION_DURATION = 400
private const val EXIT_CATEGORY_ANIMATION_DURATION = 200

private val enterScaleAnimationSpec = tween<Float>(
    durationMillis = ENTER_CATEGORY_ANIMATION_DURATION
)
private val exitScaleAnimationSpec = tween<Float>(
    durationMillis = EXIT_CATEGORY_ANIMATION_DURATION
)
