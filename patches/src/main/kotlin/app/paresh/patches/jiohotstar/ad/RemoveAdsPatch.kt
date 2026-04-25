package app.paresh.patches.jiohotstar.ad

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.jiohotstar.shared.Constants.COMPATIBILITY_JIOHOTSTAR

@Suppress("unused")
val removeAdsPatch = bytecodePatch(
    name = "Remove ads",
    description = "Removes pre-roll and mid-roll video ads."
) {
    compatibleWith(COMPATIBILITY_JIOHOTSTAR)

    execute {
        // Force: disablePreRoll=true(p1), disableMidRoll=true(p2),
        //        hasPreRoll=false(p3), hasPlayablePreRoll=false(p4), enableMidRollLoad=false(p5)
        AdMetadataConstructorFingerprint.method.addInstructions(0, """
            const/4 p1, 0x1
            const/4 p2, 0x1
            const/4 p3, 0x0
            const/4 p4, 0x0
            const/4 p5, 0x0
        """)

        // Hide home feed banner/display ads
        DisplayAdComposableFingerprint.method.addInstructions(0, """
            return-void
        """)
    }
}
