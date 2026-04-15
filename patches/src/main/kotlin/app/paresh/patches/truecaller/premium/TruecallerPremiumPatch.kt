package app.paresh.patches.truecaller.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.truecaller.shared.Constants.COMPATIBILITY_TRUECALLER

@Suppress("unused")
val truecallerPremiumPatch = bytecodePatch(
    name = "Truecaller Premium",
    description = "Unlocks premium features."
) {
    compatibleWith(COMPATIBILITY_TRUECALLER)

    execute {
        // Always return true for isPremium check
        IsPremiumFingerprint.method.addInstructions(0, """
            const/4 v0, 0x1
            return v0
        """)

        // Return GOLD tier instead of FREE
        GetPremiumTierFingerprint.method.addInstructions(0, """
            sget-object v0, Lcom/truecaller/premium/data/tier/PremiumTierType;->GOLD:Lcom/truecaller/premium/data/tier/PremiumTierType;
            return-object v0
        """)

        // All features available — unlocks Max blocking, 140 series, telemarketers etc.
        IsFeatureAvailableFingerprint.method.addInstructions(0, """
            const/4 v0, 0x1
            return v0
        """)
    }
}
