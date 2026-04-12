package app.paresh.patches.docscanner.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import app.morphe.patcher.methodCall
import com.android.tools.smali.dexlib2.AccessFlags

// Targets the core premium check: l5.c.b()
// This method calls c() (one-time purchase check) then a() (subscription check)
// Both are in the same class which contains product ID strings
object IsPremiumFingerprint : Fingerprint(
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    parameters = listOf(),
    strings = listOf("com.cv.proversion", "com.cv.large_amount"),
    filters = listOf(
        methodCall(returnType = "Z"),
        methodCall(returnType = "Z")
    )
)
