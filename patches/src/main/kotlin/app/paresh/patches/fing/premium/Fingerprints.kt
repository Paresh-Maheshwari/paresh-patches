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

// Targets the UI method in rn/d that reads subscription_product_id
// from SharedPreferences and shows/hides upgrade buttons
object AccountUIFingerprint : Fingerprint(
    returnType = "V",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    filters = listOf(
        string("uiprefs"),
        string("subscription_product_id"),
        string("PREMIUM")
    )
)
