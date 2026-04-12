package app.paresh.patches.fing.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.fing.shared.Constants.COMPATIBILITY_FING

@Suppress("unused")
val fingPremiumPatch = bytecodePatch(
    name = "Fing Premium",
    description = "Unlocks premium network tools and features."
) {
    compatibleWith(COMPATIBILITY_FING)

    execute {
        // Return PREMIUM tier from subscription tier method
        GetSubscriptionTierFingerprint.method.addInstructions(0, """
            sget-object v0, Lfm/r;->c:Lfm/r;
            return-object v0
        """)

        // Return true from ServiceActivity.T0() - controls UI premium status
        // This makes the account/plan screen show premium and hides upgrade cards
        HasActiveSubscriptionFingerprint.method.addInstructions(0, """
            const/4 v0, 0x1
            return v0
        """)
    }
}
