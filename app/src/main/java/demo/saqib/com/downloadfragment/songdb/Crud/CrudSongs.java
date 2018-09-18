package demo.saqib.com.downloadfragment.songdb.Crud;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;
import demo.saqib.com.downloadfragment.Helpers.AppConstant;
import demo.saqib.com.downloadfragment.Helpers.Logs;
import demo.saqib.com.downloadfragment.Helpers.ProgressHelper;
import demo.saqib.com.downloadfragment.songdb.Config.Database;
import demo.saqib.com.downloadfragment.songdb.Tables.SavedSongList;


public class CrudSongs {

    private Database database;
    private Activity activity;
    private ProgressHelper progressHelper;
    private CRUDOperationListner crudOperationListner;

    public CrudSongs(Activity activity,CRUDOperationListner crudOperationListner) {
        this.activity = activity;
        this.crudOperationListner =crudOperationListner;
        initProgress();
        initDatabase();
    }

    private void initProgress(){
        progressHelper = ProgressHelper.getInstance();
        progressHelper.initProgressDilog(activity);
    }
    private void initDatabase(){
        database = Room.databaseBuilder(activity, Database.class, AppConstant.DB.DB_NAME)
                .allowMainThreadQueries().build();
    }

    public interface CRUDOperationListner {
        void onInsert(String message,long id);
        void onUpdate(String message,int id);
        void onGetAll(String message,List<SavedSongList> userInfos);
    }

    public void saveSong(SavedSongList songInfo){
       /* if(database.isOpen()){
            saveSongItem(songInfo);
        }else {
            Logs.p("Database is not open");
        }*/
        saveSongItem(songInfo);
    }

    public void getAllSongs(){
       /* if(database.isOpen()){
            getSongList();
        }else {
            Logs.p("Database is not open");
        }*/

        getSongList();
    }

    private void removeSession(){

    }


    private void saveSongItem(final SavedSongList song){

        new AsyncTask<Void, Void, Void>() {
            long id = -1;
            @Override
            protected Void doInBackground(Void... voids) {
                id = database.getUserInfoDao().insertUser(song);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Inserting User");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressHelper.dissmiss();
                crudOperationListner.onInsert("message",id);
            }
        }.execute();
    }

    private void getSongList() {
        new AsyncTask<Void, Void, Void>() {
            List<SavedSongList> users = new ArrayList<>();
            @Override
            protected Void doInBackground(Void... params) {
                users = database.getUserInfoDao().getAllUser();
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Getting User");
            }

            @Override
            protected void onPostExecute(Void notes) {
                progressHelper.dissmiss();
                crudOperationListner.onGetAll("success",users);
            }
        }.execute();
    }
}
