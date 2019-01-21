package com.antoniost.alib.aimage;
/**
 * Created by Antonio Samano on 27/12/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


import com.antoniost.alib.aimage.BitmapEffects;
import com.antoniost.alib.aimage.StoreImage;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    private String TAG="DownloadImage";
    private String url="";
    private String error="";

    private onDownloadImage delegate;

    public interface onDownloadImage{
        void success(Bitmap imagen);
        void error();
    }

    public DownloadImage(String ruta, onDownloadImage callback) {
        url = ruta;
        delegate= callback;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            error = e.getMessage();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        if(result!=null){
            delegate.success(result);
        }else{
            //Log.w(TAG, "onPostExecute Url: "+url+" Error: - "+error );
            delegate.error();
        }
    }

    public static void request(String ruta, onDownloadImage delegate){
        DownloadImage ajax = new DownloadImage(ruta, delegate);
        ajax.execute();
    }
    public static void requestSave(String ruta, final String filename, final Context context){
        requestSave(ruta, filename,context,null);
    }
    public static void requestSave(String ruta, final String filename, final Context context, final onDownloadImage delegate){
        DownloadImage ajax = new DownloadImage(ruta, new onDownloadImage() {
            @Override
            public void success(Bitmap imagen) {
                StoreImage storeImage = new StoreImage(context);
                Bitmap bitmap = BitmapEffects.fixMaxSizeDisplay(imagen);
                if(storeImage.save(filename, bitmap)){
                    if(delegate!=null){
                        delegate.success(bitmap);
                    }
                }else{
                    if(delegate!=null){
                        delegate.error();
                    }
                }
            }
            @Override
            public void error() {
                if(delegate!=null){
                    delegate.error();
                }
            }
        });
        ajax.execute();
    }
}
