package demo.saqib.com.downloadfragment.download;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;


import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import demo.saqib.com.downloadfragment.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class DownloadService extends IntentService {


    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;


    private String songId="";
    private String songUrl="";
    @Override
    protected void onHandleIntent(Intent intent) {

        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationBuilder = new NotificationCompat.Builder(this,"default")
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setSound(null)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        try{
            songId = intent.getStringExtra("id");
            songUrl = intent.getStringExtra("url");
        }catch (Exception e){
            System.out.println("----** No song ID URL found");
        }


        initDownload();

    }
    private void initDownload(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dl.google.com/dl/")
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


        Call<ResponseBody> request = retrofitInterface.downloadFileDynamicUrl(songUrl);
        try {
            createDirectory(SongList.DIRECTORY_NAME);
            downloadFile(request.execute().body());

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+SongList.DIRECTORY_NAME+"/", songId);
        OutputStream output = new FileOutputStream(outputFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);

            if (currentTime > 1000 * timeCount) {

                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }
    private void sendNotification(Download download){

        sendIntent(download);
        notificationBuilder.setProgress(100,download.getProgress(),false);
        notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download){

        Intent intent = new Intent(DownloadActivity.MESSAGE_PROGRESS);
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }
    private void onDownloadComplete(){

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("File Downloaded");
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

    private static void createDirectory(String directoryName){
        File jalsoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+directoryName+"/");
        if(!jalsoDir.exists())
            jalsoDir.mkdirs();
        else
            System.out.println("----**error "+directoryName+" already exists");
    }
}