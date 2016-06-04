package com.ys.ui.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.landfoneapi.misposwa.E_REQ_RETURN;
import com.landfoneapi.misposwa.ILfListener;
import com.landfoneapi.misposwa.MyApi;
import com.ys.ui.base.App;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

public class MyService extends Service {

	private MyBinder mBinder = new MyBinder();
	private Context getServiceContext(){
		return this;
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	@Override
	public void onCreate(){
		Log.i("MyService", "onCreate!!");
		super.onCreate();
	}
	@Override
	public void onDestroy(){
		Log.i("MyService", "onDestroy!!");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
	public class MyBinder extends Binder{
		/**
		 * 获取当前Service的实例
		 * @return
		 */
		public MyService getService(){
			return MyService.this;
		}
		///////////////////////////////////////业务操作////////////////////////////////////////////
		private MyApi mMyApi = new MyApi();

		public void setILfListener(ILfListener lsn){
			mMyApi.setILfListener(lsn);
		}

		/**
		 * 是否使用同步接口，切换时设置一次即可
		 * @param v
		 */
		public void setUseSynch(boolean v){
			 mMyApi.setUseSynch(v);
		}

		/**
		 * 是否使用同步方法
		 * @return
		 */
		public boolean isUseSynch(){
			return mMyApi.isUseSynch();
		}
		public E_REQ_RETURN pos_init(){

			App app = (App)App.getContext();
			SharedPreferences sp = getSharedPreferences("posSerial", MODE_PRIVATE);
			String path = sp.getString("pos_path", app.getMinipos_path());
			int baudrate = Integer.decode(sp.getString("pos_baudrate", String.valueOf(app.getMinipos_baudrate())));

			/* Check parameters */
			if ( (path.length() == 0) || (baudrate == -1)) {
				ToastUtils.showError("baudarete is error ：" + baudrate, App.getContext());
				// throw new InvalidParameterException();
			}
			//设置串口接口
			mMyApi.setPOSISerialPort(null);//null时使用android的串口jni，android_serialport_api.SerialPort
			//设置透传ip、端口；POS的串口路径和波特率

			return mMyApi.pos_init("113.108.182.4", 10061,
					path, String.valueOf(baudrate));//"/dev/ttyS1"//lf
		}
		public E_REQ_RETURN pos_init(String path, int port){
			//设置串口接口
			mMyApi.setPOSISerialPort(null);//null时使用android的串口jni，android_serialport_api.SerialPort
			//设置透传ip、端口；POS的串口路径和波特率
			return mMyApi.pos_init("113.108.182.4", 10061, path, String.valueOf(port));//"/dev/ttyS1"//lf
		}
		/**
		 * 签到
		 * @return
		 */
		public E_REQ_RETURN pos_signin(){
			return mMyApi.pos_signin();
		}

		/**
		 *	取助农类交易信息
		 * @param tradeSerial 凭证号,填写"000000"为获取最后一笔交易
		 * @return
		 */
		public E_REQ_RETURN pos_getTradeInfo(String tradeSerial){
			return mMyApi.pos_getrecord("000000000000000","00000000",tradeSerial);
		}

		/**
		 * 取消操作，网络通讯时不可取消* @return
		 */
		public E_REQ_RETURN pos_cancel(){
			return mMyApi.pos_cancel();
		}

		/**
		 * 确认操作
		 * @return
		 */
		public E_REQ_RETURN pos_confirm(){
			return mMyApi.pos_confirm();
		}
		/**
		 * 释放接口
		 * @return
		 */
		public E_REQ_RETURN pos_release(){
			return mMyApi.pos_release();
		}

		/**
		 * 结算
		 * @return
		 */
		public E_REQ_RETURN pos_settle(){
			return mMyApi.pos_settle();
		}



		///////////////////////////////////////////////////非助农接口////////////////////////////////////////////////////////////
		/**
		 * 查余额，余额通过液晶透传显示数据上报（Display）
		 * @return
		 */
		public E_REQ_RETURN pos_query(){
			return mMyApi.pos_query();
		}

		/**
		 * 消费
		 * @param amount_fen
		 * @return
		 */
		public E_REQ_RETURN pos_purchase(int amount_fen){
			return mMyApi.pos_purchase(amount_fen);
		}

		/**
		 * 是否允许查询余额
		 * @return	false-允许
		 */
		public boolean pos_isQuerying(){return mMyApi.pos_isQuerying();}

	}
}
