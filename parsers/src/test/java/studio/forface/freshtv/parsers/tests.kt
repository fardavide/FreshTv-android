package studio.forface.freshtv.parsers

import studio.forface.freshtv.domain.entities.SourceFile

val realEpgSource = SourceFile.Epg("http://www.epgitalia.tv/xml/guide.gzip", SourceFile.Type.REMOTE )