package demo.saqib.com.downloadfragment.songdb.Config;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;


import demo.saqib.com.downloadfragment.songdb.DAO.SavedSongListDao;
import demo.saqib.com.downloadfragment.songdb.Tables.SavedSongList;



@android.arch.persistence.room.Database(
        entities = {SavedSongList.class},
        version = 1)
public abstract class Database extends RoomDatabase {

    public abstract SavedSongListDao getUserInfoDao();
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public boolean inTransaction() {
        return super.inTransaction();
    }
}
