package ua.animecraft.feature.rating

import com.google.errorprone.annotations.Immutable

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

@Immutable
enum class RatingDialogMode {
    RATING,
    FEEDBACK,
    THANKS
}
