package demo.saqib.com.downloadfragment.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by root on 1/6/17.
 */

public class KeyPref {

    //ToDo this class helps for store temporary data
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    public static final String DMS_PREF = "key_pref";
    public static final String KEY = "key";

    //Todo: Default constructor
    public KeyPref(Context context) {
        mSharedPreferences = context.getSharedPreferences(DMS_PREF, Context.MODE_PRIVATE);
    }

    //Todo: data save method
    public void saveKey(SecretKey value) {
        String encodedKey = Base64.encodeToString(value.getEncoded(), Base64.DEFAULT);
        Logs.p("Key Pref Save : "+encodedKey);
        mEditor = mSharedPreferences.edit();
        mEditor.putString(KEY, encodedKey);
        mEditor.commit();
    }

    //Todo: get value method
    public SecretKey getKey() {
        Logs.p("Key Pref get : "+mSharedPreferences.getString(KEY, null));
        byte[] encodedKey = Base64.decode(mSharedPreferences.getString(KEY, null), Base64.DEFAULT);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return originalKey;
    }

    //Todo: clear data method
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }
}

