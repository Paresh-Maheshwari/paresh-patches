package app.paresh.patches.macrodroid.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

// Central premium status check — confirmation.f.j()
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

// Purchase validation on startup — NewHomeScreenActivity.k3()
object ValidatePurchaseFingerprint : Fingerprint(
    returnType = "V",
    accessFlags = listOf(AccessFlags.PRIVATE, AccessFlags.FINAL),
    parameters = listOf(),
    strings = listOf("Validate purchases is enabled with frequency: ")
)
