package app.paresh.patches.fing.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import app.morphe.patcher.fieldAccess
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object GetSubscriptionTierFingerprint : Fingerprint(
    returnType = "Lfm/r;",
    parameters = listOf(),
    filters = listOf(
        string("subscription_product_id")
    )
)

// Targets ServiceActivity.T0() which checks if user has active subscription
// It reads fm/w.d field and compares to fm/r.b (STARTER tier)
object HasActiveSubscriptionFingerprint : Fingerprint(
    definingClass = "Lcom/overlook/android/fing/ui/base/ServiceActivity;",
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    filters = listOf(
        fieldAccess(
            definingClass = "Lfm/w;",
            type = "Lfm/r;"
        )
    )
)
