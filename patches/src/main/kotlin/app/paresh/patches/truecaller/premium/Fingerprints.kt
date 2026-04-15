package app.paresh.patches.truecaller.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

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

// Targets g02.k.f(PremiumFeature) — checks if feature exists
object HasFeatureFingerprint : Fingerprint(
    classFingerprint = PremiumFeatureManagerClassFingerprint,
    returnType = "Z",
    parameters = listOf("Lcom/truecaller/premium/data/feature/PremiumFeature;"),
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
)

// Targets com.truecaller.premium.data.k.m1() — returns premiumExpiresTimestamp
// Default 0 shows as 01/01/1970
object GetExpiryTimestampFingerprint : Fingerprint(
    returnType = "J",
    parameters = listOf(),
    filters = listOf(
        string("premiumExpiresTimestamp")
    )
)

// Targets com.truecaller.premium.data.k.v() — checks if premium data is complete
// Returns true when data is missing → triggers upgrade prompts
object IsPremiumDataIncompleteFingerprint : Fingerprint(
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        string("premiumLastFetchDate"),
        string("premiumLevel"),
        string("premiumKind")
    )
)
