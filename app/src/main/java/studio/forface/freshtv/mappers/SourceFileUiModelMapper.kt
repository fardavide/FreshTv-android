package studio.forface.freshtv.mappers

import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * A Mapper of [SourceFileUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella
 */
internal class SourceFileUiModelMapper: UiModelMapper<SourceFile, SourceFileUiModel, SourceFile>() {

    /** @see UiModelMapper.toUiModel */
    override fun SourceFile.toUiModel(): SourceFileUiModel {
        val shownName = name ?: path.substringAfterLast('/' )
        val shortPath = path // TODO
        val sourceTypeIconRes = when( type ) {
            SourceFile.Type.LOCAL -> R.drawable.ic_file
            SourceFile.Type.REMOTE -> R.drawable.ic_web
        }

        return when( this ) {
            is SourceFile.Epg ->
                SourceFileUiModel.Epg( name, shownName, path, shortPath, type, sourceTypeIconRes )

            is SourceFile.Playlist ->
                SourceFileUiModel.Playlist( name, shownName, path, shortPath, type, sourceTypeIconRes )
        }
    }

    /** @see UiModelMapper.toEntity */
    override fun SourceFileUiModel.toEntity() = when( this ) {
        is SourceFileUiModel.Epg ->         SourceFile.Epg( fullPath, sourceType, databaseName )
        is SourceFileUiModel.Playlist ->    SourceFile.Playlist( fullPath, sourceType, databaseName )
    }
}