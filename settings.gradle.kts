enableFeaturePreview("GRADLE_METADATA" )

include(":buildSrc")

include(":domain")
include(":localdata", ":localdata:sqldelight" )//, ":localdata:room" )
include(":parsers" )

include(":androiddatabase", ":androiddatabase:sqldelight" )//, ":androiddatabase:room" )
include(":commonandroid" )
include(":dimodules")
include(":player")
include(":preferences")
include(":settings" )

include(":app" )
