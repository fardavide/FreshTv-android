package studio.forface.freshtv.uimodels

import androidx.annotation.DrawableRes
import studio.forface.freshtv.domain.entities.SourceFile

/**
 * @author Davide Giuseppe Farella
 * An Ui Model representing a Source File: Playlist or Epg.
 */
internal sealed class SourceFileUiModel {
    abstract val databaseName: String?
    abstract val shownName: String
    abstract val fullPath: String
    abstract val shortPath: String
    abstract val sourceType: SourceFile.Type
    @get:DrawableRes abstract val sourceTypeIconRes: Int

    data class Epg(
            override val databaseName: String?,
            override val shownName: String,
            override val fullPath: String,
            override val shortPath: String,
            override val sourceType: SourceFile.Type,
            override val sourceTypeIconRes: Int
    ): SourceFileUiModel()

    data class Playlist(
            override val databaseName: String?,
            override val shownName: String,
            override val fullPath: String,
            override val shortPath: String,
            override val sourceType: SourceFile.Type,
            override val sourceTypeIconRes: Int
    ): SourceFileUiModel()
}