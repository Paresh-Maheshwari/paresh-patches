package app.paresh.patches.fing.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import app.morphe.patcher.methodCall
import com.android.tools.smali.dexlib2.AccessFlags

// Targets gm/b.d() - returns subscription tier enum
object GetSubscriptionTierFingerprint : Fingerprint(
    returnType = "Lfm/r;",
    parameters = listOf(),
    filters = listOf(
        string("subscription_product_id")
    )
)

// Targets fm/p.H() - the UI premium status check
// Called by the account/plan screen to decide premium vs free UI
// Uses synchronized block and checks internal state fields
object HasSubscriptionFingerprint : Fingerprint(
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    filters = listOf(
        methodCall(definingClass = "Lfm/p;", name = "G")
    )
)
