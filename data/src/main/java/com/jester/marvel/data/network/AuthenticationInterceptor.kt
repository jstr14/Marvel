package com.jester.marvel.data.network

import com.jester.data.marvel.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class AuthenticationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        val original = chain!!.request()
        val originalHttpUrl = original.url()

        val privateKey = BuildConfig.PRIVATE_KEY
        val publicKey = BuildConfig.PUBLIC_KEY
        val ts = System.currentTimeMillis().toString()

        val hash = getMD5FromApiKey(ts+privateKey+publicKey)

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", publicKey)
                .addQueryParameter("hash",hash)
                .addQueryParameter("ts",ts)
                .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    fun getMD5FromApiKey(stringToHash: String): String {
        val digest: MessageDigest
        try {
            digest = MessageDigest.getInstance("MD5")
            digest.update(stringToHash.toByteArray(Charset.forName("US-ASCII")), 0, stringToHash.length)
            val magnitude = digest.digest()
            val bi = BigInteger(1, magnitude)
            return String.format("%0" + (magnitude.size shl 1) + "x", bi)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}