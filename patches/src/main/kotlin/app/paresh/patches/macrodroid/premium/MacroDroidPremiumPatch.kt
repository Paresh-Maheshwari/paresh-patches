package app.paresh.patches.macrodroid.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.macrodroid.shared.Constants.COMPATIBILITY_MACRODROID

@Suppress("unused")
val macroDroidPremiumPatch = bytecodePatch(
    name = "MacroDroid Premium",
    description = "Unlocks premium features and removes macro limits."
) {
    compatibleWith(COMPATIBILITY_MACRODROID)

    execute {
        // Bypass ALL signature verification methods (main + template store)
        Fingerprint(
            returnType = "Z",
            parameters = listOf("Landroid/content/Context;"),
            filters = listOf(
                methodCall(definingClass = "Landroid/content/pm/PackageManager;", name = "getPackageInfo"),
                methodCall(definingClass = "Landroid/content/pm/Signature;", name = "toCharsString")
            )
        ).matchAllOrNull()?.forEach { match ->
            match.method.addInstructions(0, """
                const/4 v0, 0x0
                return v0
            """)
        }

        // Return PRO status from the central premium check
        PremiumStatusFingerprint.method.addInstructions(0, """
            sget-object v0, Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;->a:Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;
            return-object v0
        """)

        // Skip purchase validation on startup
        ValidatePurchaseFingerprint.method.addInstructions(0, "return-void")
    }
}
