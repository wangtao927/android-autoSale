package com.ys.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ys.ui.R;
import com.ys.ui.view.AsyncImageLoader;
import com.ys.ui.view.GoodsGridViewItem;
import com.ys.ui.view.ImageAndText;
import com.ys.ui.view.ViewCache;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ImageAndTextListAdapter extends ArrayAdapter<GoodsGridViewItem> {

		private static String TAG = "diaoliang";
		private static int URLCOUNT = 0;
        private GridView gridView;
        private AsyncImageLoader asyncImageLoader;
        public ImageAndTextListAdapter(Activity activity, List<GoodsGridViewItem> imageAndTexts, GridView gridView1) {
            super(activity, 0, imageAndTexts);
            this.gridView = gridView1;
            asyncImageLoader = new AsyncImageLoader();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Activity activity = (Activity) getContext();

            // Inflate the views from XML
            View rowView = convertView;
            ViewCache viewCache;
            if (rowView == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.goods_item, null);
                viewCache = new ViewCache(rowView);
                rowView.setTag(viewCache);
            } else {
                viewCache = (ViewCache) rowView.getTag();
            }
            GoodsGridViewItem imageAndText = getItem(position);

            // Load the image and set it on the ImageView
            String imageUrl = imageAndText.getImageUrl();
            ImageView imageView = viewCache.getImageView();
            
            //���ñ�ǩ 
			String Tag = imageUrl+ URLCOUNT;
            imageView.setTag(Tag);
            URLCOUNT++;
            Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,Tag,new AsyncImageLoader.ImageCallback() {
                public void imageLoaded(Drawable imageDrawable, String imageUrl, String Tag) {
                    ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(Tag);
                    if (imageViewByTag != null) {
                        imageViewByTag.setImageDrawable(imageDrawable);
                    }
                }
            });
            if (cachedImage == null) {
                imageView.setImageResource(R.drawable.sun);
            }else{
                imageView.setImageDrawable(cachedImage);
            }
            // Set the text on the TextView
            TextView textView = viewCache.getTextView();
            textView.setText(imageAndText.getGdName());
            return rowView;
        }

}