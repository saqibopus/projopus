package demo.saqib.com.downloadfragment.Helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by emxcel on 13/9/18.
 */

public class ProgressHelper {

    private ProgressDialog progressDialog;
    private static ProgressHelper progressHelper;

    public static ProgressHelper getInstance(){
        if(progressHelper == null){
            progressHelper = new ProgressHelper();
        }
        return progressHelper;
    }

    public void initProgressDilog(Activity activity){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
    }
    public void initProgressDilog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
    }
    public void show(String message){
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public void dissmiss(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

    }
}
