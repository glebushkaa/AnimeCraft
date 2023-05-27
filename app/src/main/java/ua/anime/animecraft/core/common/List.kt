package ua.anime.animecraft.core.common


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun <T> MutableList<T>.replaceAllElements(list: List<T>) {
    clear()
    addAll(list)
}