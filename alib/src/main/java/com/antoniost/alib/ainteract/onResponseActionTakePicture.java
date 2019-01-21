package com.antoniost.alib.ainteract;
/**
 * Created by Antonio Samano on 27/12/18.
 */
import android.graphics.Bitmap;

public interface onResponseActionTakePicture {
    void image(Bitmap bitmap, boolean camera);
    void cancel();
}
