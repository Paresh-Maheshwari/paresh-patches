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
        val sigBypass = """
            const/4 v0, 0x0
            return v0
        """

        // Bypass main signature check
        SignatureCheckFingerprint.method.addInstructions(0, sigBypass)

        // Bypass template store signature check
        TemplateStoreSignatureCheckFingerprint.method.addInstructions(0, sigBypass)

        // Return PRO status
        PremiumStatusFingerprint.method.addInstructions(0, """
            sget-object v0, Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;->a:Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;
            return-object v0
        """)

        // Skip purchase validation on startup
        ValidatePurchaseFingerprint.method.addInstructions(0, "return-void")

        // Block upgrade screen
        ShowUpgradeScreenFingerprint.method.addInstructions(0, "return-void")
    }
}
