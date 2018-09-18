package demo.saqib.com.downloadfragment.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by emxcel on 4/9/18.
 */

public interface RetrofitInterface {

    @GET("android/studio/ide-zips/3.1.4.0/android-studio-ide-173.4907809-linux.zip")
    @Streaming
    Call<ResponseBody> downloadFile();

    @GET()
    @Streaming
    Call<ResponseBody> downloadFileDynamicUrl(@Url String url);
}
