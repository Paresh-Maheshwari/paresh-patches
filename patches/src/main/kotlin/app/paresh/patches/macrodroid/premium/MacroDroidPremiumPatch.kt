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
        // Override j() to always return a$c (PRO status singleton)
        PremiumStatusFingerprint.method.addInstructions(0, """
            sget-object v0, Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;->a:Lcom/arlosoft/macrodroid/confirmation/a${'$'}c;
            return-object v0
        """)
    }
}
