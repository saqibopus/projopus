package demo.saqib.com.downloadfragment.SongList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import demo.saqib.com.downloadfragment.Helpers.Logs;
import demo.saqib.com.downloadfragment.R;
import demo.saqib.com.downloadfragment.download.SongList;
import demo.saqib.com.downloadfragment.songdb.Crud.CrudSongs;
import demo.saqib.com.downloadfragment.songdb.Tables.SavedSongList;

public class SongListActivity extends AppCompatActivity implements CrudSongs.CRUDOperationListner {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SongListAdapter adapter;
    private CrudSongs crudSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        ButterKnife.bind(this);
        initClasses();
        initRecycleView();

        SongListModel song = new SongListModel("123", SongList.song1);
        SongListModel song1 = new SongListModel("456", SongList.song2);
        SongListModel song2 = new SongListModel("786", SongList.song3);

        try{
            saveSong(song);
            //saveSong(song1);
            //saveSong(song2);
        }catch (Exception e){
            Logs.p("Save song failed");
        }

        try{
            getSongData();
        }catch (Exception e){
            Logs.p("get all  song failed");
        }


    }

    private void initClasses() {
        crudSongs = new CrudSongs(SongListActivity.this,this);
    }

    private void initRecycleView() {
        adapter = new SongListAdapter(SongListActivity.this, getSongData(), new SongListAdapter.SongClick() {
            @Override
            public void onSongDownloadClick(SongListModel model) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<SongListModel> getSongData() {
        ArrayList<SongListModel> data = new ArrayList<>();
        if (crudSongs != null) {
            crudSongs.getAllSongs();
        } else {
            Logs.p("User is null while getSongData");
        }
        return data;
    }

    private void saveSong(SongListModel song) {

        if (crudSongs != null && song != null) {
            SavedSongList savedSongList = new SavedSongList();
            savedSongList.setId(Long.parseLong(song.getId()));
            savedSongList.setJson("{id,name,etc}");
            savedSongList.setSd_value(new StringBuilder(song.getId()).reverse().toString());
            crudSongs.saveSong(savedSongList);
        } else {
            Logs.p("User is null while getSongData");
        }
    }

    @Override
    public void onInsert(String message, long id) {
        Logs.tS(SongListActivity.this,message);
        Logs.p("insert : "+message);
    }

    @Override
    public void onUpdate(String message, int id) {

    }

    @Override
    public void onGetAll(String message, List<SavedSongList> userInfos) {
        Logs.tS(SongListActivity.this,message);
        Logs.p("Song list size :"+userInfos.size());

        for(SavedSongList user: userInfos){
            Logs.p("User : id -> "+user.getId()+" json->"+user.getJson()+" sd_value->"+user.getSd_value());
        }
    }
}
