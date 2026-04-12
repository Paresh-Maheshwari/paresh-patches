package app.paresh.patches.fing.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string

// Targets gm/b.d() which reads subscription_product_id and returns tier enum
object GetSubscriptionTierFingerprint : Fingerprint(
    returnType = "Lfm/r;",
    parameters = listOf(),
    filters = listOf(
        string("subscription_product_id")
    )
)

// Targets qo/o.f(Context, u) which writes "FREE" to SharedPreferences
object WriteFreeStatusFingerprint : Fingerprint(
    returnType = "V",
    filters = listOf(
        string("subscription_product_id"),
        string("FREE")
    )
)
