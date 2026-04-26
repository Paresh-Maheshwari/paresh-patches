package app.paresh.patches.telegram.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.telegram.shared.Constants.COMPATIBILITY_TELEGRAM
import com.android.tools.smali.dexlib2.AccessFlags

// Block server-push deletion (incoming deletes from other users)
object DeleteMessagesByPushFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "deleteMessagesByPush",
    returnType = "V",
    accessFlags = listOf(AccessFlags.PROTECTED),
    parameters = listOf("J", "Ljava/util/ArrayList;", "J"),
)

@Suppress("unused")
val antiDeleteMessagesPatch = bytecodePatch(
    name = "Anti-delete messages",
    description = "Prevents deleted messages from being removed locally."
) {
    compatibleWith(COMPATIBILITY_TELEGRAM)

    execute {
        // Only block server-push deletion path (incoming deletes)
        // User-initiated deletes go through deleteMessages() which is not patched
        DeleteMessagesByPushFingerprint.method.addInstructions(0, "return-void")
    }
}
