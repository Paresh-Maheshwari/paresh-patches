package app.paresh.patches.macrodroid.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.methodCall
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

// Targets confirmation.f.j() — the central premium status check.
// Returns a$c (PRO) or a$b (NOT_PRO).
// Uses stable non-obfuscated anchors: LiveData.getValue(), a$c field, a$b field.
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
