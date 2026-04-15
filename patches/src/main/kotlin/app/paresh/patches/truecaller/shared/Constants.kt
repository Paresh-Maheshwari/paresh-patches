package app.paresh.patches.truecaller.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_TRUECALLER = Compatibility(
        name = "Truecaller",
        packageName = "com.truecaller",
        apkFileType = ApkFileType.APKM,
        appIconColor = 0x1B3A4B,
        targets = listOf(
            AppTarget(version = "26.10.6")
        )
    )
}
