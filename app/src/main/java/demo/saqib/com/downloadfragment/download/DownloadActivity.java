package demo.saqib.com.downloadfragment.download;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import demo.saqib.com.downloadfragment.R;

public class DownloadActivity extends AppCompatActivity {

    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_text)
    TextView mProgressText;
    @BindView(R.id.btStopActivity)
    TextView btStopActivity;
    @BindView(R.id.tvId)
    TextView tvId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);

        registerReceiver();



        if (checkPermission()) {
            new GetListOfFiles().execute("");
        } else {
            requestPermission();
        }



    }

    public class GetListOfFiles extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+SongList.DIRECTORY_NAME;

            File f = new File(path);
            File file[] = f.listFiles();

            for(int i=0;i<file.length;i++){
                System.out.println("----** file "+file[i].getName());
            }
            System.out.println("----** Jalso Files : "+file.length);
            return null;
        }
    }

    @OnClick(R.id.btStopActivity)
    public void stopActivity() {
        DownloadActivity.this.finish();
    }

    @OnClick(R.id.btn_download)
    public void downloadFile() {

        if (checkPermission()) {
            startDownload();
        } else {
            requestPermission();
        }
    }

    private void startDownload() {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("id",SongList.song1_ID);
        intent.putExtra("url",SongList.song1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("----**Oreo");
            DownloadActivity.this.startForegroundService(intent);
        } else {
            System.out.println("----**Less than oreo");
            DownloadActivity.this.startService(intent);
        }
        tvId.setText(SongList.song1_ID);
    }

    private void registerReceiver() {
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");
                mProgressBar.setProgress(download.getProgress());
                if (download.getProgress() == 100) {
                    mProgressText.setText("File Download Complete");
                } else {
                    mProgressText.setText(String.format("Downloaded (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));
                    System.out.println(String.format("----**Downloaded (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));
                }
            }
        }
    };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                } else {
                    Snackbar.make(findViewById(R.id.coordinatorLayout), "Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("----** onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("----** onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("----** onStop");

    }
}
