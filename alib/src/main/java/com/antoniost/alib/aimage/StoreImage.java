package com.antoniost.alib.aimage;

/**
 * Created by Antonio Samano on 27/12/18.
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoreImage {

    static private String TAG="StoreImage";

    protected Context context;
    private String path;

    public StoreImage(Context context){
        this.context = context;
        File directory = new File( this.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) , "App/" );
        path = directory.getPath();
    }

    public boolean save(String filename, Bitmap image)  {
        return save(filename,image,false);
    }

    public boolean save(String filename, Bitmap image,boolean absolute)  {
        boolean back = false;
        String ruta, name;
        //Log.d(TAG, "save: "+filename.lastIndexOf("/"));
        if(filename.lastIndexOf("/") == -1){
            ruta = "";
            name = filename;
        }else{
            ruta = filename.substring(0,(filename.lastIndexOf("/")+1));
            name = filename.replace(ruta,"");
        }
        String newpath = (absolute)? ruta: ((ruta.equals(""))?path:path+ruta);
        //Log.d(TAG, "save: "+newpath);
        //Log.d(TAG, "save: "+ruta);
        //Log.d(TAG, "save: "+name);
        File dir = new File(newpath);
        dir.mkdirs();

        File img = new File(newpath, name);
        try {
            FileOutputStream out = new FileOutputStream(img);
            image.compress( Bitmap.CompressFormat.JPEG, 90, out );
            out.flush();
            out.close();
            back = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return back;
    }

    public Bitmap getBitmap(String filename){
        return getBitmap(filename,false);
    }

    public Bitmap getBitmap(String filename,boolean absolute){
        File image = (absolute)? new File(filename):new File(path+"/"+filename);
        if(image.exists()){
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            return BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        }else{
            return null;
        }
    }

    public StoreImage.ImageInfo getImageInfo(String filename){
        return getImageInfo(filename, false);
    }

    public StoreImage.ImageInfo getImageInfo(String filename,boolean absolute){
        File image = (absolute)? new File(filename):new File(path+"/"+filename);
        if (image.exists()){
            return new ImageInfo(filename, image,getBitmap(filename,absolute));
        }
        return null;
    }

    public String getAbsolutePath(String filename){
        File image = new File(filename);
        return (image.exists())?image.getAbsolutePath(): null;
    }

    public static String getPathbyURI(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static class ImageInfo{

        private String nombre, ruta, filename,mimetype, absolute, extension;
        private Bitmap bitmap;

        public ImageInfo (String filename, File archivo, Bitmap imagen){

            this.nombre = filename.substring(filename.lastIndexOf("/") + 1);
            this.ruta = filename.replace(nombre,"");
            this.extension = nombre.substring(nombre.lastIndexOf(".") + 1);
            this.mimetype = getMimeType(extension);
            this.absolute = archivo.getAbsolutePath();
            this.filename = filename;
            this.bitmap = imagen;
        }

        private String getMimeType(String extension){
            String ext = extension.toLowerCase();
            switch (ext){
                case "jpg":
                case "jpeg":
                    ext = "image/jpeg";
                    break;
                case "png":
                    ext = "image/png";
                    break;
                case "gif":
                    ext = "image/gif";
                    break;
                default:
                    ext = "image/jpeg";
                    break;
            }
            return ext;
        }

        public String getNombre(){ return nombre; }
        public String getRuta(){ return ruta; }
        public String getFilename(){ return filename; }
        public String getMimetype(){ return mimetype; }
        public String getExtension(){ return extension; }
        public String getAbsolutePath(){ return absolute; }
        public Bitmap getBitmap(){ return bitmap; }

    }

    public static boolean saveImage(Context context,String filename, Bitmap image)  {
        return new StoreImage(context).save(filename,image);
    }

    public static boolean saveImage(Context context,String filename, Bitmap image,boolean absolute){
        return new StoreImage(context).save(filename,image,absolute);
    }

}