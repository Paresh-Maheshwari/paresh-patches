package app.paresh.patches.telegram.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.telegram.shared.Constants.COMPATIBILITY_TELEGRAM
import com.android.tools.smali.dexlib2.AccessFlags

// Block database deletion (both overloads)
object MarkMessagesAsDeletedFingerprint1 : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesStorage;",
    name = "markMessagesAsDeleted",
    returnType = "Ljava/util/ArrayList;",
    parameters = listOf("J", "I", "Z", "Z"),
)

object MarkMessagesAsDeletedFingerprint2 : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesStorage;",
    name = "markMessagesAsDeleted",
    returnType = "Ljava/util/ArrayList;",
    parameters = listOf("J", "Ljava/util/ArrayList;", "Z", "Z", "I", "I"),
)

// Block server-push deletion entirely (prevents both UI removal and DB deletion)
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
        // Block database deletion
        listOf(MarkMessagesAsDeletedFingerprint1, MarkMessagesAsDeletedFingerprint2).forEach {
            it.method.addInstructions(0, """
                const/4 v0, 0x0
                return-object v0
            """)
        }

        // Block server-push deletion path entirely
        DeleteMessagesByPushFingerprint.method.addInstructions(0, "return-void")
    }
}
