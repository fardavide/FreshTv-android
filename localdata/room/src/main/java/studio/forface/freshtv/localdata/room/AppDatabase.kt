package studio.forface.freshtv.localdata.room

import androidx.room.Database
import androidx.room.RoomDatabase
import studio.forface.freshtv.localdata.room.dao.*
import studio.forface.freshtv.localdata.room.entities.*

/**
 * @author Davide Giuseppe Farella
 * Out implementation of [RoomDatabase]
 */
@Database(
    version = 1,
    entities = [
        ChannelGroupPojo::class,
        MovieChannelPojo::class,
        SourceFilePojo::class,
        TvChannelPojo::class,
        TvGuidePojo::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract val channelGroupDao: ChannelGroupDao
    abstract val movieChannelDao: MovieChannelDao
    abstract val sourceFileDao: SourceFileDao
    abstract val tvChannelDao: TvChannelDao
    abstract val tvGuideDao: TvGuideDao
}