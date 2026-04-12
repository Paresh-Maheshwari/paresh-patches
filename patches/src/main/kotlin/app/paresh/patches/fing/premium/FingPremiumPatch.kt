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
        // Return PREMIUM tier from subscription check
        GetSubscriptionTierFingerprint.method.addInstructions(0, """
            sget-object v0, Lfm/r;->c:Lfm/r;
            return-object v0
        """)

        // Replace "FREE" with "PREMIUM" in SharedPreferences writer
        // so the UI also shows premium status
        WriteFreeStatusFingerprint.let {
            val freeIndex = it.instructionMatches[1].index
            val reg = it.instructionMatches[1].getInstruction<OneRegisterInstruction>().registerA
            it.method.replaceInstruction(freeIndex, "const-string v$reg, \"PREMIUM\"")
        }
    }
}
