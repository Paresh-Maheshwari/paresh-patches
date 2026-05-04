package app.paresh.patches.jiohotstar.playback

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

// Nj.b.u — 4K resolution support check
object Is4kSupportedFingerprint : Fingerprint(
    definingClass = "LNj/b;",
    name = "u",
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    parameters = listOf("Landroid/content/Context;"),
)

// Nj.b.w — Video codec support check (h265, vp9, etc.)
object IsCodecSupportedFingerprint : Fingerprint(
    definingClass = "LNj/b;",
    name = "w",
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    filters = listOf(
        string("supportsCodec Picked from cache")
    )
)
