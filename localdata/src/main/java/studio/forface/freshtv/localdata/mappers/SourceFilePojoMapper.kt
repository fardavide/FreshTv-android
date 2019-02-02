package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.localdata.SourceFilePojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [SourceFilePojo]
 *
 * Inherit from [PojoMapper].
 */
class SourceFilePojoMapper: PojoMapper<SourceFile, SourceFilePojo>() {

    /** @see PojoMapper.toPojo */
    override fun SourceFile.toPojo() = with(this ) {
        SourceFilePojo.Impl( path, this::class.simpleName!!, name, type.name )
    }

    /** @see PojoMapper.toEntity */
    override fun SourceFilePojo.toEntity() = with(this ) {
        val sourceFileType = SourceFile.Type.valueOf( souceType )
        when ( type ) {
            SourceFile.Epg.TYPE_NAME -> SourceFile.Epg( path, sourceFileType, name )
            SourceFile.Playlist.TYPE_NAME -> SourceFile.Playlist( path, sourceFileType, name )
            else -> throw AssertionError()
        }
    }
}