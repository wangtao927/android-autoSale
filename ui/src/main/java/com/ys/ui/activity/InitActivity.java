package com.ys.ui.activity;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.landfoneapi.misposwa.CallbackMsg;
import com.landfoneapi.misposwa.E_REQ_RETURN;
import com.landfoneapi.misposwa.ILfListener;
import com.landfoneapi.protocol.pkg.Display;
import com.landfoneapi.protocol.pkg.DisplayType;
import com.landfoneapi.protocol.pkg._04_GetRecordReply;
import com.landfoneapi.protocol.pkg._04_PassbookRenewReply;
import com.ys.ui.R;
import com.ys.ui.service.MyService;

public class InitActivity extends Activity {
	private String TAG = "InitActivity";
	private MyService.MyBinder myBinder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ConnectService_whenOncreate();//连接服务		 
		BindLfService();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pos_main);

		setupUI();//设置UI
	}
	@Override
	protected void onDestroy(){
		UnBindLfService();
		super.onDestroy();

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private ServiceConnection mSc;
	private Class<?> serviceClass = MyService.class;

	private void BindLfService(){
		Log.d(TAG, this.getApplicationContext().getPackageCodePath());
		Intent service = new Intent(this.getApplicationContext(),serviceClass);//LfService.class
		this.bindService(service, mSc, Context.BIND_AUTO_CREATE);
	}

	private void UnBindLfService(){
		this.unbindService(mSc);
	}
	private void ConnectService_whenOncreate(){
		mSc = new ServiceConnection(){
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				myBinder = (MyService.MyBinder) service;
				//回调
				myBinder.setILfListener(mILfMsgHandler);
			}
			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "service disconnected");
			}
		};
	}
	/////////////////////////////////////界面处理////////////////////////////////////////
	private Button BTinit;
	private Button BRelease;
	private Button BTsignin,BTsettle;
	private Button BTtrade, BTgetRecordInfo;
	private Button BTquery;
	private Button BTcancel;


	private TextView info,key,lcd;
	private Switch SWsynch;

	private OnClickListener mOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			E_REQ_RETURN ret = null;
			String tmp  ="";
			CallbackMsg cbmsg = null;
			if(arg0 == BTinit){
				info.setText("POS init(测试参数)");
				myBinder.pos_init();
			}else if(arg0 == BTsignin){

				if(E_REQ_RETURN.REQ_OK != (ret=myBinder.pos_signin())){
					//调用失败，可能的返回如下
					//E_REQ_RETURN.REQ_BUSY//接口忙，有操作未完成
					//E_REQ_RETURN.REQ_DENY//与其它操作或状态冲突
					info.setText("返回:"+ret.getDesc());
				}else{
					info.setText("POS pos_signin..");
				}

			}else if(arg0 == BTtrade){
				if(E_REQ_RETURN.REQ_OK != (ret=myBinder.pos_purchase(1))){
					info.setText("返回:"+ret.getDesc());
				}else{
					info.setText("POS pos_purchase..");
					if(myBinder.isUseSynch()//使用同步方法时
							&& ret.getObj()!=null) {//有返回数据
						//处理方式与handler里一样
						cbmsg = (CallbackMsg) ret.getObj();//从ret的obj转换
						if (cbmsg.reply == 0) {//成功
							tmp = ("返回ACK, msg:" + cbmsg.info);
							if (cbmsg.mREPLY != null) {
								tmp += "\n" + ((_04_PassbookRenewReply) (cbmsg.mREPLY)).getReturnData();//返回的数据
							}
							info.setText(tmp);
						} else if (cbmsg.reply == 1) {//失败
							info.setText("返回NAK, op_type:" + cbmsg.op_type.getDesc() + ",errcode:" + cbmsg.nak_err_code.getDesc() + ", msg:" + cbmsg.info);
						}
					}
				}
			}else if(arg0 == BTgetRecordInfo){
				if(E_REQ_RETURN.REQ_OK != (ret=myBinder.pos_getTradeInfo("000000"))){
					info.setText("返回:"+ret.getDesc());
				}else{
					info.setText("POS pos_getTradeInfo..");
				}
			}else if(arg0 == BTquery){
				if(!myBinder.pos_isQuerying()) {//是否允许查询
					if (E_REQ_RETURN.REQ_OK != (ret = myBinder.pos_query())) {
						info.setText("返回:" + ret.getDesc());
					} else {
						info.setText("POS query..");
					}
				}else{
					info.setText("设备忙");
				}
			}else if(arg0 == BRelease){
				if(E_REQ_RETURN.REQ_OK != (ret=myBinder.pos_release())){
					info.setText("返回:"+ret.getDesc());
				}else{
					info.setText("POS release..");
				}
			}else if(arg0 == BTcancel){

				if(E_REQ_RETURN.REQ_OK != (ret=myBinder.pos_cancel())){
					info.setText("返回:"+ret.getDesc());
				}else{
					info.setText("POS pos_cancel..");
				}
			}else if(arg0 == BTsettle){
				if(E_REQ_RETURN.REQ_OK != (ret=myBinder.pos_settle())){
					info.setText("返回:" + ret.getDesc());
				}else{
					info.setText("POS pos_settle..");
				}
			}else if(arg0 == SWsynch){//同步异步设置
				if(SWsynch.isChecked()){
					myBinder.setUseSynch(true);//设置为使用同步模式，任意位置设置都可以，切换时只设置一次
				}else{
					myBinder.setUseSynch(false);//设置为使用异步
				}
			}
			//SWsynch
		}};


	private void setupUI(){
		BTinit = (Button) this.findViewById(R.id.BTinit);
		BTinit.setOnClickListener(mOnClickListener);
		BRelease = (Button) this.findViewById(R.id.BTrelease);
		BRelease.setOnClickListener(mOnClickListener);

		BTsignin = (Button) this.findViewById(R.id.BTsignin);
		BTsignin.setOnClickListener(mOnClickListener);
		BTtrade = (Button) this.findViewById(R.id.BTtrade);
		BTtrade.setOnClickListener(mOnClickListener);
		BTquery = (Button) this.findViewById(R.id.BTquery);
		BTquery.setOnClickListener(mOnClickListener);

		BTcancel = (Button) this.findViewById(R.id.BTcancel);
		BTcancel.setOnClickListener(mOnClickListener);


		BTgetRecordInfo = (Button) this.findViewById(R.id.BTgetRecordInfo);
		BTgetRecordInfo.setOnClickListener(mOnClickListener);


		BTsettle = (Button) this.findViewById(R.id.BTsettle);
		BTsettle.setOnClickListener(mOnClickListener);

		SWsynch = (Switch) this.findViewById(R.id.SWsynch);
		SWsynch.setOnClickListener(mOnClickListener);

		info = (TextView) this.findViewById(R.id.info);
		key = (TextView) this.findViewById(R.id.key);
		lcd = (TextView) this.findViewById(R.id.lcd);
	}

	//////////////////////////////////////////业务返回///////////////////////////////////////
	private Handler mmHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Display dpl = null;//POS提示信息显示
			System.out.println("InitActivity, mILfMsgHandler incomming, msg.arg:"+msg.arg1);
			CallbackMsg cbmsg = (CallbackMsg)msg.obj;
			String tmp = "";
			switch(cbmsg.op_type){//信息（交易）类型
				case OP_POS_SIGNIN://签到
				case OP_POS_QUERY://查询
				case OP_POS_PURCHASE://支付
					if(cbmsg.reply == 0){//成功
						info.setText("返回ACK, msg:"+cbmsg.info);
					}else if(cbmsg.reply == 1){//失败
						info.setText("返回NAK, op_type:"+cbmsg.op_type.getDesc()+",errcode:"+cbmsg.nak_err_code.getDesc()+", msg:"+cbmsg.info);
					}
					break;
				case OP_POS_DISPLAY://POS提示信息
					if(cbmsg.reply == 0){//成功
						dpl = cbmsg.dsp;
						if(dpl.getType()== DisplayType._key.getType()){
							key.setText("上报键值:"+dpl.getMsg());
						}else if(dpl.getType()== DisplayType._card.getType()){
							key.setText("上报卡信息:"+dpl.getMsg());//逗号,隔开 “卡号,磁道2,磁道3,卡类型”
						}else {
							lcd.setText("提示信息("+dpl.getType()+"):"+DisplayType.getDesc(dpl)+"\n" + dpl.getMsg());
						}
					}
					break;
				case OP_POS_GETRECORD:
					System.out.println(">>>>OP_POS_GETRECORD");
					if(cbmsg.reply == 0){//成功
						tmp = "返回ACK, msg:"+cbmsg.info;
						if(cbmsg.mREPLY!=null){
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getMer();//商户代码
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTmn();//终端号
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getMer_cn_name();//商户中文名
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getMer_en_name();//商户英文名
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getBankCode();//收单行标识码
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getCardCode();//发卡行标识码
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getPosCenterCode();//POS中心标识码
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getCardNo();//卡号
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getOperatorNo();//操作员号
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getPreTradeType();//原交易类型
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getCardExp();//卡有效期
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTransacionBatchNo();//交易批次号
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTransacionVoucherNo();//原交易类型
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTransacionDatetime();//交易日期和时间
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTransacionAuthCode();//授权码
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTransacionReferNo();//参考号
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTransacionAmount();//交易金额
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTipAmount();//小费金额
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getCumAmount();//累计金额
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTotalAmount();//总金额
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getInterCreditCode();//国际信用卡公司代码
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getICTransCert();//IC卡交易证书
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTVR();//TVR
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTSI();//TSI
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getAID();//AID
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getATC();//ATC
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getAppLabels();//应用标签
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getAppPreferredName();//应用首选名称
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getTAC();//TAC
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getDeducAmount();//扣持卡人金额
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getIsOffline();//是否脱机交易
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getInputMode();//输入模式
							tmp +=","+((_04_GetRecordReply)(cbmsg.mREPLY)).getRemark();//备注信息
						}
						info.setText(tmp);
					}else if(cbmsg.reply == 1){//失败
						info.setText("返回NAK, op_type:" + cbmsg.op_type.getDesc() + ",errcode:" + cbmsg.nak_err_code.getDesc() + ", msg:" + cbmsg.info);
					}
					break;
			}
		}
	};
	//支付接口的返回处理
	private ILfListener mILfMsgHandler = new ILfListener(){

		@Override
		public void onCallback(Message msg) {
			mmHandler.sendMessage(msg);
		}
	};

}
