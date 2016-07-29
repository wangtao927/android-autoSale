package com.ys.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.beta.Beta;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.McGoodsListAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.serial.print.activity.PrintHelper;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wangtao on 2016/4/9.
 */
public class AdminActivity extends BaseActivity implements View.OnClickListener, LMRecyclerView.LoadMoreListener {
    private final static String TAG = AdminActivity.class.getSimpleName();

    @Bind(R.id.btn_reset)
    Button btnReset;

    @Bind(R.id.btn_buhuo)
    Button btnBuHuo;

    @Bind(R.id.btn_clear)
    Button btnClear;

    @Bind(R.id.btn_sys_out)
    Button btnSysOut;
    @Bind(R.id.btn_init_serial)
    Button btnInitSerial;
    @Bind(R.id.btn_clear_saleList)
    Button btnCleanSaleList;
//    @Bind(R.id.btn_init_sale)
//    Button btnInitSale;


    @Bind(R.id.btn_back)
    Button btnBack;
    @Bind(R.id.btn_check_update)
    Button btnCheckUpdate;

    @Bind(R.id.tv_admin_mcno)
    TextView tvMcNo;
    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;

    private List<McGoodsBean> lists = new ArrayList();

    private McGoodsListAdapter adapter;

    private long mTotalCount;

    private static final int mPageSize = 40;

    private int mPageIndex = 1;


    @Override
    protected void create(Bundle savedInstanceState) {
        initView();

        btnBuHuo.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnSysOut.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnInitSerial.setOnClickListener(this);
        btnCleanSaleList.setOnClickListener(this);
//        btnInitSale.setOnClickListener(this);
        btnCheckUpdate.setOnClickListener(this);
        tvMcNo.setText("终端号：" + App.mcNo);
        loadData();
        adapter = new McGoodsListAdapter(AdminActivity.this, lists);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(App.ctx));
        recyclerView.setLoadMoreListener(this);
    }

    private void loadData() {
        mTotalCount = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().count();

        QueryBuilder<McGoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder().orderAsc(McGoodsBeanDao.Properties.Mg_channo);
        int offset = mPageIndex == 1 ? 0 : (mPageIndex * mPageSize);
        List pageList = queryBuilder.offset(offset).limit(mPageSize).list();
        lists.addAll(pageList);

    }

    private void refresh() {
        adapter.setData(lists);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.admin_main;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_reset:

                reset();
                break;
            case R.id.btn_buhuo:
                App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateAll(lists);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_clear:
                // 清除卡货
                DbManagerHelper.clearAllChannStatus(lists);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_back:

                // 返回首页
                finish();
                startActivity(new Intent(this, HomeActivity.class));

                break;
            case R.id.btn_sys_out:
                // 退出程序
                finish();
//                android-serialport.os.Process.killProcess(android.os.Process.myPid());
//
//                System.exit(0);
                // 退出没效果， 抛异常
                throw new RuntimeException("admin exit system");
                //break;
            case R.id.btn_init_serial:

                PrintHelper.getInstance().initPrint();
                break;
              case R.id.btn_clear_saleList:
                   // 跳转到流水界面
                  finish();
                  startActivity(new Intent(AdminActivity.this, AdminSaleLitActivity.class));
                  //App.getDaoSession(App.getContext()).getSaleListBeanDao().deleteSaleListBySendStatus();
                break;
//              case R.id.btn_init_sale:
//                  SerialMachineHelper.getInstance().getSerial();
//
//                break;
            case R.id.btn_check_update:
                Beta.checkUpgrade();
                break;
            default:
                break;
        }

    }

    @Override
    public void loadMore() {
        if (lists.size() < mTotalCount) {
            mPageIndex++;
            loadData();
            refresh();
        } else {
            //ToastUtils.showError("所有数据已经全部加载了", App.getContext());
        }
    }

    void backProcess() {
        new AlertDialog.Builder(this).setTitle("确认要重置吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        RetrofitManager.builder().mcReset(App.mcNo)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {

                                    }
                                })
                                .subscribe(new Action1<CommonResponse<TermInitResult>>() {
                                    @Override
                                    public void call(CommonResponse<TermInitResult> response) {
                                        Log.d("result", response.toString());
                                        if (response.isSuccess()) {
                                            // 生成成功  同步数据
                                            initTerm(response);

                                            // 初始化数据成功， 初始化出货机，打印机，银联


                                            //startActivity(new Intent(TermInitActivity.this, MainActivity.class));
                                        } else {
                                        }
                                        // 关闭当前active
                                        finish();
                                        startActivity(new Intent(AdminActivity.this, AdminActivity.class));
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Toast.makeText(AdminActivity.this, "获取数据失败" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                        dialog.cancel();
                    }
                }).show();

    }

    private void reset() {
        backProcess();

    }

    private McStatusBean getInitBean(String termno) {
        McStatusBean bean = new McStatusBean();

        bean.setMc_no(termno);
        return bean;
    }

    private void initTerm(CommonResponse<TermInitResult> response) {
        // 1. 保存终端号到sqllite
        McStatusBean mcStatusBean = getInitBean(response.getExt_data().getMachine().getMc_no());
        DbManagerHelper.initTermStatus(mcStatusBean);
        // 2. 更新终端参数
        DbManagerHelper.initMcParam(response.getExt_data().getMcparam());
        // 3. 更新商品表
        DbManagerHelper.initGoods(response.getExt_data().getGoods());
        // 4. 更新终端库存
        DbManagerHelper.initMcGoods(response.getExt_data().getMcgoods());
        // 5.更新管理员
        DbManagerHelper.initAdmin(response.getExt_data().getMcadmin());

        // 6. 更新广告
        DbManagerHelper.initAdv(response.getExt_data().getMcadv());

        // 初始化
        App.mcNo = mcStatusBean.getMc_no();

    }

}
