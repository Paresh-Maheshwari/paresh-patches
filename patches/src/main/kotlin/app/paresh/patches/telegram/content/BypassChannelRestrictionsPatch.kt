package app.paresh.patches.telegram.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.methodCall
import app.morphe.patcher.patch.bytecodePatch
import app.paresh.patches.telegram.shared.Constants.COMPATIBILITY_TELEGRAM
import com.android.tools.smali.dexlib2.AccessFlags

// Returns restriction text for copyrighted/porn/disabled channels
object GetRestrictionReasonFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "getRestrictionReason",
    returnType = "Ljava/lang/String;",
    parameters = listOf("Ljava/util/ArrayList;"),
)

// Checks if content is sensitive (porn) on MessagesController
object IsSensitiveFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "isSensitive",
    returnType = "Z",
    parameters = listOf("Ljava/util/ArrayList;"),
)

// Shows "can't open" alert dialog
object ShowCantOpenAlertFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "showCantOpenAlert",
    returnType = "V",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
)

// Handles CHANNEL_PRIVATE/USER_BANNED server errors
object CheckChannelErrorFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "checkChannelError",
    returnType = "V",
    parameters = listOf("Ljava/lang/String;", "J"),
)

// Shows 18+ sensitive content dialog before loading messages
object CheckSensitiveFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "checkSensitive",
    returnType = "V",
    parameters = listOf(
        "Lorg/telegram/ui/ActionBar/BaseFragment;",
        "J",
        "Ljava/lang/Runnable;",
        "Ljava/lang/Runnable;",
    ),
)

// Controls sensitive content display toggle
object ShowSensitiveContentFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "showSensitiveContent",
    returnType = "Z",
    parameters = listOf(),
)

// Per-message sensitive check
object MessageObjectIsSensitiveFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessageObject;",
    name = "isSensitive",
    returnType = "Z",
    parameters = listOf(),
)

// Hides sensitive media thumbnails
object MessageObjectIsHiddenSensitiveFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessageObject;",
    name = "isHiddenSensitive",
    returnType = "Z",
    parameters = listOf(),
)

// Creates "no access" alert dialog for restricted channels
object CreateNoAccessAlertFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/ui/Components/AlertsCreator;",
    name = "createNoAccessAlert",
    returnType = "Lorg/telegram/ui/ActionBar/AlertDialog\$Builder;",
)

// loadFullChat error handler that calls checkChannelError
object LoadFullChatErrorFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    returnType = "V",
    parameters = listOf("Lorg/telegram/tgnet/TLRPC\$TL_error;", "J"),
    filters = listOf(
        methodCall(
            definingClass = "Lorg/telegram/messenger/MessagesController;",
            name = "checkChannelError",
        ),
    ),
)

// getChannelDifference error handler that calls checkChannelError
object GetChannelDiffErrorFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    returnType = "V",
    parameters = listOf("Lorg/telegram/tgnet/TLRPC\$TL_error;", "J"),
    filters = listOf(
        methodCall(
            definingClass = "Lorg/telegram/messenger/MessagesController;",
            name = "checkChannelError",
        ),
        methodCall(
            definingClass = "Lorg/telegram/messenger/NotificationCenter;",
            name = "lambda\$postNotificationNameOnUIThread\$1",
        ),
    ),
)

// Force setContentSettings to always enable sensitive content (bypass sensitive_can_change)
object SetContentSettingsFingerprint : Fingerprint(
    definingClass = "Lorg/telegram/messenger/MessagesController;",
    name = "setContentSettings",
    returnType = "V",
    parameters = listOf("Z"),
)

@Suppress("unused")
val bypassChannelRestrictionsPatch = bytecodePatch(
    name = "Bypass channel restrictions",
    description = "Allows opening copyrighted, sensitive, and temporarily disabled channels."
) {
    compatibleWith(COMPATIBILITY_TELEGRAM)

    execute {
        // Force sensitive_enabled=true server-side (bypass sensitive_can_change check)
        SetContentSettingsFingerprint.method.addInstructions(0, """
            const/4 p1, 0x1
        """)

        // No restriction reason = channel accessible
        GetRestrictionReasonFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return-object v0
        """)

        // Suppress "can't open" alert
        ShowCantOpenAlertFingerprint.method.addInstructions(0, "return-void")

        // Skip CHANNEL_PRIVATE/USER_BANNED error handling
        CheckChannelErrorFingerprint.method.addInstructions(0, "return-void")

        // Never flag content as sensitive
        IsSensitiveFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)

        // Skip sensitive content dialog — run success callback directly
        CheckSensitiveFingerprint.method.addInstructions(0, """
            if-eqz p4, :skip
            invoke-interface {p4}, Ljava/lang/Runnable;->run()V
            :skip
            return-void
        """)

        // Always show sensitive content
        ShowSensitiveContentFingerprint.method.addInstructions(0, """
            const/4 v0, 0x1
            return v0
        """)

        // Messages never marked as sensitive
        MessageObjectIsSensitiveFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)

        // Sensitive media always shown
        MessageObjectIsHiddenSensitiveFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return v0
        """)

        // Suppress "no access" alert
        CreateNoAccessAlertFingerprint.method.addInstructions(0, """
            const/4 v0, 0x0
            return-object v0
        """)

        // loadFullChat error → return void
        LoadFullChatErrorFingerprint.method.addInstructions(0, "return-void")

        // getChannelDifference error → return void
        GetChannelDiffErrorFingerprint.methodOrNull?.addInstructions(0, "return-void")
    }
}
