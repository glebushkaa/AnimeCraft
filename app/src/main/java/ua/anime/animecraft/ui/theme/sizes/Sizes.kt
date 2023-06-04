package ua.anime.animecraft.ui.theme.sizes

import ua.anime.animecraft.ui.theme.sizes.dialogs.DialogContentSizes
import ua.anime.animecraft.ui.theme.sizes.items.ItemsSizes
import ua.anime.animecraft.ui.theme.sizes.screens.ScreenSizes

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

data class Sizes(
    val generic: GenericSizes = GenericSizes(),
    val dialogs: DialogContentSizes = DialogContentSizes(),
    val screens: ScreenSizes = ScreenSizes(),
    val items: ItemsSizes = ItemsSizes()
)
