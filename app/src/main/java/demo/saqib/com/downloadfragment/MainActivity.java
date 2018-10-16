package demo.saqib.com.downloadfragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.saqib.com.downloadfragment.SongList.SongListActivity;
import demo.saqib.com.downloadfragment.download.DownloadActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        System.out.println("----**start");

    }

    @OnClick(R.id.btDownload)
    public void moveToDownload(){
        Intent i =new Intent(MainActivity.this,DownloadActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.btSongList)
    public void songList(){
        Intent i =new Intent(MainActivity.this,SongListActivity.class);
        startActivity(i);
    }

    private void playSong(){
        MediaPlayer mp=new MediaPlayer();
        try{
            mp.setDataSource("/sdcard/Music/maine.mp3");//Write your location here
            mp.prepare();
            mp.start();

        }catch(Exception e){e.printStackTrace();}
    }

}
