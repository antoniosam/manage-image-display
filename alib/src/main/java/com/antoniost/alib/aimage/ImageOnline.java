package com.antoniost.alib.aimage;
/**
 * Created by Antonio Samano on 27/12/18.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import com.antoniost.alib.aimage.CacheImage;

public class ImageOnline {
    private TempCache tempcache;

    private Context context;

    interface onBeforeRender {
        public Bitmap beforeRender(Bitmap image);
    }

    private static final String TAG = "ImageOnline";
    private CacheImage cacheImage;

    public ImageOnline() {

    }

    public ImageOnline(Context context) {
        this.context = context;
    }

    public void render(String ruta, View imageView) {
        render(ruta, imageView, false, false,null);
    }
    public void render(String ruta, View imageView, boolean savecache, onBeforeRender delegate) {
        render(ruta, imageView, savecache, false,delegate);
    }
    public void renderBg(String ruta, View imageView) {
        renderLista(ruta, imageView, false, true,null);
    }
    public void renderBg(String ruta, View imageView, boolean savecache, onBeforeRender delegate) {
        renderLista(ruta, imageView, savecache, true,delegate);
    }

    public void renderLista(String ruta, View imageView) {
        renderLista(ruta, imageView, false, false,null);
    }
    public void renderLista(String ruta, View imageView, boolean savecache) {
        renderLista(ruta, imageView, savecache, false,null);
    }
    public void renderLista(String ruta, View imageView,  onBeforeRender delegate) {
        renderLista(ruta, imageView, false, false,delegate);
    }
    public void renderLista(String ruta, View imageView, boolean savecache, onBeforeRender delegate) {
        renderLista(ruta, imageView, savecache, false,delegate);
    }
    public void renderListaBg(String ruta, View imageView) {
        renderLista(ruta, imageView, false, true,null);
    }
    public void renderListaBg(String ruta, View imageView, boolean savecache) {
        renderLista(ruta, imageView, savecache, true,null);
    }
    public void renderListaBg(String ruta, View imageView, onBeforeRender delegate) {
        renderLista(ruta, imageView, false, true,delegate);
    }
    public void renderListaBg(String ruta, View imageView, boolean savecache, onBeforeRender delegate) {
        renderLista(ruta, imageView, savecache, true,delegate);
    }

    private TempCache getTempCache() {
        if(tempcache == null){
            tempcache = new TempCache();
        }
        return tempcache;
    }

    private void renderLista(final String ruta, final View view, boolean savecache, final boolean background, final onBeforeRender delegate) {
        final TempCache tempCache = getTempCache();

        DownloadImage.onDownloadImage onDownloadImage;
        if (savecache) {
            if(context == null){
                throw new NullPointerException(" must instance with Context new ImageOnline(context)");
            }
            if(tempCache.hasImage(ruta)){
                Bitmap bitmap = tempCache.getImage(ruta);
                if (delegate != null) {
                    bitmap = delegate.beforeRender(bitmap);
                }
                show(background, view, bitmap);
            }else{
                final CacheImage cache = new CacheImage(context);
                if (cache.hasImage(slugurl(ruta))) {
                    Bitmap bitmap = cache.getImage(slugurl(ruta));
                    tempCache.saveImage(ruta,bitmap);
                    if (delegate != null) {
                        bitmap = delegate.beforeRender(bitmap);
                    }
                    show(background, view, bitmap);
                }
                onDownloadImage = new DownloadImage.onDownloadImage() {
                    @Override
                    public void success(Bitmap imagen) {
                        Bitmap bitmap = BitmapEffects.fixMaxSizeDisplay(imagen);
                        cache.saveImage(slugurl(ruta), bitmap);
                        tempCache.saveImage(ruta,bitmap);
                        if (delegate != null) {
                            bitmap = delegate.beforeRender(bitmap);
                        }
                        show(background, view, bitmap);
                    }

                    @Override
                    public void error() {
                        Log.d(TAG, "error: onDownloadImage " + ruta);
                    }
                };
                DownloadImage.request(ruta, onDownloadImage);
            }

        } else {
            if(tempCache.hasImage(ruta)){
                Bitmap bitmap = tempCache.getImage(ruta);
                if (delegate != null) {
                    bitmap = delegate.beforeRender(bitmap);
                }
                show(background, view, bitmap);
            }else{
                onDownloadImage = new DownloadImage.onDownloadImage() {
                    @Override
                    public void success(Bitmap imagen) {
                        Bitmap bitmap = BitmapEffects.fixMaxSizeDisplay(imagen);
                        tempCache.saveImage(ruta,bitmap);
                        if (delegate != null) {
                            bitmap = delegate.beforeRender(bitmap);
                        }

                        show(background, view, bitmap);
                    }

                    @Override
                    public void error() {
                        Log.d(TAG, "error: onDownloadImage " + ruta);
                    }
                };
                DownloadImage.request(ruta, onDownloadImage);
            }

        }

    }

    private void render(final String ruta, final View view, boolean savecache, final boolean background, final onBeforeRender delegate) {
        DownloadImage.onDownloadImage onDownloadImage;
        if (savecache) {
            if(context == null){
                throw new NullPointerException(" must instance with Context new ImageOnline(context)");
            }
            final CacheImage cache = new CacheImage(context);
            if (cache.hasImage(slugurl(ruta))) {
                Bitmap bitmap = cache.getImage(slugurl(ruta));
                if (delegate != null) {
                    bitmap = delegate.beforeRender(bitmap);
                }
                show(background, view, bitmap);
            }
            onDownloadImage = new DownloadImage.onDownloadImage() {
                @Override
                public void success(Bitmap imagen) {
                    Bitmap bitmap = BitmapEffects.fixMaxSizeDisplay(imagen);
                    cache.saveImage(slugurl(ruta), bitmap);
                    if (delegate != null) {
                        bitmap = delegate.beforeRender(bitmap);
                    }
                    show(background, view, bitmap);
                }

                @Override
                public void error() {
                    Log.d(TAG, "error: onDownloadImage " + ruta);
                }
            };
        } else {
            onDownloadImage = new DownloadImage.onDownloadImage() {
                @Override
                public void success(Bitmap imagen) {
                    Bitmap bitmap = BitmapEffects.fixMaxSizeDisplay(imagen);
                    if (delegate != null) {
                        bitmap = delegate.beforeRender(bitmap);
                    }
                    show(background, view, bitmap);
                }

                @Override
                public void error() {
                    Log.d(TAG, "error: onDownloadImage " + ruta);
                }
            };
        }
        DownloadImage.request(ruta, onDownloadImage);

    }

    private void show(boolean background, View view, Bitmap imagen) {
        if (background) {
            BitmapDrawable ob = new BitmapDrawable(context.getResources(), imagen);
            view.setBackground(ob);
        } else {
            ((ImageView) view).setImageBitmap(imagen);
        }
    }

    private String slugurl(String url) {
        return url.replace("/", "_").replace(":", "_").replace("?", "_").replace("=", "_");
    }

    public void renderCircular(final String ruta, final ImageView view) {
        render(ruta, view, false, new onBeforeRender() {
            @Override
            public Bitmap beforeRender(Bitmap image) {
                return BitmapEffects.circular(image);
            }
        });
    }

    public void renderCircular(final String ruta, final ImageView view, boolean savecache) {
        render(ruta, view, savecache, new onBeforeRender() {
            @Override
            public Bitmap beforeRender(Bitmap image) {
                return BitmapEffects.circular(image);
            }
        });
    }

    private class TempCache {

        private static final String TAG = "CacheImage";
        private LruCache<String, Bitmap> mMemoryCache;

        public TempCache() {
            // Get max available VM memory, exceeding this amount will throw an
            // OutOfMemory exception. Stored in kilobytes as LruCache takes an
            // int in its constructor.
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            //Log.d(TAG, "CacheImage: "+cacheSize);
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    //Log.d(TAG, "sizeOf: "+key+" "+(bitmap.getByteCount() / (1024 * 1)));
                    return bitmap.getByteCount() / (1024);
                }
            };
        }

        public boolean hasImage(String name) {
            //Log.d(TAG, "hasImage: "+name+" "+mMemoryCache.get(name));
            return (mMemoryCache.get(name) != null);
        }

        public Bitmap getImage(String name) {
            return mMemoryCache.get(name);
        }

        public void saveImage(String key, Bitmap bitmap) {
            //Log.d(TAG, "saveImage: "+key+" "+mMemoryCache.put(key, bitmap)+" "+bitmap);
            mMemoryCache.put(key, bitmap);
        }
    }

    public static void renderOneImage(String ruta, View imageView) {
        new ImageOnline().render(ruta, imageView);
    }
    public static void renderOneImageCache(Context context,String ruta, View imageView) {
        new ImageOnline(context).render(ruta, imageView,true,null);
    }
    public static void renderOneImageEffect(String ruta, View imageView,onBeforeRender delegate) {
        new ImageOnline().render(ruta, imageView,false,delegate);
    }
    public static void renderOneImageCacheEffect(Context context,String ruta, View imageView,onBeforeRender delegate) {
        new ImageOnline(context).render(ruta, imageView,true,delegate);
    }
    public static void renderOneImageEffectCircular(String ruta, View imageView) {
        new ImageOnline().render(ruta, imageView, false, new onBeforeRender() {
            @Override
            public Bitmap beforeRender(Bitmap image) {
                return BitmapEffects.circular(image);
            }
        });
    }
    public static void renderOneImageCacheEffectCircular(Context context,String ruta, View imageView) {
        new ImageOnline(context).render(ruta, imageView,true,new onBeforeRender() {
            @Override
            public Bitmap beforeRender(Bitmap image) {
                return BitmapEffects.circular(image);
            }
        });
    }
    public static void renderOneBg(String ruta, View imageView) {
        new ImageOnline().renderBg(ruta, imageView);
    }
    public static void renderOneBgCache(Context context,String ruta, View imageView) {
        new ImageOnline(context).renderBg(ruta, imageView,true,null);
    }

}