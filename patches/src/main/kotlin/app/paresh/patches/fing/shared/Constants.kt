package app.paresh.patches.fing.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_FING = Compatibility(
        name = "Fing - Network Tools",
        packageName = "com.overlook.android.fing",
        apkFileType = ApkFileType.APKM,
        appIconColor = 0x0288D1,
        targets = listOf(
            AppTarget(version = "12.11.9")
        )
    )
}
