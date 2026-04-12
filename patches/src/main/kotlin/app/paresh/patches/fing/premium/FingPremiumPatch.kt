package app.paresh.patches.fing.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.fing.shared.Constants.COMPATIBILITY_FING
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

@Suppress("unused")
val fingPremiumPatch = bytecodePatch(
    name = "Fing Premium",
    description = "Unlocks premium network tools and features."
) {
    compatibleWith(COMPATIBILITY_FING)

    execute {
        // Return PREMIUM tier from subscription check method
        GetSubscriptionTierFingerprint.method.addInstructions(0, """
            sget-object v0, Lfm/r;->c:Lfm/r;
            return-object v0
        """)

        // Patch the account UI to always show PREMIUM status
        // Replace the empty default value for getString("subscription_product_id", "")
        // with "PREMIUM" so the UI always sees premium status
        AccountUIFingerprint.method.apply {
            val subIdIndex = AccountUIFingerprint.instructionMatches[1].index
            val emptyStringIndex = subIdIndex + 1
            val reg = getInstruction<OneRegisterInstruction>(emptyStringIndex).registerA
            replaceInstruction(emptyStringIndex, "const-string v$reg, \"PREMIUM\"")
        }
    }
}
