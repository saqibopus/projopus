package demo.saqib.com.downloadfragment.download;


import android.util.Base64;

public class SongList {
    public static final String DIRECTORY_NAME ="bobo";
    public static final String KEY ="0123456789123456";
    public static final String song1 ="http://bsingle.ve.vc/data/48/39432/283003/Mere Rashke Qamar - Arijit Singh (DjPunjab.Com).mp3";
    public static final String song2 ="http://bsingle.ve.vc/data/48/39432/283003/Mere Rashke Qamar - Arijit Singh (DjPunjab.Com).mp3";
    public static final String song3 ="http://http://djpunjab.faith/djpunjab/48/39732/Badtameez Dil - Benny Dayal.mp3";

    public static final String song1_ID ="123";

    public static byte[] getKey(){
        return Base64.decode(KEY.getBytes(),Base64.DEFAULT);
    }

    public static byte[] getKeyBytes(){
        return KEY.getBytes();
    }
    /*

    https://stackoverflow.com/questions/37274768/how-to-encrypt-and-decrypt-audio-file-android

    */
}
