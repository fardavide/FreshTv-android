package studio.forface.freshtv.mappers

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * @author Davide Giuseppe Farella.
 * A Mapper of [SourceFileUiModel]
 *
 * Inherit from [UiModelMapper]
 */
internal class SourceFileUiModelMapper: UiModelMapper<SourceFile, SourceFileUiModel>() {

    /** @see UiModelMapper.toUiModel */
    override fun SourceFile.toUiModel(): SourceFileUiModel {
        val shownName = name ?: path.substringAfterLast('/' )
        val shortPath = path // TOOD
        return when( this ) {

            is SourceFile.Epg ->
                SourceFileUiModel.Epg( name, shownName, path, shortPath, type )

            is SourceFile.Playlist ->
                SourceFileUiModel.Playlist( name, shownName, path, shortPath, type )
        }
    }

    /** @see UiModelMapper.toEntity */
    override fun SourceFileUiModel.toEntity() = when( this ) {
        is SourceFileUiModel.Epg ->         SourceFile.Epg( fullPath, sourceType, databaseName )
        is SourceFileUiModel.Playlist ->    SourceFile.Playlist( fullPath, sourceType, databaseName )
    }
}