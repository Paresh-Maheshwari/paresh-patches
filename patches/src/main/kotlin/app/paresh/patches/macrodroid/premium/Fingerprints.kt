package app.paresh.patches.macrodroid.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

// Targets confirmation.f.j() — the central premium status check.
// Returns a$c (PRO) or a$b (NOT_PRO).
object PremiumStatusFingerprint : Fingerprint(
    returnType = "Lcom/arlosoft/macrodroid/confirmation/a;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    filters = listOf(
        methodCall(definingClass = "Landroidx/lifecycle/LiveData;", name = "getValue"),
        fieldAccess(opcode = Opcode.SGET_OBJECT, definingClass = "Lcom/arlosoft/macrodroid/confirmation/a\$c;", name = "a"),
        fieldAccess(opcode = Opcode.SGET_OBJECT, definingClass = "Lcom/arlosoft/macrodroid/confirmation/a\$b;", name = "a")
    )
)

// Targets NewHomeScreenActivity.k3() — the purchase validation on startup.
// Runs when pro, contacts Google Play to verify purchase, shows error dialog on failure.
object ValidatePurchaseFingerprint : Fingerprint(
    returnType = "V",
    accessFlags = listOf(AccessFlags.PRIVATE, AccessFlags.FINAL),
    parameters = listOf(),
    strings = listOf("Validate purchases is enabled with frequency: ")
)
