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

package com.ys.ui.serial.salemachine;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.utils.ToastUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android_serialport_api.SerialPort;

public abstract class SerialMachineActivity extends Activity {


	protected App mApplication;
	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	private InputStream mInputStream;
	ReadThread mReadThread;
	//protected int baudrate  = 19200;

	//protected String path = "/dev/ttyES1";

	boolean begin = false;
	boolean end = false;
//	protected static List<byte[]> list;
//	protected static List<byte[]> list1;
//	protected  long timer = 0;
	/**
	 * 读取终端设备数据
	 * @author Administrator
	 */
	private class ReadThread extends Thread {

		@Override
		public void run() {
			super.run();

			// 定义一个包的最大长度
			int maxLength = 512;
			//byte[] buffer = new byte[maxLength];
			// 每次收到实际长度
			int available = 0;
			// 当前已经收到包的总长度
			int currentLength = 0;
			// 协议头长度4个字节（开始符1，类型1，长度2）
			int headerLength = 4;
//			list = new CopyOnWriteArrayList<>();
//			list1 = new CopyOnWriteArrayList<>();
			List<Byte> temp=new ArrayList<>();

			while (!isInterrupted()) {
				byte[] buffer = new byte[maxLength];
				try {
					available = mInputStream.available();
					if (available > 0) {

						// 防止超出数组最大长度导致溢出
//						if (available > maxLength - currentLength) {
//							available = maxLength - currentLength;
//						}
						if (available > maxLength) {
							available = maxLength;
						}
						mInputStream.read(buffer, currentLength, available);
						currentLength += available;
//						if (buffer[0] == 0x00) {
//							continue;
//						}

//						if (buffer[0]==0x02) {
//							begin = true;
//						}

						for (int i=0; i<buffer.length;i++) {
							if (buffer[i] == 0x02) {
//								timer++;
//								list1.add(buffer);
								begin = true;
								end = false;
							}

							if (begin && !end) {

								temp.add(buffer[i]);
							}

							if (buffer[i] == 0x03) {

								byte[] temp2 = new byte[temp.size()];
								for (int k = 0; k < temp.size(); k++) {
									temp2[k] = temp.get(k);
								}

								//list.add(temp2);
								onDataReceived(temp2);

								temp.clear();
								end = true;
								begin = false;

							}
						}


					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
	}

	protected void init() {
		mApplication = (App)getApplication();
//
		try {
			mSerialPort = mApplication.getSerialPort();

			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			/* Create a receiving thread */
			mReadThread = new ReadThread();
			mReadThread.start();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("SerialMachineActivity","onCreate:" + e.getMessage());
			//ToastUtils.showError("Exception:" + e.getMessage(), this);
			CrashReport.postCatchedException(e);
		}
	}

	protected abstract void onDataReceived(byte[] buffer);
	@Override
	protected void onDestroy() {
		if (mReadThread != null)
			mReadThread.interrupt();
		mApplication.closeSaleSerialPort();
		mSerialPort = null;
		super.onDestroy();
	}


}
