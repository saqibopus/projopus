package demo.saqib.com.downloadfragment.test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by emxcel on 11/9/18.
 */

public class CustomException extends Throwable {

    public void data(){


        try {
            PrintWriter pw = new PrintWriter("abc.txt");
            getFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getFile() throws FileNotFoundException{

    }


}
