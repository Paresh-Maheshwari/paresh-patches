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
        // Return PREMIUM tier enum constant
        GetSubscriptionTierFingerprint.method.addInstructions(0, """
            sget-object v0, Lfm/r;->c:Lfm/r;
            return-object v0
        """)

        // Bypass scan limit check — always return true for premium status
        IsPremiumCheckFingerprint.method.addInstructions(0, """
            const/4 v0, 0x1
            return v0
        """)

        // All features entitled — always return true
        IsFeatureEntitledFingerprint.method.addInstructions(0, """
            const/4 v0, 0x1
            return v0
        """)
    }
}
