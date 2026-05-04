package app.paresh.patches.jiohotstar.playback

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

// Nj.b.n — HDR10+ support check (uses constant 4 for profile level)
object IsHdr10PlusSupportedFingerprint : Fingerprint(
    definingClass = "LNj/b;",
    name = "n",
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    parameters = listOf("Landroid/content/Context;", "Z", "I", "Z"),
)

// Nj.b.o — HDR10 support check (uses constant 2 for profile level)
object IsHdr10SupportedFingerprint : Fingerprint(
    definingClass = "LNj/b;",
    name = "o",
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    parameters = listOf("Landroid/content/Context;", "Z", "I", "Z"),
)
