package ua.animecraft.feature.download.skin

import com.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.domain.usecase.preferences.UpdateDownloadDialogDisabledUseCase
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class DownloadViewModel @Inject constructor(
    private val updateDownloadDialogDisabledUseCase: UpdateDownloadDialogDisabledUseCase
) : AnimeCraftViewModel() {

    
}