package app.paresh.patches.fing.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string

// Subscription tier getter — reads subscription_product_id from SharedPreferences
object GetSubscriptionTierFingerprint : Fingerprint(
    returnType = "Lfm/r;",
    parameters = listOf(),
    filters = listOf(
        string("subscription_product_id")
    )
)

// Premium status check — ServiceActivity.T0()
// Controls scan limits and upgrade UI visibility
object PremiumStatusCheckFingerprint : Fingerprint(
    definingClass = "Lcom/overlook/android/fing/ui/base/ServiceActivity;",
    name = "T0",
    returnType = "Z",
    parameters = listOf()
)
