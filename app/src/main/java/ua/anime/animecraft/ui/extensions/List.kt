package ua.anime.animecraft.ui.extensions

import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun List<Skin>.filterListByName(name: String) = filter {
    it.name.startsWith(name) || it.name.contains(name)
}
