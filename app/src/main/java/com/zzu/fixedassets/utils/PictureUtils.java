package com.zzu.fixedassets.utils;


import com.zzu.fixedassets.app.Constans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Mersens on 2016/11/10.
 */

public class PictureUtils {

    public static void saveToDisk(InputStream stream,onSaveFileListener listener) {
        try {
            File futureStudioIconFile = new File(Constans.ALBUM_PATH);
            if(!futureStudioIconFile.exists()){
                futureStudioIconFile.mkdirs();
            }
            File myCaptureFile = new File(futureStudioIconFile, UUID.randomUUID()+".jpg");
            String path=Constans.ALBUM_PATH+UUID.randomUUID()+".jpg";
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[1024];
                inputStream = stream;
                outputStream = new FileOutputStream(myCaptureFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                listener.onSuccess(path);
            } catch (IOException e) {
               listener.onError(e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            listener.onError(e);
        }
    }

    public interface onSaveFileListener{
        void onSuccess(String path);
        void onError(Exception e);

    }


}
