package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.ys.ui.R;
import com.ys.ui.adapter.GngxGridAdaper;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.GngxEnum;
import com.ys.ui.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能功效
 * Created by wangtao on 2016/7/8.
 */
public class YsGNGXActivity extends BaseTimerActivity implements View.OnClickListener {


    @Bind(R.id.gridView)
    GridView gridView;

    private List<Map<String, Object>> data_list;
    SimpleAdapter simpleAdapter;

    private int[] images = new int[]{
            R.mipmap.zibuyangsheng, R.mipmap.ydfs, R.mipmap.yybj, R.mipmap.ylqx,
            R.mipmap.wgyy, R.mipmap.qqsh, R.mipmap.cwyy, R.mipmap.gmyy,
            R.mipmap.hxxt, R.mipmap.mzhl, R.mipmap.qrjd, R.mipmap.pfyy
    };

    private int[] ids = new int[] {
            GngxEnum.ZIBUYANGSHENG.getId(), GngxEnum.YDFS.getId(), GngxEnum.YYBJ.getId(),GngxEnum.YLQX.getId(),
            GngxEnum.WGYY.getId(), GngxEnum.QQSH.getId(), GngxEnum.CWYY.getId(), GngxEnum.GMYY.getId(),
            GngxEnum.HXXT.getId(), GngxEnum.MZHL.getId(), GngxEnum.QRJD.getId(),  GngxEnum.PFYY.getId(),
    };

    @Override
    protected int getLayoutId() {
        return R.layout.level2_gngx;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        data_list = new ArrayList<>();
        getData();

        String[] from ={"image", "id"};
        int[] to = {R.id.iv_gngx, R.id.tv_id};

        simpleAdapter = new SimpleAdapter(this, data_list, R.layout.level2_gngx_item, from, to);

        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = data_list.get(position);

                finish();
                Intent intent = new Intent(YsGNGXActivity.this, YsDetailActivity.class);
                intent.putExtra("index", (int)(map.get("id")));
                intent.putExtra("desc", GngxEnum.findParamById((int)map.get("id")).getDesc());
                startActivity(intent);

            }
        });
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<images.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            map.put("id", ids[i]);
            data_list.add(map);
        }

        return data_list;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            default:
                break;
        }

    }

    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(YsGNGXActivity.this, YsActivity.class));
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }
}
