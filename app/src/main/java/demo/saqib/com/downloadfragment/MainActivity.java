package demo.saqib.com.downloadfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.saqib.com.downloadfragment.download.DownloadActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        System.out.println("----**start");
        try{
            Intent i =new Intent(MainActivity.this,DownloadActivity.class);
            startActivity(i);

        }catch (Exception e){
            System.out.println("----**Excepton");
        }
        System.out.println("----**End");
    }

    @OnClick(R.id.btDownload)
    public void moveToDownload(){
        Intent i =new Intent(MainActivity.this,DownloadActivity.class);
        startActivity(i);
    }
}