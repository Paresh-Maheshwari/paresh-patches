package app.paresh.patches.truecaller.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

// Targets com.truecaller.premium.data.k.b() — the central isPremium check
// Returns !getBoolean("isPremiumExpired", true)
object IsPremiumFingerprint : Fingerprint(
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        string("isPremiumExpired")
    )
)

// Targets com.truecaller.premium.data.k.S1() — returns the premium tier type
// Reads "premiumLevel" from SharedPreferences, defaults to FREE
object GetPremiumTierFingerprint : Fingerprint(
    returnType = "Lcom/truecaller/premium/data/tier/PremiumTierType;",
    parameters = listOf(),
    filters = listOf(
        string("premiumLevel")
    )
)

// Finds g02.k class via shouldShowAds property
object ShouldShowAdsClassFingerprint : Fingerprint(
    returnType = "Z",
    parameters = listOf(),
    strings = listOf("shouldShowAds", "getShouldShowAds()Z")
)

// Targets g02.k.k(PremiumFeature, boolean) — master feature availability check
object IsFeatureAvailableFingerprint : Fingerprint(
    classFingerprint = ShouldShowAdsClassFingerprint,
    returnType = "Z",
    parameters = listOf("Lcom/truecaller/premium/data/feature/PremiumFeature;", "Z"),
)

// Targets g02.k.f(PremiumFeature) — checks if feature exists in list
object HasFeatureFingerprint : Fingerprint(
    classFingerprint = ShouldShowAdsClassFingerprint,
    returnType = "Z",
    parameters = listOf("Lcom/truecaller/premium/data/feature/PremiumFeature;"),
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
)
