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

// Main signature check — com.arlosoft.macrodroid.a.a(Context)
object SignatureCheckFingerprint : Fingerprint(
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    parameters = listOf("Landroid/content/Context;"),
    filters = listOf(
        methodCall(definingClass = "Landroid/content/pm/PackageManager;", name = "getPackageInfo"),
        methodCall(definingClass = "Landroid/content/pm/Signature;", name = "toCharsString")
    )
)

// Template store class — find via API key
object TemplateStoreClassFingerprint : Fingerprint(
    strings = listOf("adb97ac6-f780-4a41-8475-ce661b574999")
)

// Template store signature check — l5/t.w(Context)
object TemplateStoreSignatureCheckFingerprint : Fingerprint(
    classFingerprint = TemplateStoreClassFingerprint,
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PRIVATE, AccessFlags.FINAL),
    parameters = listOf("Landroid/content/Context;"),
    filters = listOf(
        methodCall(definingClass = "Landroid/content/pm/PackageManager;", name = "getPackageInfo"),
        methodCall(definingClass = "Landroid/content/pm/Signature;", name = "toCharsString")
    )
)

// Signature hash generator — r1.a(Context) returns SHA1+Base64 of APK signature
// Used as "device ID" in template store API hash computation.
object SignatureHashFingerprint : Fingerprint(
    definingClass = "Lcom/arlosoft/macrodroid/utils/r1;",
    name = "a",
    returnType = "Ljava/lang/String;",
    parameters = listOf("Landroid/content/Context;")
)

// Upgrade screen launcher — UpgradeActivity2$a.a(Activity)
object ShowUpgradeScreenFingerprint : Fingerprint(
    definingClass = "Lcom/arlosoft/macrodroid/upgrade/UpgradeActivity2\$a;",
    name = "a",
    returnType = "V",
    parameters = listOf("Landroid/app/Activity;")
)
