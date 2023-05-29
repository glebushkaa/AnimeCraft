package ua.anime.animecraft.data.files

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import ua.anime.animecraft.R
import ua.anime.animecraft.core.log.error
import ua.anime.animecraft.core.log.tag

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

@Singleton
class SkinFilesHandler @Inject constructor(@ApplicationContext private val context: Context) {

    fun saveSkinToMinecraft(gameImageFileName: String) = runCatching {
        val minecraftDirectory = getExternalStoragePublicDirectory(MINECRAFT_DIRECTORY_PATH)
        val skinFile = File(minecraftDirectory, context.getString(R.string.custom_skin))
        val gameImageFileBytes = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            gameImageFileName
        ).readBytes()
        skinFile.writeBytes(gameImageFileBytes)
    }.onFailure {
        error(it.tag(), it) { it.message ?: "SaveSkinToMinecraft error occurred" }
    }

    fun saveSkinToGallery(gameImageFileName: String) = runCatching {
        val skinFilePath = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            gameImageFileName
        ).path
        val bitmap = BitmapFactory.decodeFile(skinFilePath)
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, gameImageFileName)
            put(MediaStore.MediaColumns.MIME_TYPE, IMAGE_MIME_TYPE)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val imageUri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ) ?: return@runCatching
        resolver.openOutputStream(imageUri)?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, it)
        }
    }.onFailure {
        error(it.tag(), it) { it.message ?: "SaveSkinToGallery error occurred" }
    }

    private companion object {
        const val MINECRAFT_DIRECTORY_PATH = "/games/com.mojang/minecraftpe/"
        const val IMAGE_MIME_TYPE = "image/png"
        const val IMAGE_QUALITY = 100
    }
}
