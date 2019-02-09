package studio.forface.freshtv.settings.helper

/** Equivalent to [Settings.hasKey] */
internal operator fun Settings.contains( key: String ): Boolean = hasKey( key )

/** Equivalent to [Settings.remove]*/
internal operator fun Settings.minusAssign( key: String ) {
    remove( key )
}

/** Equivalent to [Settings.getInt]*/
internal operator fun Settings.get( key: String, defaultValue: Int ) = getInt( key, defaultValue )

/** Equivalent to [Settings.getLong]*/
internal operator fun Settings.get( key: String, defaultValue: Long ) = getLong( key, defaultValue )

/** Equivalent to [Settings.getString]*/
internal operator fun Settings.get( key: String, defaultValue: String ) = getString( key, defaultValue )

/** Equivalent to [Settings.getFloat]*/
internal operator fun Settings.get( key: String, defaultValue: Float ) = getFloat( key, defaultValue )

/** Equivalent to [Settings.getDouble]*/
internal operator fun Settings.get( key: String, defaultValue: Double ) = getDouble( key, defaultValue )

/** Equivalent to [Settings.getBoolean]*/
internal operator fun Settings.get( key: String, defaultValue: Boolean ) = getBoolean( key, defaultValue )

/**
 * @return NOT NULL [V].
 * Find the proper getter by the reified [V]
 */
internal inline operator fun <reified V: Any> Settings.get( key: String, defaultValue: V ): V {
    return when ( V::class ) {
        Int::class ->       getInt( key, defaultValue as Int ) as V
        Long::class ->      getLong( key, defaultValue as Long ) as V
        String::class ->    getString( key, defaultValue as String ) as V
        Float::class ->     getFloat( key, defaultValue as Float ) as V
        Double::class ->    getDouble( key, defaultValue as Double ) as V
        Boolean::class ->   getBoolean( key, defaultValue as Boolean ) as V
        else -> throw IllegalArgumentException( "Type '${V::class.qualifiedName}' is not a supported type" )
    }
}

/**
 * @return NULLABLE [V].
 * Find the proper getter by the reified [V]
 */
internal inline operator fun <reified V: Any?> Settings.get( key: String ): V? {
    if ( key !in this ) return null
    return when ( V::class ) {
        Int::class ->       getInt( key ) as V
        Long::class ->      getLong( key ) as V
        String::class ->    getString( key ) as V
        Float::class ->     getFloat( key ) as V
        Double::class ->    getDouble( key ) as V
        Boolean::class ->   getBoolean( key ) as V
        else -> throw IllegalArgumentException( "Type '${V::class.qualifiedName}' is not a supported type" )
    }
}

/** Equivalent to [Settings.putInt]*/
internal operator fun Settings.set( key: String, value: Int ) {
    putInt( key, value )
}

/** Equivalent to [Settings.putLong]*/
internal operator fun Settings.set( key: String, value: Long ) {
    putLong( key, value )
}

/** Equivalent to [Settings.putString]*/
internal operator fun Settings.set( key: String, value: String ) {
    putString( key, value )
}

/** Equivalent to [Settings.putFloat]*/
internal operator fun Settings.set( key: String, value: Float ) {
    putFloat( key, value )
}

/** Equivalent to [Settings.putDouble]*/
internal operator fun Settings.set( key: String, value: Double ) {
    putDouble( key, value )
}

/** Equivalent to [Settings.putBoolean]*/
internal operator fun Settings.set( key: String, value: Boolean ) {
    putBoolean( key, value )
}

/** Internal use only. Find the proper setter by the reified [V] */
internal inline operator fun <reified V: Any?> Settings.set( key: String, value: V ) {
    if ( value == null ) {
        remove( key )
        return
    }
    when ( V::class ) {
        Int::class ->       set( key, value as Int )
        Long::class ->      set( key, value as Long )
        String::class ->    set( key, value as String )
        Float::class ->     set( key, value as Float )
        Double::class ->    set( key, value as Double )
        Boolean::class ->   set( key, value as Boolean )
        else -> throw IllegalArgumentException( "Type '${V::class.qualifiedName}' is not a valid type" )
    }
}