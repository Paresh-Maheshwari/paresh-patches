package app.paresh.patches.truecaller.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string

// Targets com.truecaller.premium.data.k.b() — the central isPremium check
object IsPremiumFingerprint : Fingerprint(
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        string("isPremiumExpired")
    )
)

// Targets com.truecaller.premium.data.k.S1() — returns the premium tier type
object GetPremiumTierFingerprint : Fingerprint(
    returnType = "Lcom/truecaller/premium/data/tier/PremiumTierType;",
    parameters = listOf(),
    filters = listOf(
        string("premiumLevel")
    )
)

// Finds g02.k class via its constructor which has unique parameter names
object PremiumFeatureManagerClassFingerprint : Fingerprint(
    returnType = "V",
    strings = listOf("premiumFeaturesInventory", "qaPremiumFeatureHelper")
)

// Targets g02.k.k(PremiumFeature, boolean) — master feature availability check
object IsFeatureAvailableFingerprint : Fingerprint(
    classFingerprint = PremiumFeatureManagerClassFingerprint,
    returnType = "Z",
    parameters = listOf("Lcom/truecaller/premium/data/feature/PremiumFeature;", "Z"),
)
