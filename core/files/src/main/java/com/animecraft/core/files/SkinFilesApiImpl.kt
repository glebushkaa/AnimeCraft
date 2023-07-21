package com.animecraft.core.files

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.animecraft.core.domain.files.SkinFilesApi
import com.animecraft.core.log.error
import com.animecraft.core.log.tag
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

@Singleton
class SkinFilesApiImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SkinFilesApi {

    override fun saveSkinToMinecraft(fileName: String) = runCatching {
        val minecraftDir = getExternalStoragePublicDirectory(MINECRAFT_DIRECTORY_PATH)
        val file = File(minecraftDir, MINECRAFT_SKIN_FILE_NAME)
        val data = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            fileName
        ).inputStream().readBytes()
        val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, it)
        }
        Unit
    }.onFailure {
        error(it.tag(), it) {
            it.message ?: "SaveSkinToMinecraft error occurred"
        }
    }

    override fun saveSkinToGallery(fileName: String) = runCatching {
        val skinFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            fileName
        ).path
        val bitmap = BitmapFactory.decodeFile(skinFile)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            saveToGalleryHigherAndroidQ(fileName, bitmap)
        } else {
            saveToGalleryLowerAndroidQ(fileName, bitmap)
        }
    }.onFailure {
        error(it.tag(), it) {
            it.message ?: "SaveSkinToGallery error occurred"
        }
    }

    private fun saveToGalleryLowerAndroidQ(gameImageFileName: String, bitmap: Bitmap) {
        FileOutputStream(getPictureDir(gameImageFileName)).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveToGalleryHigherAndroidQ(gameImageFileName: String, bitmap: Bitmap) {
        val resolver = context.contentResolver
        val contentValues = getContentValues(gameImageFileName)
        val imageUri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ) ?: return
        resolver.openOutputStream(imageUri)?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getContentValues(gameImageFileName: String) = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, gameImageFileName)
        put(MediaStore.MediaColumns.MIME_TYPE, IMAGE_MIME_TYPE)
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    private fun getPictureDir(fileName: String): File {
        return File(
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            fileName
        )
    }

    private companion object {
        const val MINECRAFT_SKIN_FILE_NAME = "custom.png"
        const val MINECRAFT_DIRECTORY_PATH =
            "Android/data/com.mojang.minecraftpe/files/games/com.mojang/minecraftpe"
        const val IMAGE_MIME_TYPE = "image/png"
        const val IMAGE_QUALITY = 100
    }
}
