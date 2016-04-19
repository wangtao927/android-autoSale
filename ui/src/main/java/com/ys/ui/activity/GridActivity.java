package com.ys.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;

import com.ys.data.bean.GoodsBean;
import com.ys.ui.R;
import com.ys.ui.adapter.ImageAndTextListAdapter;
import com.ys.ui.base.App;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.view.GoodsGridViewItem;
import com.ys.ui.view.ImageAndText;

public class GridActivity extends Activity {

	private ImageAndTextListAdapter adapter;
	private List<GoodsGridViewItem> listImageAndText;
	private GridView gridview ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_list);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		gridview = (GridView)findViewById(R.id.gv_list);
		initDatas();
//		String[] urls = {"http://s13.sinaimg.cn/mw690/5b29fb5fge09080b5cdec&690",
//						 "http://s13.sinaimg.cn/mw690/5b29fb5fge09080b5cdec&690",
//						 "http://s7.sinaimg.cn/middle/4bdf6ca8te086771aa996&690",
//						 "http://s13.sinaimg.cn/middle/4bdf6ca8te0867709cdec&690",
//						 "http://s7.sinaimg.cn/middle/4bdf6ca8te08676f028d6&690",
//						 "http://s14.sinaimg.cn/middle/4bdf6ca8te0867713097d&690",
//						 "http://s5.sinaimg.cn/middle/4bdf6ca8te0867861e5e4&690",
//						 "http://s14.sinaimg.cn/middle/4bdf6ca8te08679d3971d&690",
//						 "http://s11.sinaimg.cn/middle/4bdf6ca8te086784a9cca&690",
//						 "http://s13.sinaimg.cn/middle/4bdf6ca8te0867709cdec&690",
//						 "http://s7.sinaimg.cn/middle/4bdf6ca8te08676f028d6&690",
//						 "http://s14.sinaimg.cn/middle/4bdf6ca8te0867713097d&690",
//						 "http://s5.sinaimg.cn/middle/4bdf6ca8te0867861e5e4&690",
//						 "http://s14.sinaimg.cn/middle/4bdf6ca8te08679d3971d&690",
//						 "http://s11.sinaimg.cn/middle/4bdf6ca8te086784a9cca&690",
//						 "http://s9.sinaimg.cn/mw690/5b29fb5fge09074bd4618&690",
//						 "http://s8.sinaimg.cn/mw690/5b29fb5fge09075583c07&690",
//						 "http://s9.sinaimg.cn/mw690/5b29fb5fge09075a70738&690",
//						 "http://s14.sinaimg.cn/mw690/5b29fb5fge090760fb93d&690",
//						 "http://s13.sinaimg.cn/mw690/5b29fb5fge09080b5cdec&690"
//						 };

//		List<GoodsGridViewItem> listImageAndText = new ArrayList<GoodsGridViewItem>();
//		for(int i = 0;i < urls.length ;i++){
//			GoodsGridViewItem iat = new GoodsGridViewItem(urls[i], "商品名称"+i);
//			listImageAndText.add(iat);
//		}
		//Activity activity, List<ImageAndText> imageAndTexts, GridView gridView1
		adapter = new ImageAndTextListAdapter(GridActivity.this, listImageAndText,gridview);
		gridview.setAdapter(adapter);
	}

	private void initDatas() {
		listImageAndText = new ArrayList<GoodsGridViewItem>();
		List<GoodsBean> lists = App.getDaoSession(App.getContext()).getGoodsBeanDao().loadAll();
        for (GoodsBean bean : lists) {
			GoodsGridViewItem item = new GoodsGridViewItem();
			//  注意   bean.getGd_img1() 是带有下划线，group1/M00/00/00/wKhLglcL2suAfku6AAuC35f-phI4495495|文件名.jpg  需要处理掉
			item.setImageUrl(RetrofitManager.BASE_URL + bean.getGd_img1());
			item.setGdName(bean.getGd_name());
			listImageAndText.add(item);
		}

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_main, menu);
//		return true;
//	}

}
