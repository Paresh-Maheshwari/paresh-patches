package app.paresh.patches.truecaller.premium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string

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
