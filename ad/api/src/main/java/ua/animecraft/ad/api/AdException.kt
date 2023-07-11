package ua.animecraft.ad.api

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

class AdException(
    override val message: String,
    private val domain: String,
    private val code: Int
) : Exception(message) {
    override fun toString(): String {
        return "domain: $domain, code: $code, message: $message"
    }
}
