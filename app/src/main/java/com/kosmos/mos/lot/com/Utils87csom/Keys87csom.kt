package com.kosmos.mos.lot.com.Utils87csom

import android.util.Base64


const val APPSFLYER_CAMPAIGN_TAG_87csom = "campaign"
const val APPSFLYER_STATUS_TAG_87csom = "af_status"
const val APPSFLYER_ADGROUP_TAG_87csom = "adgroup"
const val APPSFLYER_ADSET_TAG_87csom = "adset"
const val APPSFLYER_AF_CHANNEL_TAG_87csom = "af_channel"
const val APPSFLYER_MEDIA_SOURCE_TAG_87csom = "media_source"

const val FB_DEFAULT_KEY_87csom = "mosdefkey"
const val FB_BLACK_KEY_87csom = "mosblackpage"
const val FB_WHITE_KEY_87csom = "moswhitepage"
const val CODED_ONESIGNAL_87csom = "ZDA1MGI0ZjItN2JiOS00NDZiLWFkNGEtZmExODI4NmQzN2E1"
const val CODE_APPSFLYER_87csom = "Z01FamV4N3hCRG91UGtITUs1R0JLaA=="

fun decodeString87csom (str87csom: String) = String(Base64.decode(str87csom, Base64.DEFAULT))