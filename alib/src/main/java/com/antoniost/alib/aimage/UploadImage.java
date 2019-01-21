package com.antoniost.alib.aimage;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;*/


public class UploadImage extends AsyncTask<Void, Void, String> {


    public interface onUploadImage{
        void success(String exito);
        void error(String error);
    }
    static  String TAG="UploadImage";

    public UploadImage( String destino,onUploadImage callback ){
    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
    /*Context context;
    private String destino;
    private boolean unica = false;

    public onUploadImage delegate = null;//Call back interface

    private String error_mensaje;
    private int error_code;

    MultipartBody.Builder builderBody = null;
    RequestBody requestBody = null;

    public interface onUploadImage{
        void success(String exito);
        void error(String error);
    }

    public UploadImage( String destino,onUploadImage callback ){
        this.destino = destino;
        delegate = callback;
    }

    public void postImage(String localpath, String mimetype){
        unica = true;

        MediaType MEDIA_TYPE = MediaType.parse(mimetype);
        requestBody = RequestBody.create( MEDIA_TYPE, new File(localpath) );
    }
    public void addImage(String nombre, String localpath, String mimetype){
        if(builderBody == null){
            builderBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        }
        MediaType MEDIA_TYPE = MediaType.parse(mimetype);
        builderBody.addFormDataPart( nombre, infoimage.getNombre(),RequestBody.create( MEDIA_TYPE, localpath ) );
    }


    @Override
    protected String doInBackground(Void... params) {

        OkHttpClient client = new OkHttpClient();

        RequestBody body =  (unica)? requestBody : builderBody.build();

        Request.Builder builderRequest = new Request.Builder();
        builderRequest.url(destino);
        builderRequest.post(body);
        builderRequest.addHeader("cache-control", "no-cache");
        Request request = builderRequest.build();

        String respuesta = null;
        try {
            Response response = client.newCall(request).execute();

            ResponseBody responseBody = response.body();
            respuesta=responseBody.string();

        } catch (IOException e) {
            error_mensaje = e.getMessage();
        }

        return respuesta;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result == null ){
            Log.w(TAG,"onPostExecute: Null");
            delegate.error(error_mensaje);
        }else{
            Log.d(TAG,"onPostExecute: Exito");
            delegate.success(result);
        }
    }
*/
    public static void sendImage(String destino,String localpath, String mimetype, onUploadImage delegate){
        //UploadImage ajax = new UploadImage(destino, delegate);
        //ajax.postImage(localpath,mimetype);
        //ajax.execute();
    }

}

