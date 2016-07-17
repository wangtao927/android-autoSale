package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.GngxEnum;
import com.ys.ui.common.constants.RtbwEnum;
import com.ys.ui.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *  人体部位
 * Created by wangtao on 2016/7/8.
 */
public class YsRTBWActivity extends BaseTimerActivity {

    @Bind(R.id.gv_rtbw)
    GridView gridView;
    private List<Map<String, Object>> data_list;
    SimpleAdapter simpleAdapter;

    private int[] images = new int[]{
            R.mipmap.tou, R.mipmap.jin,
            R.mipmap.xiong, R.mipmap.fu,
            R.mipmap.sizhi, R.mipmap.szq,
            R.mipmap.bei, R.mipmap.yaotun,
            R.mipmap.xinzang, R.mipmap.fei,
            R.mipmap.shenzang, R.mipmap.dachang,
            R.mipmap.xiaochang, R.mipmap.pangguang,
            R.mipmap.ganpi, R.mipmap.ganzang,
            R.mipmap.dan, R.mipmap.wei,
    };

    private int[] ids = new int[] {
            RtbwEnum.TOU.getId(), RtbwEnum.JIN.getId(),
            RtbwEnum.XIONG.getId(),RtbwEnum.FU.getId(),
            RtbwEnum.SIZHI.getId(), RtbwEnum.SZQ.getId(),
            RtbwEnum.BEI.getId(), RtbwEnum.YAOTUN.getId(),
            RtbwEnum.XINZANG.getId(), RtbwEnum.FEI.getId(),
            RtbwEnum.SHENGZANG.getId(),  RtbwEnum.DACHANG.getId(),
            RtbwEnum.XIAOCHANG.getId(),  RtbwEnum.PANGGUANG.getId(),
            RtbwEnum.PIZANG.getId(),  RtbwEnum.GANZANG.getId(),
            RtbwEnum.DAN.getId(),  RtbwEnum.WEI.getId(),
    };
    @Override
    protected int getLayoutId() {
        return R.layout.level2_rtbw;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        data_list = new ArrayList<>();
        getData();
        String[] from ={"image", "id"};
        int[] to = {R.id.iv_gngx, R.id.tv_id};
        simpleAdapter = new SimpleAdapter(this, data_list, R.layout.level2_rtbw_item, from, to);

        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = data_list.get(position);
                ToastUtils.showShortMessage(map.get("image") + "--" + map.get("id").toString());

                finish();
                Intent intent = new Intent(YsRTBWActivity.this, YsDetailActivity.class);
                intent.putExtra("index", (int)(map.get("id")));
                intent.putExtra("desc", RtbwEnum.findParamById((int)map.get("id")).getDesc());
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
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(YsRTBWActivity.this, HomeActivity.class));
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
