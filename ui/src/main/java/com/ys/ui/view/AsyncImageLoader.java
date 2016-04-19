package com.ys.ui.view;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.R.drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class AsyncImageLoader {

	 private static String TAG = "AsyncImageLoader";
     private HashMap<String, SoftReference<Drawable>> imageCache;
     public AsyncImageLoader() {
             imageCache = new HashMap<String, SoftReference<Drawable>>();
         }
      
     public Drawable loadDrawable(final String imageUrl,final String Tag, final ImageCallback imageCallback) {
             if (imageCache.containsKey(imageUrl)) {
            	 
                 SoftReference<Drawable> softReference = imageCache.get(imageUrl);
                 Drawable drawable = softReference.get();
                 if (drawable != null) {
                	 Log.i(TAG,"资源相同ͬ");
                     return drawable;
                 }
                 if(drawable == null){
                	 Log.i(TAG,"资源已经被回收");
                 }
             }
             final Handler handler = new Handler() {
                 public void handleMessage(Message message) {
                     imageCallback.imageLoaded((Drawable) message.obj, imageUrl,Tag);
                 }
             };
             new Thread() {
                 @Override
                 public void run() {
                     Drawable drawable = loadImageFromUrl(imageUrl);
                     imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                     Message message = handler.obtainMessage(0, drawable);
                     handler.sendMessage(message);
                 }
             }.start();
             return null;
         }
       
    public static Drawable loadImageFromUrl(String url) {
    	/**
    	 * 加载网络图片
   	    */

        try {
            return Drawable.createFromStream(new URL(url).openStream(), "src");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    	/**
    	 * 加载内存卡图片
    	 */
/*    	    Options options=new Options();
    	    options.inSampleSize=2;
    	    Bitmap bitmap=BitmapFactory.decodeFile(url, options);
    	    Drawable drawable=new BitmapDrawable(bitmap);*/
            //return drawable;
        }
    
      
    public interface ImageCallback {
             public void imageLoaded(Drawable imageDrawable, String imageUrl, String Tag);
             
         }
}