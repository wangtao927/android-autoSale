/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.ys.ui.serial.print.activity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.PrintConstants;
import com.ys.ui.utils.ToastUtils;

public class MainMenu extends SerialPortActivity
{
	private ToggleButton mBtnBtSwitch;
	private Button mCutBtn ;
	private Button mCode39Btn;
	 private Button    mPrintBtn;
	private Button closeBtn;
	private Button ttyS0;
	private Button ttyS1;
	 	public void Init()
	{
		mBtnBtSwitch = (ToggleButton) this.findViewById(R.id.tbtnSwitch);
		mBtnBtSwitch.setOnClickListener(new BtnListener());

		mPrintBtn = (Button) findViewById(R.id.printButton);
		mPrintBtn.setOnClickListener(new BtnListener());

		mCutBtn = (Button) findViewById(R.id.CutBtn);
		mCutBtn.setOnClickListener(new BtnListener());
		mCode39Btn= (Button) findViewById(R.id.Code39);
		mCode39Btn.setOnClickListener(new BtnListener());

        closeBtn= (Button) findViewById(R.id.CloseBtn);
		closeBtn.setOnClickListener(new BtnListener());
		ttyS0= (Button) findViewById(R.id.ttyS0);
		ttyS0.setOnClickListener(new BtnListener());
		ttyS1= (Button) findViewById(R.id.ttyS1);
		ttyS1.setOnClickListener(new BtnListener());


	}
	class BtnListener implements OnClickListener
	{
		public void onClick(View v)
		{

			if(v==mBtnBtSwitch)//
			{

				if(mSerialPort==null)
				{
					InitCom(); if(mSerialPort==null){mBtnBtSwitch.setChecked(false);mBtnBtSwitch.setText("打开串口");}else{mBtnBtSwitch.setChecked(true);mBtnBtSwitch.setText("关闭串口");}
				}
				else
				{
					CloseCom();
					if(mSerialPort==null){mBtnBtSwitch.setChecked(false);mBtnBtSwitch.setText("打开串口");}else{mBtnBtSwitch.setChecked(true);mBtnBtSwitch.setText("关闭串口");}
				}
			}

			if(v==mCutBtn)
			{
				byte SendBuf[]={0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x1b,0x69};
				SendData(SendBuf);
			}

			if(v==mCode39Btn)
			{
				byte SendBuf[]={0X0D, 0X0A, 0X1D, 0X48, 0X02, 0X1D, 0X68, (byte) 0X80, 0X1D, 0X77, 0X02, 0X1D, 0X6B, 0X45, 0X09, 0X30, 0X31, 0X32, 0X33, 0X34, 0X35, 0X36, 0X37, 0X38, 0X0D, 0X0A};
				SendData(SendBuf);
			}
			if(v==mPrintBtn)
			{

 				//String mSendData = mSendEditText.getText().toString();

//				PrintHelper.getInstance().gdPrint("12345678",
//						"88888888", "测试测试商品名称", "一盒/6只", "10.00", "9.00", "10.00");

				String mSendData =String.format(PrintConstants.content, "12345678", "2016-05-03",
						"88888888", "测试测试商品名称", "一盒/6只", "10.00", "9.00", "10.00元", "");

				byte SendBuf[]={0x1b,0x40};
				SendData(SendBuf);

				try
				{
					byte[] send = null;
					send = mSendData.getBytes("GBK");
					 SendData(send);

				}
				catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (v == closeBtn) {
				CloseCom();
				//PrintHelper.getInstance().initPrint();
			}
//			if (v == ttyS0) {
//                PrintHelper.getInstance().ttyS0();
//			}
//			if (v == ttyS1) {
//				PrintHelper.getInstance().ttyS1();
//			}
		}
	}


	public void gdPrint(String orderNo, String termNo, String gdName, String gdType, String price, String vipPrice, String actualPrice) {
		// 初始化串口
		InitCom();

		// 打印条形码
		printCode39();

		// 打印内容
		print(orderNo, termNo, gdName, gdType, price, vipPrice, actualPrice);

		// 切纸
		printCut();

		CloseCom();

	}

	private void printCode39() {
		byte SendBuf[]={0X0D, 0X0A, 0X1D, 0X48, 0X02, 0X1D, 0X68, (byte) 0X80, 0X1D, 0X77, 0X02, 0X1D, 0X6B, 0X45, 0X09, 0X30, 0X31, 0X32, 0X33, 0X34, 0X35, 0X36, 0X37, 0X38, 0X0D, 0X0A};
		SendData(SendBuf);

	}

	private void print(String orderNo, String termNo, String gdName, String gdType, String price, String vipPrice, String actualPrice) {

		String mSendData =String.format(PrintConstants.content, orderNo, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
				termNo, gdName, gdType, price, vipPrice, actualPrice);

		byte SendBuf[]={0x1b,0x40};
		SendData(SendBuf);

		try {
			byte[] send = null;
			send = mSendData.getBytes("GBK");
			SendData(send);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printCut() {

		byte SendBuf[]={0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x1b,0x69};
		SendData(SendBuf);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		Init();
		if(mSerialPort==null){mBtnBtSwitch.setChecked(false);mBtnBtSwitch.setText("打开串口");}else{mBtnBtSwitch.setChecked(true);mBtnBtSwitch.setText("关闭串口");}

	}// end onCreate

	@Override
	protected void onDataReceived(final byte[] buffer, int size)
	{
		final byte[] bs = buffer;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				ToastUtils.showShortMessage(new String(bs));

			}
		});
		// TODO Auto-generated method stub

	}
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		CloseCom();
	}

}
