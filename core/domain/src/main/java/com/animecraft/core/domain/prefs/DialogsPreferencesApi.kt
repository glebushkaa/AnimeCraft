package com.animecraft.core.domain.prefs

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

interface DialogsPreferencesApi {

    fun isDownloadDialogDisabled(): Boolean

    fun setDownloadDialogDisabled(isDisabled: Boolean)

    fun isRateDialogDisabled(): Boolean

    fun setRateDialogDisabled(isDisabled: Boolean)

    fun isRateCompleted(): Boolean

    fun setRateCompleted(isCompleted: Boolean)
}