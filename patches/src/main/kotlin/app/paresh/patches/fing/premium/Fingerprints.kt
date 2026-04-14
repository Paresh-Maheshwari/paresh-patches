package app.paresh.patches.fing.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string

// Targets gm/b.d() which reads subscription_product_id from SharedPreferences
// and returns the subscription tier enum (fm/r)
object GetSubscriptionTierFingerprint : Fingerprint(
    returnType = "Lfm/r;",
    parameters = listOf(),
    filters = listOf(
        string("subscription_product_id")
    )
)

// Targets ServiceActivity.T0() — the premium check that gates scan limits
// Returns true if user has premium, false otherwise
object IsPremiumCheckFingerprint : Fingerprint(
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        methodCall("Lfm/p;->C()Lfm/w;"),
        methodCall("Ljava/lang/Enum;->compareTo(Ljava/lang/Enum;)I"),
        methodCall("Lqo/o;->e()Z")
    )
)
