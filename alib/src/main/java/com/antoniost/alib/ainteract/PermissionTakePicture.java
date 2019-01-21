package com.antoniost.alib.ainteract;
/**
 * Created by Antonio Samano on 27/12/18.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;


public class PermissionTakePicture {
    static private String TAG="PermissionTakePicture";

    private Activity activity;
    private Context context;
    private Fragment fragment;
    private boolean isActivity = false;
    private int code = 0;
    private boolean require = false;
    private onResponsePermissionTakePicture callback;


    public PermissionTakePicture(){
        require = false;
    }
    public PermissionTakePicture(onResponsePermissionTakePicture callback){
        require = false;
        this.callback = callback;
    }
    public PermissionTakePicture(Context context, int code, onResponsePermissionTakePicture callback){
        require = true;
        isActivity = true;
        this.context = context;
        this.code = code;
        this.callback = callback;
    }
    public PermissionTakePicture(Context context,Fragment fragment, int code, onResponsePermissionTakePicture callback){
        require = true;
        isActivity = false;
        this.context = context;
        this.fragment = fragment;
        this.code = code;
        this.callback = callback;
    }
    public static PermissionTakePicture buildPetition(Context context,Fragment fragment, int code, onResponsePermissionTakePicture callback){
        //Activity act = (Activity) context;
        PermissionTakePicture per;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            per = new PermissionTakePicture(context,fragment, code,  callback);
        }else{
            Log.d(TAG, "buildPetition: NO need Permission");
            per = new PermissionTakePicture(callback);
        }
        return per;
    }
    public static PermissionTakePicture buildPetition(Context context, int code, onResponsePermissionTakePicture callback){
        //Activity act = (Activity) context;
        PermissionTakePicture per;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               per = new PermissionTakePicture(context, code,  callback);
        }else{
            Log.d(TAG, "buildPetition: NO need Permission");
             per = new PermissionTakePicture(callback);
        }
        return per;
    }


    public boolean canTakePicture(){
        if (require){
            boolean camera, store;
            if(isActivity){
                Activity activity = (Activity)context;
                camera = (ContextCompat.checkSelfPermission( activity, Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED);
                store = (ContextCompat.checkSelfPermission( activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED);
            }else{
                camera = (ContextCompat.checkSelfPermission( context, Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED);
                store = (ContextCompat.checkSelfPermission( context, Manifest.permission.WRITE_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED);
            }

            return  (camera && store);
        }else{
            return true;
        }
    }

    public  void requestTakePicture(){
        if(require){
            String[] lista  = new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };
            if(isActivity){
                ActivityCompat.requestPermissions( activity, lista , code);
            }else{
                fragment.requestPermissions( lista , code);
            }
        }else{
            Log.d(TAG, "buildPetition: NO need Permission");
            callback.grant();

        }

    }

    public void answerRequest(int requestCode,String permissions[], int[] grantResults){
        if (code == requestCode ) {
            boolean autorizado = true;
            if (grantResults.length > 0  ) {
                for(int a: grantResults ){
                    if(a != PackageManager.PERMISSION_GRANTED){  autorizado=false; }
                }
            }else{
                autorizado = false;
            }

            if (autorizado) {
                callback.grant();
            } else {
                callback.denied();
            }
        }
    }


    /*
    public void checkAccionImagen( ){
        boolean camara = check( Manifest.permission.CAMERA );
        boolean store = check( Manifest.permission.WRITE_EXTERNAL_STORAGE );
        if( camara && store ){
            //imageGrant=true;
            if( delegateAccionFoto!=null ){ delegateAccionFoto.permisos(); }
        }else{
            String[] lista = new String[0];
            if(!camara && !store){
                lista = new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };
            }else if (!camara){
                lista = new String[]{ Manifest.permission.CAMERA };
            }else {
                lista = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE };
            }
            //imageGrant=false;
            request(lista, codeImagen);
        }
    }


    public void answerRequest(int requestCode,String permissions[], int[] grantResults){
        if (requestCode == codeImagen) {
            if (checkGrantResults(grantResults)) {
                if( delegateAccionFoto!=null ){ delegateAccionFoto.permisos(); }
            } else {
                if( delegateAccionFoto!=null ){ delegateAccionFoto.denegados(); }
            }
        }
    }



    private boolean check( String permiso ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return  (ContextCompat.checkSelfPermission( act,  permiso)   == PackageManager.PERMISSION_GRANTED);
        }else{
            return true;
        }

    }
    private void request( String[] lista , int code){
        ActivityCompat.requestPermissions( act, lista , code);
    }


    private boolean checkGrantResults(int[] grantResults){
        boolean autorizado=true;
        if (grantResults.length > 0  ) {
            for(int a: grantResults ){
                if(a != PackageManager.PERMISSION_GRANTED){  autorizado=false; }
            }
        }else{
            autorizado = false;
        }
        return autorizado;

    }




    public interface onGrantAcccionFoto{
        void permisos();
        void denegados();

    }
*/
}

