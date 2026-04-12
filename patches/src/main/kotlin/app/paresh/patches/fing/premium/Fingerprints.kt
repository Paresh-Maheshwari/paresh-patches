package app.paresh.patches.fing.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

object GetSubscriptionTierFingerprint : Fingerprint(
    returnType = "Lfm/r;",
    parameters = listOf(),
    filters = listOf(
        string("subscription_product_id")
    )
)

object WriteFreeStatusFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "V",
    filters = listOf(
        string("subscription_product_id"),
        string("FREE")
    )
)
