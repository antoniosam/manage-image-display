package com.antoniost.alib.aimage;
/**
 * Created by Antonio Samano on 27/12/18.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class BitmapEffects {

    static String TAG ="BitmapEffect";

    public static Bitmap cropCenter(Bitmap srcBmp){
        Bitmap dstBmp;
        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );
        }else{
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return  dstBmp;
    }

    public static Bitmap circular(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap (bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


    public static Bitmap fixMaxSizeDisplay(Bitmap bitmap){
        if(bitmap != null){
            if(bitmap.getWidth() > 1440){
                int nh = (int) ( bitmap.getHeight() * (1440 / bitmap.getWidth()) );
                return Bitmap.createScaledBitmap(bitmap, 1440, nh, true);
            }else{
                return  bitmap;
            }
        }
        return null;
    }

    public static Bitmap sizeThumb(Bitmap bitmap){
        return BitmapEffects.resize(70,bitmap);
    }

    public static Bitmap sizeStoreCache(Bitmap bitmap){
        return BitmapEffects.resize(200,bitmap);
    }

    public static Bitmap resize(int size, Bitmap bitmap){
        if(bitmap != null){
            float s= (float) size;
            float height = bitmap.getHeight();
            float width = bitmap.getWidth();
            float i = height * (s / width);
            return Bitmap.createScaledBitmap(bitmap, size, Math.round(i), true);
        }
        return null;
    }
}
