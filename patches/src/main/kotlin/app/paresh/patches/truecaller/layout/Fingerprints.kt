package app.paresh.patches.truecaller.layout

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.string

// Targets hc2.c.a() — scam feed manager isEnabled check
// Calls hc2.a.a() and hc2.baz.a() interfaces
object ScamFeedEnabledFingerprint : Fingerprint(
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        methodCall(definingClass = "Lhc2/a;", name = "a"),
        methodCall(definingClass = "Lhc2/baz;", name = "a")
    )
)

// Targets gg1.c.w() — call assistant feature toggle
// Checks "featureCallAssistant" feature flag
object AssistantFeatureFingerprint : Fingerprint(
    returnType = "Z",
    parameters = listOf(),
    filters = listOf(
        string("featureCallAssistant")
    )
)
