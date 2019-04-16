package studio.forface.freshtv.domain.gateways

/**
 * @author Davide Giuseppe Farella
 * A class for manage app's Settings
 */
interface AppSettings {

    /** A [Boolean] representing whether the night mode is enabled */
    var nightMode: Boolean

    /** The day before an old Guide will be deleted */
    var oldGuidesLifespanDays: Long

    // TODO enum class UiMode { Light, Dark, System, Battery, Schedule }
}