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

// Signature verification — com.arlosoft.macrodroid.a.a(Context)
// Returns true if signature doesn't match known keys (= tampered).
object SignatureCheckFingerprint : Fingerprint(
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    parameters = listOf("Landroid/content/Context;"),
    filters = listOf(
        methodCall(definingClass = "Landroid/content/pm/PackageManager;", name = "getPackageInfo"),
        methodCall(definingClass = "Landroid/content/pm/Signature;", name = "toCharsString")
    )
)
