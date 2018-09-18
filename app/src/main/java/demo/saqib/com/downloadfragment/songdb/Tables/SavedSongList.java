package demo.saqib.com.downloadfragment.songdb.Tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "saved_song_list")
public class SavedSongList {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "json")
    private String json;

    @ColumnInfo(name = "sd_value")
    private String sd_value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getSd_value() {
        return sd_value;
    }

    public void setSd_value(String sd_value) {
        this.sd_value = sd_value;
    }
}
