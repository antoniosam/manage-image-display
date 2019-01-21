package com.antoniost.alib.ainteract;
/**
 * Created by Antonio Samano on 27/12/18.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import com.antoniost.alib.aimage.StoreImage;

public class ActionTakePicture {

    private static final String TAG = "ActionTakePicture" ;
    private Context context;
    private Fragment fragment;
    private boolean isActivity = false;
    private int codecamara = 0;
    private int codegalery = 0;

    private onResponseActionTakePicture callback;


    public static ActionTakePicture buildPetition(Context context, int codecamara, int codegalery , onResponseActionTakePicture callback){
        return new ActionTakePicture( context, codecamara,  codegalery,callback);
    }
    public static ActionTakePicture buildPetition(Context context, Fragment fragment, int codecamara, int codegalery , onResponseActionTakePicture callback){
        return new ActionTakePicture(context,fragment, codecamara,  codegalery,callback);
    }
    public ActionTakePicture(Context context, Fragment fragment, int codecamara, int codegalery , onResponseActionTakePicture callback){
        isActivity = false;
        this.context = context;
        this.fragment = fragment;
        this.codecamara = codecamara;
        this.codegalery = codegalery;
        this.callback = callback;
    }
    public ActionTakePicture(Context context, int codecamara, int codegalery , onResponseActionTakePicture callback){
        isActivity = true;
        this.context = context;
        this.codecamara = codecamara;
        this.codegalery = codegalery;
        this.callback = callback;
    }

    public void openCamera(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePicture.putExtra("RESULT_OK", 5);
        if(isActivity){
            ((Activity)context).startActivityForResult(takePicture, codecamara);
        }else{
            fragment.startActivityForResult(takePicture, codecamara);
        }
    }

    public void openGalery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.putExtra("RESULT_OK", 5);
        if(isActivity){
            ((Activity)context).startActivityForResult(pickPhoto, codegalery);
        }else{
            fragment.startActivityForResult(pickPhoto, codegalery);
        }
    }

    public void answerRequest(int requestCode, int resultCode, Intent data) {
        //Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode);
        if (requestCode == codegalery) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                //Log.d(TAG, "selectedImage: "+selectedImage.getPath());
                String filename = StoreImage.getPathbyURI(context, selectedImage);

                //Log.d(TAG, "filename: "+filename);

                if (filename != null) {
                    Bitmap bitmap1 = new StoreImage(context).getBitmap(filename,true);
                    callback.image( bitmap1 ,true);

                } else {
                    callback.cancel();
                }
            } else {
                callback.cancel();
            }


        } else if (requestCode == codecamara ) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                callback.image( bitmap ,false);
            } else {
                callback.cancel();
            }
        }

    }


}
