package app.paresh.patches.macrodroid.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.macrodroid.shared.Constants.COMPATIBILITY_MACRODROID

@Suppress("unused")
val macroDroidPremiumPatch = bytecodePatch(
    name = "MacroDroid Premium",
    description = "Unlocks premium features and removes macro limits."
) {
    compatibleWith(COMPATIBILITY_MACRODROID)

    execute {
        // Bypass signature verification (returns false = signature OK)
        SignatureCheckFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)

        // Return PRO status from the central premium check
        PremiumStatusFingerprint.method.addInstructions(0, """
            sget-object v0, Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;->a:Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;
            return-object v0
        """)

        // Skip purchase validation on startup
        ValidatePurchaseFingerprint.method.addInstructions(0, "return-void")
    }
}
