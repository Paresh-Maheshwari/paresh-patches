package app.paresh.patches.telegram.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.telegram.shared.Constants.COMPATIBILITY_TELEGRAM

object IsChatNoForwardsLongFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "isChatNoForwards",
    returnType = "Z",
    parameters = listOf("J"),
)

object IsChatNoForwardsChatFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "isChatNoForwards",
    returnType = "Z",
    parameters = listOf("Lorg/telegram/tgnet/TLRPC\$Chat;"),
)

object ChatActivityIsPeerNoForwardsFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/ui/ChatActivity;",
    name = "isPeerNoForwards",
    returnType = "Z",
)

object ProfileActivityIsPeerNoForwardsFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/ui/ProfileActivity;",
    name = "isPeerNoForwards",
    returnType = "Z",
)

@Suppress("unused")
val bypassContentRestrictionPatch = bytecodePatch(
    name = "Bypass content restrictions",
    description = "Allows copying, saving, and screenshots from restricted channels."
) {
    compatibleWith(COMPATIBILITY_TELEGRAM)

    execute {
        // Central noforwards check — allows copy/save/screenshot
        IsChatNoForwardsLongFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)

        IsChatNoForwardsChatFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)

        // Peer noforwards — allows copy/save in chat and profile
        ChatActivityIsPeerNoForwardsFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)

        ProfileActivityIsPeerNoForwardsFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)
    }
}
