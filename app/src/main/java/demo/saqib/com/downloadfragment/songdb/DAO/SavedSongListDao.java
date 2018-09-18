package demo.saqib.com.downloadfragment.songdb.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import demo.saqib.com.downloadfragment.songdb.Tables.SavedSongList;



@Dao
public interface SavedSongListDao {

    @Insert
    long insertUser(SavedSongList userInfo);

    /*@Query("UPDATE saved_song_list SET name = :new_user_name WHERE name = :old_user_name")
    int updateUserName(String old_user_name, String new_user_name);*/

    @Update
    int updateUser(SavedSongList userInfo);

    @Query("SELECT * FROM saved_song_list")
    List<SavedSongList> getAllUser();


    @Delete
    int deleteUsers(SavedSongList userInfo);

    @Query("DELETE FROM saved_song_list")
    void nukeTable();

}
