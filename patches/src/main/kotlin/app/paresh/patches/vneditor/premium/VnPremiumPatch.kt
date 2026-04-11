package app.paresh.patches.vneditor.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.vneditor.shared.Constants.COMPATIBILITY_VN

@Suppress("unused")
val vnPremiumPatch = bytecodePatch(
    name = "VN Premium",
    description = "Unlocks premium features in VN Video Editor."
) {
    compatibleWith(COMPATIBILITY_VN)

    execute {
        IsPremiumFingerprint.method.addInstructions(0, """
            const/4 v0, 0x1
            return v0
        """)
    }
}