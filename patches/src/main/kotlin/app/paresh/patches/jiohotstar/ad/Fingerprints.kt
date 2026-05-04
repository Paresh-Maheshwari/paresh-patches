package app.paresh.patches.jiohotstar.ad

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

// AdMetadata constructor — controls pre-roll/mid-roll ad flags
object AdMetadataConstructorFingerprint : Fingerprint(
    returnType = "V",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    parameters = listOf("Z", "Z", "Z", "Z", "Z", "Ljava/lang/String;", "Ljava/util/List;"),
    filters = listOf(
        string("ssaiTag"),
        string("perPodPositions")
    )
)

// ExoCorePlayerImpl.E() — Global "is ads enabled" check for the player
object GlobalAdsEnabledFingerprint : Fingerprint(
    definingClass = "Log/g;",
    name = "E",
    returnType = "Z",
    parameters = listOf()
)

// WatchLiveAdsViewModel.b() — Initializes live ad sessions
object WatchLiveAdsInitFingerprint : Fingerprint(
    definingClass = "Lcom/hotstar/ads/watch/WatchLiveAdsViewModel;",
    name = "b",
    returnType = "V",
    parameters = listOf("Lad/l3;", "LQi/u;")
)

// vg/r — Ad manager that constructs AdPlaybackState
object LiveAdManagerFingerprint : Fingerprint(
    definingClass = "Lvg/r;",
    name = "<init>",
    returnType = "V"
)

// bo/m.a — inner composable that renders display ad content (home feed banner ads)
object DisplayAdComposableFingerprint : Fingerprint(
    returnType = "V",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC, AccessFlags.FINAL),
    filters = listOf(
        string("DisplayAd"),
    )
)

// SGAI Prefetcher getAd — fetches live ads from server cache
object SgaiPrefetcherGetAdFingerprint : Fingerprint(
    returnType = "Ljava/lang/Object;",
    filters = listOf(
        string("SGAI_Prefetcher"),
        string("getAd: ")
    )
)

// LiveAdsManager main ad processing loop (Eb.e.f)
object LiveAdsManagerProcessFingerprint : Fingerprint(
    returnType = "Ljava/lang/Object;",
    filters = listOf(
        string("LiveAdsManager"),
        string("Removed stale ad at %d")
    )
)
