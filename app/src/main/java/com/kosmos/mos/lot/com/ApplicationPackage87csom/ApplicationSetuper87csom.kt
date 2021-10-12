package com.kosmos.mos.lot.com.ApplicationPackage87csom

import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.kosmos.mos.lot.com.Utils87csom.*
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationSetuper87csom (private val applicationMethods87csom: ApplicaitonContract87csom.ApplicationMethods87csom) : ApplicaitonContract87csom.ApplicationSetupMethods87csom {

    override fun afSetup87csom() {
        val conversionListener87csom = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
                p0?.run {
                    status87csom =
                        if (getValue(APPSFLYER_STATUS_TAG_87csom).toString() == "Organic") "Organic" else "Non-organic"

                    getValue(APPSFLYER_CAMPAIGN_TAG_87csom)
                        .toString()
                        .split("||").drop(1)
                        .map {
                            it.split(":")[1]
                        }.let { list87csom ->
                            key87csom =
                                if (list87csom.isNotEmpty()) list87csom[0] else "empty"
                            sub287csom =
                                if (list87csom.size >= 2) list87csom[1] else "empty"
                            sub387csom =
                                if (list87csom.size >= 3) list87csom[2] else "empty"
                        }


                    mediaSource87csom = getValue(APPSFLYER_MEDIA_SOURCE_TAG_87csom).toString()
                    afChannel87csom = getValue(APPSFLYER_AF_CHANNEL_TAG_87csom).toString()
                    adGroup87csom = getValue(APPSFLYER_ADGROUP_TAG_87csom).toString()
                    adSet87csom = getValue(APPSFLYER_ADSET_TAG_87csom).toString()

                }
            }

            override fun onConversionDataFail(p0: String?) {
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
            }

            override fun onAttributionFailure(p0: String?) {
            }
        }
        AppsFlyerLib.getInstance().run {
            uid87csom = getAppsFlyerUID(applicationMethods87csom.context87csom())
            init(
                decodeString87csom(CODE_APPSFLYER_87csom),
                conversionListener87csom,
                applicationMethods87csom.context87csom()
            )
            start(applicationMethods87csom.context87csom())
        }
    }

    override fun osSetup87csom() {
        OneSignal.initWithContext(applicationMethods87csom.context87csom())
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setAppId(decodeString87csom(CODED_ONESIGNAL_87csom))
    }

    override fun maSetup87csom() {
        MobileAds.initialize(applicationMethods87csom.context87csom())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                AID87csom =
                    AdvertisingIdClient.getAdvertisingIdInfo(applicationMethods87csom.context87csom())?.id
            } catch (e87csom: Exception) {

            }
        }
    }
}