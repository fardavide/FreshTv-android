package studio.forface.freshtv.domain.gateways

/**
 * @author Davide Giuseppe Farella
 * A class for manage app's Settings
 */
interface AppSettings {

    /** The day before an old Guide will be deleted */
    var oldGuidesLifespanDays: Int

}