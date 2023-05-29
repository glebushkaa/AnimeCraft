package ua.anime.animecraft.ad.impl

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.startup.Initializer
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import ua.anime.animecraft.BuildConfig
import ua.anime.animecraft.core.log.debug
import ua.anime.animecraft.core.log.error
import ua.anime.animecraft.core.log.info
import ua.anime.animecraft.core.log.tag
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

class MobileAdsInitializer : Initializer<Unit> {

    @SuppressLint("HardwareIds")
    override fun create(context: Context) {
        runCatching {
            MobileAds.initialize(context) { status ->
                logStatus(status)
                val configuration = RequestConfiguration.Builder()
                if (BuildConfig.DEBUG) {
                    val androidId = Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                    val deviceId = md5(androidId).uppercase()
                    debug(this@MobileAdsInitializer.tag()) { "DeviceId: $deviceId" }
                    configuration.setTestDeviceIds(listOf(deviceId))
                }
                MobileAds.setRequestConfiguration(configuration.build())
            }
        }.onFailure { throwable ->
            error(this@MobileAdsInitializer.tag(), throwable)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    @Suppress("MagicNumber")
    private fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest: MessageDigest = MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest: ByteArray = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices) {
                var h = Integer.toHexString(0xFF and messageDigest[i].toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (ignored: NoSuchAlgorithmException) {
            return ""
        }
    }

    private fun logStatus(status: InitializationStatus) {
        info(this@MobileAdsInitializer.tag()) {
            status.adapterStatusMap.entries.joinToString { entity ->
                val key = entity.key
                val adapterStatus = entity.value
                val description = adapterStatus.description
                val latency = adapterStatus.latency
                val state = adapterStatus.initializationState.name

                "$key (description: $description, latency: $latency, initializationState: $state)"
            }
        }
    }
}