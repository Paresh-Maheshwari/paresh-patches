package app.paresh.patches.vneditor.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_VN = Compatibility(
        name = "VN - AI Video Editor",
        packageName = "com.frontrow.vlog",
        apkFileType = ApkFileType.APKM,
        appIconColor = 0x00C853,
        targets = listOf(
            AppTarget(version = "2.12.0")
        )
    )
}