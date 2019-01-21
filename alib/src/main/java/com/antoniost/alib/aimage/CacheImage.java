package com.antoniost.alib.aimage;
/**
 * Created by Antonio Samano on 27/12/18.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CacheImage {

    private static final String TAG = "CacheImage";
    private Context context;

    public CacheImage(Context context){
        this.context = context;
    }

    public Bitmap getImage(String imagen){
        File file = new File( context.getCacheDir() , imagen );
        if(file.exists()){
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            return BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
        }
        return null;
    }
    public boolean hasImage(String imagen){
        File file = new File( context.getCacheDir() , imagen );
        return file.exists();
    }
    public boolean saveImage(String nombre,Bitmap imagen){
        boolean back;
        File file = new File(context.getCacheDir(), nombre);
        try {
            FileOutputStream out = new FileOutputStream(file);
            imagen.compress( Bitmap.CompressFormat.PNG, 100, out );
            out.flush();
            out.close();
            back = true;
            Log.d(TAG, "save: "+file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            back = false;
        }

        return back;
    }

    public static boolean save(Context context, String nombre, Bitmap imagen){
        CacheImage cacheImage = new CacheImage(context);
        return cacheImage.saveImage(nombre,imagen);
    }

    public static boolean has(Context context, String nombre, Bitmap imagen){
        CacheImage cacheImage = new CacheImage(context);
        return cacheImage.hasImage(nombre);
    }

    public static Bitmap get(Context context, String nombre){
        CacheImage cacheImage = new CacheImage(context);
        return cacheImage.getImage(nombre);
    }
}
